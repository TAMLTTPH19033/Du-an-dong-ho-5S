package com.datn.dongho5s.Controller;

import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Repository.NhanVienRepository;
import com.datn.dongho5s.Request.LoginRequest;
import com.datn.dongho5s.Security.AccountFilterService;
import com.datn.dongho5s.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private AccountFilterService tokenProvider;
    @Autowired
    NhanVienRepository nhanVienRepository;


    @GetMapping("/")
    public String viewHome(){
        return "admin/index";
    }

    @GetMapping("/login-admin")
    public String viewLogin(){
        return "admin/login";
    }

//    @PostMapping("/loginAdmin")
//    public ModelAndView authenticateUser(@Valid LoginRequest loginRequest, BindingResult bindingResult, Model model) {
//        // Xử lý đăng nhập và kiểm tra kết quả
//        try {
//            Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            NhanVien userEntity = nhanVienRepository.getNhanVienByEmail(authentication.getName());
//            String jwt = tokenProvider.generateToken(authentication);
//
//            // Thực hiện chuyển hướng đến view "login-success.html" và truyền dữ liệu cần hiển thị
//            ModelAndView mv = new ModelAndView("admin/index");
//            mv.addObject("token", jwt);
//            mv.addObject("id", userEntity.getId());
//            mv.addObject("description", "Đăng nhập thành công");
//            mv.addObject("name", userEntity.getEmail());
//            return mv;
//        } catch (Exception e) {
//            System.out.println(e);
//            // Xử lý lỗi và chuyển hướng đến view "login-fail.html"
//            ModelAndView mv = new ModelAndView("login-fail");
//            mv.addObject("errorMessage", "Sai mật khẩu hoặc email");
//            return mv;
//        }
//    }
    public Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
