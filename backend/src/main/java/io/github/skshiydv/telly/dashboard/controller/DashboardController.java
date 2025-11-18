package io.github.skshiydv.telly.dashboard.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardController {
    @GetMapping
    public Map<String, Object> healthCheck(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }
}
