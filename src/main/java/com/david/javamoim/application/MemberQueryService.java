package com.david.javamoim.application;

import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.MemberRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberQueryService {

    private static final String NOT_EXISTED_ID_MESSAGE = "존재하지 않는 아이디입니다.";
    private final MemberRepository memberRepository;

    @Autowired
    public MemberQueryService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMember(String memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchElementException(NOT_EXISTED_ID_MESSAGE);
        });
    }
}
