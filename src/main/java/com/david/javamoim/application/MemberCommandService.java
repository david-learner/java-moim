package com.david.javamoim.application;

import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberCommandService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(MemberJoinRequest request) {
        Member savedMember = memberRepository.save(request.toMember());
        return savedMember.getUid();
    }
}
