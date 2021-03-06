package com.microecom.authservice;

import com.microecom.authservice.model.PasswordEncryptor;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.List;

@EnableAuthorizationServer
@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    private @Autowired PasswordEncryptor passwordEncryptor;

    private final AuthenticationConfiguration auth;

    private KeyPair keyPair;

    public AuthServerConfig(
            AuthenticationConfiguration auth
    ) {
        this.auth = auth;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret(passwordEncryptor.encrypt("secret"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        var chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(
                List.of(new CustomTokenEnchancer(), (JwtCustomizableTokenConverter) accessTokenConverter())
        );

        endpoints.authenticationManager(auth.getAuthenticationManager())
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(chain)
                .tokenStore(tokenStore());
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new JwtCustomizableTokenConverter(getKeyPair(), Collections.singletonMap("kid", "auth-service-kid"));
    }

    private KeyPair getKeyPair() {
        if (keyPair == null) {
            keyPair = (new KeyStoreKeyFactory(new ClassPathResource("auth_pair.jks"), "auth12345aBc".toCharArray()))
                    .getKeyPair("microecom-auth-keypair");
        }

        return keyPair;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore((JwtAccessTokenConverter) accessTokenConverter());
    }

    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) getKeyPair().getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("auth-service-kid");
        return new JWKSet(builder.build());
    }
}
