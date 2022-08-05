package com.david.javamoim.application;

import javax.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull
        String id,
        @NotNull
        String password
) {
}
