package com.datn.dongho5s.Controller.RestController.KhachHang;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Exception.ErrorResponse;

import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Request.LoginRequest;
import com.datn.dongho5s.Request.RegisterRequest;
import com.datn.dongho5s.Response.LoginResponse;
import com.datn.dongho5s.Security.AccountFilterService;
import com.datn.dongho5s.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class AccountRestController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private AccountFilterService tokenProvider;
    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Authentication authenticate(String username, String password) throws Exception {
        try {
          return   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser( @Valid @RequestBody LoginRequest loginRequest) {
                try {
                Authentication authentication = authenticate(
                                loginRequest.getUsername(), loginRequest.getPassword()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                KhachHang userEntity = khachHangRepository.getKhachHangByEmail(authentication.getName());
                String jwt = tokenProvider.generateToken(authentication);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(jwt);
                loginResponse.setStatus(200);
                loginResponse.setIdKhachHang(userEntity.getIdKhachHang());
                loginResponse.setMessage("Đăng nhập thành công");
                loginResponse.setUsername(userEntity.getEmail());
                return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
            } catch (Exception e) {
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Sai mật khẩu hoặc email "));
            }
    }

    @PostMapping("/register")
    public ResponseEntity<?> singup( @Valid @RequestBody RegisterRequest registerRequest){
        return accountService.register(registerRequest);
    }

    @GetMapping("/getTP")
    public ResponseEntity<?> getTP() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getListTP());
    }

    @GetMapping("/getQH/{idTinh}")
    public ResponseEntity<?> getQH(@PathVariable("idTinh") Integer idTinh) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getListQuan(idTinh));
    }
    @GetMapping("/getPX/{idQuan}")
    public ResponseEntity<?> getPX(@PathVariable("idQuan") Integer idQuan) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getListPhuong(idQuan));
    }
}
