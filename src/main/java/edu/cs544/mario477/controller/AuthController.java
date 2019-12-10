package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.RegistrationDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @PostMapping("/register")
    public Response<RegistrationDTO> register(@RequestBody @Valid RegistrationDTO dto) {
        return ResponseBuilder.buildSuccess(dto);
    }
}
