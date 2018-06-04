package com.thinkgem.jeesite.modules.sys.security;

import com.thinkgem.jeesite.modules.sys.service.SystemService;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SystemCredentialsMatcher extends SimpleCredentialsMatcher {

    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        Object accountCredentials = getCredentials(info);

        return SystemService.validatePassword(String.valueOf(token.getPassword()), accountCredentials.toString());
    }
    

}