package com.david.javamoim.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    // 회원번호 - 시스템에서 자동으로 부여되는 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String name;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Column(unique = true)
    private String id;
    private String password;
    @Column(unique = true)
    private String email;
    private String organization;
    private String allergicFoods;
    private String selfIntroduction;
    @Enumerated(EnumType.STRING)
    private Role role;

    protected Member() {
    }

    public static Member createMember(String name, LocalDate birthDate, Sex sex, String id, String password,
                                      String email,
                                      String organization, String allergicFoods, String selfIntroduction, Role role) {
        if (role == Role.HOST) {
            return createHost(name, birthDate, sex, id, password, email, organization, role);
        }
        return createGuest(name, birthDate, sex, id, password, email, allergicFoods, selfIntroduction, role);

    }

    private static Member createGuest(String name, LocalDate birthDate, Sex sex, String id, String password,
                                      String email, String allergicFoods, String selfIntroduction, Role role) {
        return Member.builder()
                .name(name)
                .birthDate(birthDate)
                .sex(sex)
                .id(id)
                .password(password)
                .email(email)
                .allergicFoods(allergicFoods)
                .selfIntroduction(selfIntroduction)
                .role(role)
                .build();
    }

    private static Member createHost(String name, LocalDate birthDate, Sex sex, String id, String password,
                                     String email, String organization, Role role) {
        return Member.builder()
                .name(name)
                .birthDate(birthDate)
                .sex(sex)
                .id(id)
                .password(password)
                .email(email)
                .organization(organization)
                .role(role)
                .build();
    }

    public boolean isRole(Role role) {
        return this.role == role;
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
}
