package com.david.javamoim.application;

import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.MemberRepository;
import com.david.javamoim.security.JwtTokenProvider;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class LoginQueryService {

    private static final String NOT_EXISTED_ID_MESSAGE = "존재하지 않는 아이디입니다.";
    private static final String INVALID_PASSWORD_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public LoginQueryService(JwtTokenProvider jwtTokenProvider,
                             MemberRepository memberRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }

    public String login(LoginRequest request) {
        Member foundMember = memberRepository.findById(request.id()).orElseThrow(() -> {
            throw new NoSuchElementException(NOT_EXISTED_ID_MESSAGE);
        });
        if (!foundMember.isValidPassword(request.password())) {
            throw new IllegalArgumentException(INVALID_PASSWORD_MESSAGE);
        }
        return jwtTokenProvider.createToken(request.id());
    }
}
