package com.datn.dongho5s.Controller;

import com.datn.dongho5s.Service.NhanVienService;
import com.datn.dongho5s.Service.impl.NhanVienServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final NhanVienService service;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MainController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          NhanVienService service,
                          BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String processLogin(String email, String password, RedirectAttributes redirectAttributes) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Tạo đối tượng UsernamePasswordAuthenticationToken với thông tin đăng nhập
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            // Xác thực thông tin đăng nhập sử dụng AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(token);

            // Nếu xác thực thành công, đăng nhập thành công và chuyển hướng đến trang chủ
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "redirect:/";
            } else {
                // Nếu thông tin đăng nhập không hợp lệ, thêm thông báo lỗi và chuyển hướng đến trang đăng nhập
                redirectAttributes.addFlashAttribute("error", "Tài khoản hoặc mật khẩu không đúng");
                return "redirect:login";
            }
        } catch (UsernameNotFoundException ex) {
            // Nếu tài khoản không tồn tại, thêm thông báo lỗi và chuyển hướng đến trang đăng nhập
            redirectAttributes.addFlashAttribute("error", "Tài khoản không tồn tại");
            return "redirect:login";
        } catch (AuthenticationException ex) {
            // Nếu xảy ra lỗi xác thực, thêm thông báo lỗi và chuyển hướng đến trang đăng nhập
            redirectAttributes.addFlashAttribute("error", "Lỗi xác thực: " + ex.getMessage());
            return "redirect:login";
        }
    }
}
