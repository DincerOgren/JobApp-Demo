package com.jobapp.auth.user;

import com.jobapp.auth.models.LoginRequestDTO;
import com.jobapp.auth.models.LoginResponseDTO;
import com.jobapp.auth.models.UserRequestDTO;
import com.jobapp.auth.models.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.registerUser(userRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        LoginResponseDTO responseDTO = userService.loginUser(loginDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
