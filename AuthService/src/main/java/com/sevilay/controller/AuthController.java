package com.sevilay.controller;

import com.sevilay.dto.request.ActivationRequestDto;
import com.sevilay.dto.request.LoginRequestDto;
import com.sevilay.dto.request.RegisterRequestDto;
import com.sevilay.dto.request.UpdateEmailOrUsernameRequestDto;
import com.sevilay.dto.response.RegisterResponseDto;
import com.sevilay.repository.entity.Auth;
import com.sevilay.service.AuthService;
import com.sevilay.utility.JwtTokenManager;
import com.sevilay.utility.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sevilay.constants.RestApi.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtTokenManager tokenManager;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

//    @PostMapping(LOGIN)
//    public ResponseEntity<Boolean> login(@RequestBody LoginRequestDto dto) {
//        return ResponseEntity.ok(authService.login(dto));
//    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }


    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(ActivationRequestDto dto) {
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<Auth>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }


    @GetMapping("/create_token")
    public ResponseEntity<String> createToken(Long id, Role role) {
        return ResponseEntity.ok(tokenManager.createToken(id, role).get());
    }

    @GetMapping("/create_token2")
    public ResponseEntity<String> createToken2(Long id) {
        return ResponseEntity.ok(tokenManager.createToken(id).get());
    }

    @GetMapping("/get_id_from_token")
    public ResponseEntity<Long> getIdFromToken(String token) {
        return ResponseEntity.ok(tokenManager.getIdFromToken(token).get());
    }

    @GetMapping("/get_role_from_token")
    public ResponseEntity<String> getRoleFromToken(String token) {
        return ResponseEntity.ok(tokenManager.getRoleFromToken(token).get());
    }

    @PutMapping("/update_email_or_username")
    public ResponseEntity<Boolean> update(@RequestBody UpdateEmailOrUsernameRequestDto updateEmailOrUsernameRequestDto) {
        return ResponseEntity.ok(authService.updateEmailOrUsername(updateEmailOrUsernameRequestDto));
    }

}
