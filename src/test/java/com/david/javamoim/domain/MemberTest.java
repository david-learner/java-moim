package com.david.javamoim.domain;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @Test
    @DisplayName("모임 주최자 역할로 회원를 생성한다.")
    void createAsHost() {
        Member host = Member.createMember(
                "david",
                LocalDate.parse("1950-06-25"),
                Sex.MALE,
                "kr.seoul.david",
                "Iamdavid12!@",
                "david@david.com",
                "david company",
                null,
                null,
                Role.HOST);

        Assertions.assertThat(host.isRole(Role.HOST)).isTrue();
    }

    @Test
    @DisplayName("모임 참여자 역할로 회원를 생성한다.")
    void createAsGuest() {
        Member guest = Member.createMember(
                "david",
                LocalDate.parse("1950-06-25"),
                Sex.MALE,
                "kr.seoul.david",
                "Iamdavid12!@",
                "david@david.com",
                null,
                "egg, bean",
                "Hi, I am David.",
                Role.GUEST);

        Assertions.assertThat(guest.isRole(Role.GUEST)).isTrue();
    }
}
