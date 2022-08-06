package com.david.javamoim.application.member;

import com.david.javamoim.application.member.dtos.JoinRequest;
import com.david.javamoim.application.member.dtos.MemberUpdateRequest;
import com.david.javamoim.application.member.dtos.RoleRequest;
import com.david.javamoim.domain.Member;
import com.david.javamoim.domain.MemberRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberCommandService {

    private static final String NOT_MATCHED_UID_MESSAGE = "인증된 사용자와 요청된 정보의 아이디가 일치하지 않습니다.";
    private static final String NOT_EXISTED_ID_MESSAGE = "존재하지 않는 아이디입니다.";
    private final MemberRepository memberRepository;

    @Autowired
    public MemberCommandService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(JoinRequest request) {
        memberRepository.save(request.toMember());
    }

    public void addRole(String memberId, RoleRequest request) {
        Member foundMember = findMember(memberId);
        request.applyTo(foundMember);
    }

    public void updateMember(Long memberUid, String memberId, MemberUpdateRequest request) {
        Member foundMember = findMember(memberId);
        validateMemberUid(memberUid, foundMember);
        request.applyTo(foundMember);
    }

    private Member findMember(String memberId) {
        Member foundMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchElementException(NOT_EXISTED_ID_MESSAGE);
        });
        return foundMember;
    }

    private void validateMemberUid(Long uid, Member foundMember) {
        if (!foundMember.isUid(uid)) {
            throw new IllegalArgumentException(NOT_MATCHED_UID_MESSAGE);
        }
    }
}
