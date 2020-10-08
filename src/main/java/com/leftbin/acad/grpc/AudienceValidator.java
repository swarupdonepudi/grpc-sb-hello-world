package com.leftbin.acad.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Validates that the JWT token contains the intended audience in its claims.
 */
class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final String audience;
    Logger l = LoggerFactory.getLogger(AudienceValidator.class);
    AudienceValidator(String audience) {
        this.audience = audience;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        l.info("swarup: validating the token {}", jwt.getSubject());
        jwt.getClaims().forEach((k,v)->l.info("key: {}, val: {}", k, v));
        OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);

        if (jwt.getAudience().contains(audience)) {
            l.info("swarup: token contains the correct audience {}", audience);
            return OAuth2TokenValidatorResult.success();
        } else {
            l.info("swarup: token does not contain the correct audience {}", audience);
        }

        return OAuth2TokenValidatorResult.failure(error);
    }
}
