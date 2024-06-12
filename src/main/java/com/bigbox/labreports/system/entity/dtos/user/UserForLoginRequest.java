package com.bigbox.labreports.system.entity.dtos.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForLoginRequest {
    private String username;
    private String password;
}
