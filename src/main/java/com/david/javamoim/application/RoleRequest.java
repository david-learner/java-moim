package com.david.javamoim.application;

import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.Role;
import javax.validation.constraints.NotNull;

public record RoleRequest(
        @NotNull
        Role role,
        String organization,
        String allergicFoods,
        String selfIntroduction
) {
    public void applyTo(Member member) {
        if (role == Role.HOST) {
            member.addHostRole(this.organization);
        }
        if (role == Role.GUEST) {
            member.addGuestRole(this.allergicFoods, this.selfIntroduction);
        }
    }
}
