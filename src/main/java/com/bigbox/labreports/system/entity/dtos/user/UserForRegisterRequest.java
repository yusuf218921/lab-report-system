package com.bigbox.labreports.system.entity.dtos.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UserForRegisterRequest {
    private String username;
    private String email;
    private String password;
}
