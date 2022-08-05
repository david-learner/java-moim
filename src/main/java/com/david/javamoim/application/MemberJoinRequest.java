package com.david.javamoim.application;

import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.Role;
import com.david.javamoim.domain.Sex;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length.List;
import org.springframework.format.annotation.DateTimeFormat;

public record MemberJoinRequest(
        @NotNull
        @List({
                @Length(min = 2, message = "이름은 2자이상이어야 합니다."),
                @Length(max = 10, message = "이름은 15자이하여야 합니다.")
        })
        String name,
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        String birthDate,
        @NotNull
        Sex sex,
        @NotNull
        String id,
        @NotNull
        String password,
        @NotNull
        String email,
        String organization,
        String allergicFoods,
        String selfIntroduction,
        @NotNull
        Role role
) {
    public Member toMember() {
        return Member.createMember(name, LocalDate.parse(birthDate), sex, id, password, email, organization,
                allergicFoods, selfIntroduction, role);
    }
}
