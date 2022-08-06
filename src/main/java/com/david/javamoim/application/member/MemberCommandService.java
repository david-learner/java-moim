package com.david.javamoim.application.member;

import com.david.javamoim.application.member.dtos.JoinRequest;
import com.david.javamoim.application.member.dtos.RoleRequest;
import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberCommandService {

    private final MemberQueryService memberQueryService;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberCommandService(MemberQueryService memberQueryService,
                                MemberRepository memberRepository) {
        this.memberQueryService = memberQueryService;
        this.memberRepository = memberRepository;
    }

    public void join(JoinRequest request) {
        memberRepository.save(request.toMember());
    }

    public void addRole(String memberId, RoleRequest request) {
        Member foundMember = memberQueryService.findMember(memberId);
        request.applyTo(foundMember);
    }
}
