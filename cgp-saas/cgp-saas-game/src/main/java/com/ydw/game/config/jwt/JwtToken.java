package com.ydw.game.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

import java.security.Principal;

/**
 * JwtToken
 * @author Wang926454
 * @date 2018/8/30 14:06
 */
public class JwtToken implements AuthenticationToken,Principal{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5544896206609002500L;
	/**
     * Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

	@Override
	public String getName() {
		return token;
	}
}
