package com.astar.common.library.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JWTUtility {

//    //*In Minutes, default 5 minutes if missing
//    @Value("${token.alive.time:5}")
//    public static short tokenAliveTime;
//
//    @Value("token.issuer:ASTAR")
//    public static String issuer;

    /**
     * @param claims
     * @return
     * @throws JOSEException
     * @throws KeyLengthException
     */
//    TODO ENHANCE
    public static String generateJWTToken(
            Map<String, Object> claims,
            JWEAlgorithm algorithm,
            EncryptionMethod encryptionMethod,
            RSAPublicKey publicKey,
            Duration timeToLive
    ) throws JOSEException {

        // ENHANCE
        //*1. The HEADER
        JWEHeader header = new JWEHeader(algorithm, encryptionMethod);
        JWTClaimsSet.Builder JWTBuilder = new JWTClaimsSet.Builder();
        for (Map.Entry<String, Object> data : claims.entrySet()) {
            JWTBuilder = JWTBuilder.claim(data.getKey(), data.getValue());
        }

        Instant now = Instant.now();
        JWTBuilder = JWTBuilder
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plus(timeToLive)));
        //*2. The PAYLOAD
        JWTClaimsSet claimsSet = JWTBuilder.build();
        //*3. The Signature
        EncryptedJWT JWT = new EncryptedJWT(header, claimsSet);
        RSAEncrypter encrypter = new RSAEncrypter(publicKey);
        encrypter.getJCAContext().setProvider(BouncyCastleProviderSingleton.getInstance());
        JWT.encrypt(encrypter);
        return JWT.serialize();
    }

    public static EncryptedJWT decryptJWTToken(
            String jwtString,
            RSAPrivateKey privateKey
    ) throws ParseException, JOSEException {
        EncryptedJWT JWT = EncryptedJWT.parse(jwtString);
        RSADecrypter decrypter = new RSADecrypter(privateKey);
        decrypter.getJCAContext().setProvider(BouncyCastleProviderSingleton.getInstance());
        JWT.decrypt(decrypter);
        return JWT;

    }

    /**
     * @param token
     * @param claim
     * @param secretKey
     * @return
     * @throws BadJOSEException
     * @throws ParseException
     * @throws JOSEException
     */
    public static String getClaimsValue(
            EncryptedJWT JWT, String claim
    ) throws ParseException {
        return JWT.getJWTClaimsSet().getClaimAsString(claim);
    }
}
