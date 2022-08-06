package com.david.javamoim.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.david.javamoim.application.JoinRequest;
import com.david.javamoim.application.LoginQueryService;
import com.david.javamoim.application.LoginRequest;
import com.david.javamoim.application.MemberCommandService;
import com.david.javamoim.domain.Role;
import com.david.javamoim.domain.Sex;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class LoginRestControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final MemberCommandService memberCommandService;
    private final LoginQueryService loginQueryService;
    private final ObjectMapper om;

    @Autowired
    public LoginRestControllerIntegrationTest(MockMvc mockMvc,
                                              MemberCommandService memberCommandService,
                                              LoginQueryService loginQueryService,
                                              ObjectMapper om) {
        this.mockMvc = mockMvc;
        this.memberCommandService = memberCommandService;
        this.loginQueryService = loginQueryService;
        this.om = om;
    }

    @Test
    @DisplayName("로그인을 성공하면 인증토큰을 반환한다")
    void if_login_data_is_valid_return_access_token() throws Exception {
        String id = "kr.seoul.david";
        String password = "Iamdavid12!@";
        createMemberAsHost(id, password);
        LoginRequest loginRequest = new LoginRequest(id, password);

        String loginResponseAsString = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ApiResponse apiResponse = om.readValue(loginResponseAsString, ApiResponse.class);
        LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) apiResponse.getBody();
        Assertions.assertThat(response.get("token")).isNotNull();
        Assertions.assertThat(response.get("token")).isNotEmpty();
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
}