package com.david.javamoim.application.member.dtos;

import com.david.javamoim.domain.Member;
import lombok.Getter;

@Getter
public class HostResponseBase extends BaseMemberResponse {

    private String organization;

    public HostResponseBase(Member member) {
        super(member.getUid(),
                member.getName(),
                member.getBirthDate(),
                member.getSex(),
                member.getId(),
                member.getEmail(),
                member.getRole());
        this.organization = member.getOrganization();
    }
}