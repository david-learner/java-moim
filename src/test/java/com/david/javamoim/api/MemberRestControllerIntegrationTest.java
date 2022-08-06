package com.david.javamoim.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.david.javamoim.application.member.dtos.JoinRequest;
import com.david.javamoim.application.login.LoginQueryService;
import com.david.javamoim.application.login.dtos.LoginRequest;
import com.david.javamoim.application.member.MemberCommandService;
import com.david.javamoim.application.member.MemberQueryService;
import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.Role;
import com.david.javamoim.domain.Sex;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class MemberRestControllerIntegrationTest {

    private static final String TOKEN_TYPE_WITH_SEPARATOR = "Bearer ";
    private final MockMvc mockMvc;
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final LoginQueryService loginQueryService;
    private final ObjectMapper om;

    @Autowired
    public MemberRestControllerIntegrationTest(MockMvc mockMvc,
                                               MemberCommandService memberCommandService,
                                               MemberQueryService memberQueryService,
                                               LoginQueryService loginQueryService,
                                               ObjectMapper om) {
        this.mockMvc = mockMvc;
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
        this.loginQueryService = loginQueryService;
        this.om = om;
    }

    @Test
    @DisplayName("모임주최자로 회원가입을 한다")
    void join_as_host() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "david");
        requestBody.put("birthDate", "1950-06-25");
        requestBody.put("sex", "MALE");
        requestBody.put("id", "kr.seoul.david");
        requestBody.put("password", "Iamdavid12!@");
        requestBody.put("email", "david@david.com");
        requestBody.put("organization", "david company");
        requestBody.put("role", "HOST");

        this.mockMvc.perform(
                        post("/api/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모임참여자로 회원가입을 한다")
    void join_as_guest() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "david");
        requestBody.put("birthDate", "1950-06-25");
        requestBody.put("sex", "MALE");
        requestBody.put("id", "kr.seoul.david");
        requestBody.put("password", "Iamdavid12!@");
        requestBody.put("email", "david@david.com");
        requestBody.put("organization", null);
        requestBody.put("allergicFoods", "egg, bean");
        requestBody.put("selfIntroduction", "Hi, I am David.");
        requestBody.put("role", "HOST");

        this.mockMvc.perform(
                        post("/api/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모임주최자 역할만 가지고 있는 멤버가 모임참여자 역할을 추가한다")
    void add_guest_role_to_member_who_has_only_host_role() throws Exception {
        String id = "kr.seoul.david";
        String password = "Iamdavid12!@";
        createMemberAsHost(id, password);
        String accessToken = login(new LoginRequest(id, password));
        String authorizationHeaderValue = TOKEN_TYPE_WITH_SEPARATOR + accessToken;
        Map<String, String> requestBodyForGuest = new HashMap<>();
        requestBodyForGuest.put("role", "GUEST");
        requestBodyForGuest.put("allergicFoods", "egg, bean");
        requestBodyForGuest.put("selfIntroduction", "Hi, I am David!");
        Member memberAsHost = findMember(id);

        assertThat(memberAsHost.isRole(Role.HOST)).isTrue();
        this.mockMvc.perform(
                        post("/api/members/roles/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, authorizationHeaderValue)
                                .content(om.writeValueAsString(requestBodyForGuest)))
                .andExpect(status().isOk());
        Member foundMember = findMember(id);
        assertThat(foundMember.isRole(Role.ALL)).isTrue();
    }

    @Test
    @DisplayName("모임참여자 역할만 가지고 있는 멤버가 모임주최자 역할을 추가한다")
    void add_host_role_to_member_who_has_only_guest_role() throws Exception {
        String id = "kr.seoul.david";
        String password = "Iamdavid12!@";
        createMemberAsGuest(id, password);
        String accessToken = login(new LoginRequest(id, password));
        String authorizationHeaderValue = TOKEN_TYPE_WITH_SEPARATOR + accessToken;
        Map<String, String> requestBodyForHost = new HashMap<>();
        requestBodyForHost.put("role", "HOST");
        requestBodyForHost.put("organization", "david company");
        Member memberAsGuest = findMember(id);

        assertThat(memberAsGuest.isRole(Role.GUEST)).isTrue();
        this.mockMvc.perform(
                        post("/api/members/roles/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, authorizationHeaderValue)
                                .content(om.writeValueAsString(requestBodyForHost)))
                .andExpect(status().isOk());
        Member foundMember = findMember(id);
        assertThat(foundMember.isRole(Role.ALL)).isTrue();
    }

    @Test
    @DisplayName("모임주최자 역할만을 가진 사용자가 자신의 정보를 조회한다")
    void member_who_has_only_host_role_requests_own_information() throws Exception {
        String id = "kr.seoul.david";
        String password = "Iamdavid12!@";
        createMemberAsHost(id, password);
        String accessToken = login(new LoginRequest(id, password));
        String authorizationHeaderValue = TOKEN_TYPE_WITH_SEPARATOR + accessToken;
        Member memberAsHost = findMember(id);

        String urlForMemberInformation = "/api/members/" + memberAsHost.getUid();
        String memberResponseAsString = this.mockMvc.perform(
                        get(urlForMemberInformation)
                                .header(HttpHeaders.AUTHORIZATION, authorizationHeaderValue))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApiResponse apiResponse = om.readValue(memberResponseAsString, ApiResponse.class);
        LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) apiResponse.getBody();
        Assertions.assertThat(response.get("organization")).isNotNull();
        Assertions.assertThat(response.get("organization")).isNotEmpty();
        Assertions.assertThat(response.get("password")).isNull();
        Assertions.assertThat(response.get("allergicFoods")).isNull();
    }

    @Test
    @DisplayName("모임참여자 역할만을 가진 사용자가 자신의 정보를 조회한다")
    void member_who_has_only_guest_role_requests_own_information() throws Exception {
        String id = "kr.seoul.david";
        String password = "Iamdavid12!@";
        createMemberAsGuest(id, password);
        String accessToken = login(new LoginRequest(id, password));
        String authorizationHeaderValue = TOKEN_TYPE_WITH_SEPARATOR + accessToken;
        Member memberAsGuest = findMember(id);

        String urlForMemberInformation = "/api/members/" + memberAsGuest.getUid();
        String memberResponseAsString = this.mockMvc.perform(
                        get(urlForMemberInformation)
                                .header(HttpHeaders.AUTHORIZATION, authorizationHeaderValue))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ApiResponse apiResponse = om.readValue(memberResponseAsString, ApiResponse.class);
        LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) apiResponse.getBody();
        Assertions.assertThat(response.get("allergicFoods")).isNotNull();
        Assertions.assertThat(response.get("allergicFoods")).isNotEmpty();
        Assertions.assertThat(response.get("selfIntroduction")).isNotNull();
        Assertions.assertThat(response.get("selfIntroduction")).isNotEmpty();
        Assertions.assertThat(response.get("password")).isNull();
        Assertions.assertThat(response.get("organization")).isNull();
    }

    private void createMemberAsHost(String id, String password) {
        JoinRequest request = new JoinRequest(
                "david",
                "1950-06-25",
                Sex.MALE,
                id,
                password,
                "david@david.com",
                "david company",
                null,
                null,
                Role.HOST
        );
        memberCommandService.join(request);
    }

    private void createMemberAsGuest(String id, String password) {
        JoinRequest request = new JoinRequest(
                "david",
                "1950-06-25",
                Sex.MALE,
                id,
                password,
                "david@david.com",
                null,
                "egg, bean",
                "Hi, I am David!",
                Role.GUEST
        );
        memberCommandService.join(request);
    }

    private String login(LoginRequest request) {
        return loginQueryService.login(request);
    }

    private Member findMember(String memberId) {
        return memberQueryService.findMember(memberId);
    }
}