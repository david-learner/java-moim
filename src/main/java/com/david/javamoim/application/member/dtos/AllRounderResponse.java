package com.david.javamoim.application.member.dtos;

import com.david.javamoim.domain.Member;
import lombok.Getter;

@Getter
public class AllRounderResponse extends BaseMemberResponse {

    private String organization;
    private String allergicFoods;
    private String selfIntroduction;

    public AllRounderResponse(Member member) {
        super(member.getUid(),
                member.getName(),
                member.getBirthDate(),
                member.getSex(),
                member.getId(),
                member.getEmail(),
                member.getRole());
        this.organization = member.getOrganization();
        this.allergicFoods = member.getAllergicFoods();
        this.selfIntroduction = member.getSelfIntroduction();
    }
}