package com.david.javamoim.api;

import com.david.javamoim.application.MemberCommandService;
import com.david.javamoim.application.MemberJoinRequest;
import com.david.javamoim.application.MemberJoinResponse;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {

    private static final String MEMBER_API_URL_PREFIX = "/members";
    private static final String URL_SEPARATOR = "/";
    private final MemberCommandService memberCommandService;

    @Autowired
    public MemberRestController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }

    @PostMapping("/api/join")
    public ResponseEntity<Void> join(@RequestBody MemberJoinRequest request) {
        MemberJoinResponse response = new MemberJoinResponse(memberCommandService.join(request));
        // ex) /members/1
        URI joinedMemberUri = URI.create(MEMBER_API_URL_PREFIX + URL_SEPARATOR + response.uid());
        return ResponseEntity.created(joinedMemberUri).build();
    }
}
