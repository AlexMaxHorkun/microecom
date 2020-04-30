package com.microecom.authservice;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

public class JwtCustomizableTokenConverter extends JwtAccessTokenConverter {
    private final Map<String, String> customHeaders;

    private final RsaSigner rsaSigner;

    private final JsonParser jsonMapper;

    public JwtCustomizableTokenConverter(KeyPair keyPair, Map<String, String> customHeaders) {
        super();
        super.setKeyPair(keyPair);
        this.customHeaders = customHeaders;
        rsaSigner = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());
        jsonMapper = JsonParserFactory.create();
    }

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = jsonMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Cannot convert access token to JSON", ex);
        }

        return JwtHelper.encode(
                content,
                rsaSigner,
                customHeaders
        ).getEncoded();
    }
}
