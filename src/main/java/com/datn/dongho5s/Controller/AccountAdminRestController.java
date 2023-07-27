//package com.datn.dongho5s.Controller;
//
//import com.datn.dongho5s.Entity.KhachHang;
//import com.datn.dongho5s.Entity.NhanVien;
//import com.datn.dongho5s.Exception.ErrorResponse;
//import com.datn.dongho5s.Repository.KhachHangRepository;
//import com.datn.dongho5s.Repository.NhanVienRepository;
//import com.datn.dongho5s.Request.LoginRequest;
//import com.datn.dongho5s.Response.LoginAdminResponse;
//import com.datn.dongho5s.Response.LoginResponse;
//import com.datn.dongho5s.Security.AccountFilterService;
//import com.datn.dongho5s.Service.AccountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//@RestController
//@CrossOrigin("*")
//@RequestMapping("/api/")
//public class AccountAdminRestController {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//    @Autowired
//    private AccountFilterService tokenProvider;
//    @Autowired
//    NhanVienRepository nhanVienRepository;
//
//    @Autowired
//    AccountService accountService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private HttpSession session;
//    public Authentication authenticate(String username, String password) throws Exception {
//        try {
//            return   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            System.out.println(e);
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
//    @PostMapping("/loginadmin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//        try {
//            Authentication authentication = authenticate(
//                    loginRequest.getUsername(), loginRequest.getPassword()
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            NhanVien userEntity = nhanVienRepository.getNhanVienByEmail(authentication.getName());
//            String jwt = tokenProvider.generateToken(authentication);
//            LoginAdminResponse loginadminResponse = new LoginAdminResponse();
//            loginadminResponse.setToken(jwt);
//            loginadminResponse.setStatus(200);
//            loginadminResponse.setId(userEntity.getId());
//            loginadminResponse.setDescription("Đăng nhập thành công");
//            loginadminResponse.setName(userEntity.getEmail());
//            this.session.setAttribute("userName",userEntity.getUsername());
//            return ResponseEntity.status(HttpStatus.OK).body(loginadminResponse);
//        } catch (Exception e) {
//            System.out.println(e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Sai mật khẩu hoặc email "));
//        }
//    }
//
//}
