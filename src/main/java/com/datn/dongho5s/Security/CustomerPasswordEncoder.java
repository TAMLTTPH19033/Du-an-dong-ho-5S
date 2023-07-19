package com.datn.dongho5s.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomerPasswordEncoder implements PasswordEncoder {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence charSequence) {
        return bCryptPasswordEncoder.encode(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) { // so sanh pass duoc tao ra va chuoi truyen vao
        return s.equals(bCryptPasswordEncoder.encode(charSequence.toString()));
    }
}
