package com.thinkgem.jeesite.modules.sys.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtils {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String encodePwd(CharSequence charSequence){
        return ENCODER.encode(charSequence);
    }

    public static boolean matchPwd(CharSequence rawPassword, String encodedPassword){
        return ENCODER.matches(rawPassword,encodedPassword);
    }
}

