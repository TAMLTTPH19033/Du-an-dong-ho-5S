package com.datn.dongho5s.Controller.RestController.KhachHang;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Exception.ErrorResponse;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Request.LoginRequest;
import com.datn.dongho5s.Response.LoginResponse;
import com.datn.dongho5s.Security.AccountFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class LoginRestController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private AccountFilterService tokenProvider;
    @Autowired
    KhachHangRepository khachHangRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            KhachHang userEntity = khachHangRepository.getKhachHangByEmail(authentication.getName());
            System.out.println(userEntity+ " userEntity");
            String jwt = tokenProvider.generateToken(authentication);
            System.out.println(jwt +"jwt");
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwt);
            loginResponse.setStatus(200);
            loginResponse.setIdKhachHang(userEntity.getIdKhachHang());
            loginResponse.setMessage("Đăng nhập thành công");
            loginResponse.setUsername(userEntity.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Sai mật khẩu hoặc email "));
        }
    }
}
