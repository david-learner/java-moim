package com.david.javamoim.application.member.dtos;

import com.david.javamoim.application.member.dtos.AllRounderResponse;
import com.david.javamoim.application.member.dtos.GuestResponseBase;
import com.david.javamoim.application.member.dtos.HostResponseBase;
import com.david.javamoim.application.member.dtos.MemberResponse;
import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.Role;

public class MemberResponseFactory {

    public static MemberResponse create(Member member) {
        if (member.isRole(Role.HOST)) {
            return new HostResponseBase(member);
        }
        if (member.isRole(Role.GUEST)) {
            return new GuestResponseBase(member);
        }
        return new AllRounderResponse(member);
    }
}
