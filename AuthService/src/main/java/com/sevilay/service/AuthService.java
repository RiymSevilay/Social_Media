package com.sevilay.service;

import com.sevilay.dto.request.ActivationRequestDto;
import com.sevilay.dto.request.LoginRequestDto;
import com.sevilay.dto.request.RegisterRequestDto;
import com.sevilay.dto.request.UpdateEmailOrUsernameRequestDto;
import com.sevilay.dto.response.RegisterResponseDto;
import com.sevilay.exception.AuthServiceException;
import com.sevilay.exception.ErrorType;
import com.sevilay.manager.UserManager;
import com.sevilay.mapper.AuthMapper;
import com.sevilay.repository.AuthRepository;
import com.sevilay.repository.entity.Auth;
import com.sevilay.utility.CodeGenerator;
import com.sevilay.utility.JwtTokenManager;
import com.sevilay.utility.ServiceManager;
import com.sevilay.utility.enums.Status;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final AuthRepository authRepository;
    private final UserManager userManager;
    private final JwtTokenManager jwtTokenManager;

    public AuthService(AuthRepository authRepository, UserManager userManager, JwtTokenManager jwtTokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = AuthMapper.INSTANCE.fromRegisterRequestToAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
        return AuthMapper.INSTANCE.fromAuthToRegisterResponse(auth);
    }


    public String login(LoginRequestDto dto) {
        Optional<Auth> authOptional = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (authOptional.isEmpty()) {
            throw new AuthServiceException(ErrorType.LOGIN_ERROR);
        }
        if (!authOptional.get().getStatus().equals(Status.ACTIVE)) {
            throw new AuthServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        Optional<String> token = jwtTokenManager.createToken(authOptional.get().getId(), authOptional.get().getRole());
//        if(token.isEmpty()){
//            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
//        }

        return jwtTokenManager.createToken(authOptional.get().getId(), authOptional.get().getRole())
                .orElseThrow(() -> {
                    throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
                });
    }


    public Boolean activateStatus(ActivationRequestDto dto) {
        Optional<Auth> auth = findById(dto.getId());
        if ((auth.isEmpty())) {
            throw new AuthServiceException((ErrorType.USER_NOT_FOUND));
        }
        if (dto.getActivationCode().equals(auth.get().getActivationCode())) {
            auth.get().setStatus(Status.ACTIVE);
            update(auth.get());
            return true;
        } else {
            throw new AuthServiceException(ErrorType.ACTIVATION_CODE_ERROR);
        }
    }

    public Boolean updateEmailOrUsername(UpdateEmailOrUsernameRequestDto updateEmailOrUsernameRequestDto) {
        Optional<Auth> auth = authRepository.findById(updateEmailOrUsernameRequestDto.getId());
        if (auth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setUsername(updateEmailOrUsernameRequestDto.getUsername());
        auth.get().setEmail(updateEmailOrUsernameRequestDto.getEmail());
        update(auth.get());
        return true;
    }
}
