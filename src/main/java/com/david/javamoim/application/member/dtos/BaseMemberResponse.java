package com.david.javamoim.application.member.dtos;

import com.david.javamoim.domain.Role;
import com.david.javamoim.domain.Sex;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public abstract class BaseMemberResponse implements MemberResponse {
    private Long uid;
    private String name;
    private LocalDate birthDate;
    private Sex sex;
    private String id;
    private String email;
    private Role role;

    public BaseMemberResponse(Long uid, String name, LocalDate birthDate, Sex sex, String id, String email,
                              Role role) {
        this.uid = uid;
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
