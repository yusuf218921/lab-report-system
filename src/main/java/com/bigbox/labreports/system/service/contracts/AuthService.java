package com.bigbox.labreports.system.service.contracts;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.user.UserForLoginRequest;
import com.bigbox.labreports.system.entity.dtos.user.UserForRegisterRequest;

public interface AuthService {
    Result register(UserForRegisterRequest request);
    DataResult<String> login(UserForLoginRequest request);
}
