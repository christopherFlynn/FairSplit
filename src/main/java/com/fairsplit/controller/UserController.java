package com.fairsplit.controller;

import com.fairsplit.dto.UserResponse;
import com.fairsplit.entity.User;
import com.fairsplit.util.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        User currentUser = AuthUtil.getCurrentUser();

        return ResponseEntity.ok(UserResponse.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .build());
    }
}
