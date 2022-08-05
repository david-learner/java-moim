package com.david.javamoim.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.david.javamoim.domain.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
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
class MemberRestControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final MemberRepository memberRepository;
    private final ObjectMapper om;

    @Autowired
    public MemberRestControllerIntegrationTest(MockMvc mockMvc, MemberRepository memberRepository,
                                               ObjectMapper om) {
        this.mockMvc = mockMvc;
        this.memberRepository = memberRepository;
        this.om = om;
    }

    @Test
    @DisplayName("모임 주최자로 회원가입을 한다")
    void join_as_host() throws Exception {
        Map<String, String> formData = new HashMap<>();
        formData.put("name", "david");
        formData.put("birthDate", "1950-06-25");
        formData.put("sex", "MALE");
        formData.put("id", "kr.seoul.david");
        formData.put("password", "Iamdavid12!@");
        formData.put("email", "david@david.com");
        formData.put("organization", "david company");
        formData.put("role", "HOST");

        this.mockMvc.perform(
                        post("/api/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(formData)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모임 참여자로 회원가입을 한다")
    void join_as_guest() throws Exception {
        Map<String, String> formData = new HashMap<>();
        formData.put("name", "david");
        formData.put("birthDate", "1950-06-25");
        formData.put("sex", "MALE");
        formData.put("id", "kr.seoul.david");
        formData.put("password", "Iamdavid12!@");
        formData.put("email", "david@david.com");
        formData.put("organization", null);
        formData.put("allergicFoods", "egg, bean");
        formData.put("selfIntroduction", "Hi, I am David.");
        formData.put("role", "HOST");

        this.mockMvc.perform(
                        post("/api/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(formData)))
                .andExpect(status().isOk());
    }
}