package br.com.loginauth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public ResponseEntity<String> getUser  (){
        return  ResponseEntity.ok("Sucess!");
    }
}
