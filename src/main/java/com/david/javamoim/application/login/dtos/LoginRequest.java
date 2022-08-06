package com.david.javamoim.application.login.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record LoginRequest(
        @NotBlank
        String id,
        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$")
        String password
) {
}
