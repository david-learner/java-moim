package com.david.javamoim.api;

import com.david.javamoim.application.member.MemberCommandService;
import com.david.javamoim.application.member.MemberQueryService;
import com.david.javamoim.application.member.dtos.JoinRequest;
import com.david.javamoim.application.member.dtos.MemberResponse;
import com.david.javamoim.application.member.dtos.MemberResponseFactory;
import com.david.javamoim.application.member.dtos.MemberUpdateRequest;
import com.david.javamoim.application.member.dtos.RoleRequest;
import com.david.javamoim.domain.Member;
import com.david.javamoim.support.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @Autowired
    public MemberRestController(MemberCommandService memberCommandService,
                                MemberQueryService memberQueryService) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
    }

    @PostMapping("/api/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequest request) {
        memberCommandService.join(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/members/roles/add")
    public ResponseEntity<Void> addRole(@Access String memberId, @RequestBody RoleRequest request) {
        memberCommandService.addRole(memberId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/members/{memberUid}")
    public ResponseEntity<ApiResponse<MemberResponse>> getMemberInformation(@Access String memberId,
                                                                            @PathVariable Long memberUid) {
        Member member = memberQueryService.findMember(memberUid, memberId);
        MemberResponse memberResponse = MemberResponseFactory.create(member);
        return ResponseEntity.ok(ApiResponse.success(memberResponse));
    }

    @PatchMapping("/api/members/{memberUid}")
    public ResponseEntity<Void> updateMemberInformation(@Access String memberId, @PathVariable Long memberUid,
                                                        @RequestBody MemberUpdateRequest request) {
        memberCommandService.updateMember(memberUid, memberId, request);
        return ResponseEntity.ok().build();
    }
}
