package com.lsuncar.securityhub.util;

import com.lsuncar.securityhub.properties.AppleProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class AppleJwtCreator {

    private final ECPrivateKey privateKey;

    private final AppleProperties appleProperties;

    public AppleJwtCreator(
            AppleProperties appleProperties
    ) throws Exception {

        this.appleProperties = appleProperties;

        String pem = new String(
                appleProperties.privateKeyPath().getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        // TODO: Replace manual PEM parsing.
        byte[] decoded = Base64.getDecoder().decode(
                pem.replaceAll(
                        "-----BEGIN PRIVATE KEY-----|-----END PRIVATE KEY-----|\\s",
                        ""
                )
        );

        this.privateKey = (ECPrivateKey) KeyFactory
                .getInstance("EC")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    public String createJwt() {

        try {
            Instant now = Instant.now();

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issuer(appleProperties.teamId())
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plus(Duration.ofDays(90))))
                    .audience("https://appleid.apple.com")
                    .subject(appleProperties.clientId())
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.ES256)
                            .keyID(appleProperties.keyId())
                            .build(),
                    claimsSet
            );

            signedJWT.sign(new ECDSASigner(privateKey));

            return signedJWT.serialize();
        } catch (Exception e) {
            //TODO logger
            System.out.println(e);
            return null;
        }
    }
}