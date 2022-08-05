package com.david.javamoim.api;

import com.david.javamoim.application.LoginQueryService;
import com.david.javamoim.application.LoginRequest;
import com.david.javamoim.application.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginRestController {

    private final LoginQueryService loginQueryService;

    @Autowired
    public LoginRestController(LoginQueryService loginQueryService) {
        this.loginQueryService = loginQueryService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        String accessToken = loginQueryService.login(request);
        LoginResponse response = new LoginResponse(accessToken);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
