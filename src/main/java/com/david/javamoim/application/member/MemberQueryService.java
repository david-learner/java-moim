package com.david.javamoim.application.member;

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
    private static final String NOT_MATCHED_UID_MESSAGE = "인증된 사용자와 요청된 정보의 아이디가 일치하지 않습니다.";
    private final MemberRepository memberRepository;

    @Autowired
    public MemberQueryService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMember(Long uid, String id) {
        Member foundMember = memberRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException(NOT_EXISTED_ID_MESSAGE);
        });
        if (!foundMember.isUid(uid)) {
            throw new IllegalArgumentException(NOT_MATCHED_UID_MESSAGE);
        }
        return foundMember;
    }
}
