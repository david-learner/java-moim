package com.david.javamoim.api;

import com.david.javamoim.application.JoinRequest;
import com.david.javamoim.application.MemberCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {

    private final MemberCommandService memberCommandService;

    @Autowired
    public MemberRestController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }

    @PostMapping("/api/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequest request) {
        memberCommandService.join(request);
        return ResponseEntity.ok().build();
    }
}
