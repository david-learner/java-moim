package com.david.javamoim.application.member.dtos;

import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.Role;
import com.david.javamoim.domain.Sex;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length.List;
import org.springframework.format.annotation.DateTimeFormat;

public record MemberUpdateRequest(
        @NotBlank
        @List({
                @Length(min = 2, message = "이름은 2자이상이어야 합니다."),
                @Length(max = 10, message = "이름은 15자이하여야 합니다.")
        })
        String name,
        @NotBlank
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,
        @NotBlank
        Sex sex,
        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$")
        String password,
        @NotBlank
        String email,
        String organization,
        String allergicFoods,
        String selfIntroduction,
        @NotBlank
        Role role
) {
    public void applyTo(Member member) {
        member.update(this.name, this.birthDate, this.sex, this.password, this.email, this.organization,
                this.allergicFoods, this.selfIntroduction, this.role);
    }
}
