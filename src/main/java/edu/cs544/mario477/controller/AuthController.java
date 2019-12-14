package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Response<UserDTO> register(@RequestBody @Valid RegistrationDTO dto) {
        UserDTO userDTO = userService.register(dto);
        return ResponseBuilder.buildSuccess(userDTO);
    }
}
