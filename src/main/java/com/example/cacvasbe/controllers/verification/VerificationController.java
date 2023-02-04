package com.example.cacvasbe.controllers.verification;

import com.example.cacvasbe.services.verification.VerificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/register")
@AllArgsConstructor
public class VerificationController {

//    private final VerificationService verificationService;
//
//    @GetMapping(value = "/verification")
//    public String verifyUser(@Valid @RequestParam("token") String token) throws Exception {
//        verificationService.verifyUser(token);
////        String link = "http://localhost:3000/login";
////        String link = "www.google.com";
//        StringBuilder sb = new StringBuilder();
//        sb.append("Email confirmed, follow the link below to login.");
////        sb.append("<br><br><a href=\"http://localhost:3000/login\" target=\"_blank\">Login</a>");
//
//        String link = sb.toString();
////        return "Registered successfully, follow the link to login, <a href=\""+ link +"\" target=\"_blank\">Login</a>";
//        return link;
//    }
}
