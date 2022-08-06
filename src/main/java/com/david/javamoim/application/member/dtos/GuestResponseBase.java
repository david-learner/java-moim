package com.david.javamoim.application.member.dtos;

import com.david.javamoim.domain.Member;
import lombok.Getter;

@Getter
public class GuestResponseBase extends BaseMemberResponse {

    private String allergicFoods;
    private String selfIntroduction;

    public GuestResponseBase(Member member) {
        super(member.getUid(),
                member.getName(),
                member.getBirthDate(),
                member.getSex(),
                member.getId(),
                member.getEmail(),
                member.getRole());
        this.allergicFoods = member.getAllergicFoods();
        this.selfIntroduction = member.getSelfIntroduction();
    }
}