package com.lchen.funnel.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lchen.funnel.auth.Sessions;
import com.lchen.funnel.auth.Sign;
import com.lchen.funnel.config.EnvConfig;
import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.filter.RequestData;
import com.lchen.funnel.model.AuthConstant;
import com.lchen.funnel.model.SecurityConstant;
import com.lchen.funnel.model.Service;
import com.lchen.funnel.model.ServiceDirectory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthRequestInterceptor implements PreForwardRequestInterceptor {

    private final String signingSecret;
    private final EnvConfig envConfig;

    private final Map<String, String> bannedUsers = new HashMap<String, String>() {{
        put("d7b9dbed-9719-4856-5f19-23da2d0e3dec", "hidden");
    }};

    public AuthRequestInterceptor(String signingSecret, EnvConfig envConfig) {
        this.signingSecret = signingSecret;
        this.envConfig = envConfig;
    }

    @Override
    public void intercept(RequestData data, RouterMappingProperties mapping) {
        // sanitize incoming requests and set authorization information
        String authorization = this.setAuthHeader(data, mapping);

        this.validateRestrict(mapping);
        this.validateSecurity(data, mapping, authorization);

        // TODO - filter restricted headers
    }

    private String setAuthHeader(RequestData data, RouterMappingProperties mapping) {
        // default to anonymous web when prove otherwise
        String authorization = AuthConstant.AUTHORIZATION_ANONYMOUS_WEB;
        HttpHeaders headers = data.getHeaders();
        Session session = this.getSession(data.getOriginRequest());
        if (session != null) {
            if (session.isSupport()) {
                authorization = AuthConstant.AUTHORIZATION_SUPPORT_USER;
            } else {
                authorization = AuthConstant.AUTHORIZATION_AUTHENTICATED_USER;
            }

            this.checkBannedUsers(session.getUserId());

            headers.set(AuthConstant.CURRENT_USER_HEADER, session.getUserId());
        } else {
            // prevent hacking
            headers.remove(AuthConstant.CURRENT_USER_HEADER);
        }
        headers.set(AuthConstant.AUTHORIZATION_HEADER, authorization);

        return authorization;
    }

    private void checkBannedUsers(String userId) {
        if (bannedUsers.containsKey(userId)) {
            log.warn(String.format("Banned user accessing service - user %s", userId));
        }
    }

    private Service getService(RouterMappingProperties mapping) {
        String host = mapping.getHost();
        String subDomain = host.replace("." + envConfig.getExternalApex(), "");
        Service service = ServiceDirectory.getMapping().get(subDomain.toLowerCase());
        if (service == null) {
        }
        return service;
    }

    private void validateRestrict(RouterMappingProperties mapping) {
        Service service = this.getService(mapping);
        if (service.isRestrictDev() && !envConfig.isDebug()) {
        }
    }

    // check response Authorization and see if it's ok
    // with the requested service
    private void validateSecurity(RequestData data, RouterMappingProperties mapping, String authorization) {
        // Check perimeter authorization
        if (AuthConstant.AUTHORIZATION_ANONYMOUS_WEB.equals(authorization)) {
            Service service = this.getService(mapping);
            if (SecurityConstant.SEC_PUBLIC != service.getSecurity()) {
                log.info("Anonymous user want to access secure service, redirect to login");
                // send to login
                String scheme = "https";
                if (envConfig.isDebug()) {
                    scheme = "http";
                }

                int port = data.getOriginRequest().getServerPort();

                try {
                    URI redirectUrl = new URI(scheme,
                            null,
                            "www." + envConfig.getExternalApex(),
                            port,
                            "/login/", null, null);

                    String returnTo = data.getHost() + data.getUri();
                    String fullRedirectUrl = redirectUrl.toString() + "?return_to=" + returnTo;

                    data.setNeedRedirect(true);
                    data.setRedirectUrl(fullRedirectUrl);
                } catch (URISyntaxException e) {
                    log.error("Fail to build redirect url", e);
                }
            }
        }
    }

    private Session getSession(HttpServletRequest request) {
        String token = Sessions.getToken(request);
        if (token == null) return null;
        try {
            DecodedJWT decodedJWT = Sign.verifySessionToken(token, signingSecret);
            String userId = decodedJWT.getClaim(Sign.CLAIM_USER_ID).asString();
            boolean support = decodedJWT.getClaim(Sign.CLAIM_SUPPORT).asBoolean();
            Session session = Session.builder().userId(userId).support(support).build();
            return session;
        } catch (Exception e) {
            log.error("fail to verify token", "token", token, e);
            return null;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Session {
        private String userId;
        private boolean support;
    }
}
