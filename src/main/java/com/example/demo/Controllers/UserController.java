package com.example.demo.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @GetMapping("/get")
    public String getSome(){
        return "hi";
    }

}
