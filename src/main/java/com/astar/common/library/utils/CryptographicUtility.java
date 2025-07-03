package com.astar.common.library.utils;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

//TODO ADD MORE

/**
 * author : Arif
 * Notes :
 * 1. Method naming conventions [RETURN TYPES][ALGORITHM][ACTION]
 */
public abstract class CryptographicUtility {

    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_TAG_LENGTH = 128; // !BITS
    private static final int GCM_IV_LENGTH = 12; // !2 BYTES = 96 BITS
    private static final int SALT_LENGTH = 64;
    private static boolean isInitializedEncryption = false;

    /**
     * Run to add BouncyCastleProvider
     */
    public static void initializeEncryption() throws NullPointerException, SecurityException {
        Security.addProvider(new BouncyCastleProvider());
        isInitializedEncryption = true;
    }

    /**
     * *********************************
     * *--------  AES SECTION  --------*
     * *********************************
     */
    /**
     * @param plainText
     * @return
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static String StrAESEncrypt(
            String plainText,
            SecretKey secretKey
    ) throws Exception, NoSuchAlgorithmException, NoSuchProviderException {
        if (!isInitializedEncryption) throw new Exception("Error");
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        byte[] ivAndCipherText = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, ivAndCipherText, 0, iv.length);
        System.arraycopy(cipherText, 0, ivAndCipherText, iv.length, cipherText.length);
        return Base64.getEncoder().encodeToString(ivAndCipherText);
    }

    /**
     * @param plainText
     * @param secretKey
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public static Pair<String, String> PairAESEncrypt(
            String plainText,
            SecretKey secretKey
    ) throws Exception {
        if (!isInitializedEncryption) throw new Exception("Error");
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        String ivBase64Str = Base64.getEncoder().encodeToString(iv);
        String cipherTextBase64 = Base64.getEncoder().encodeToString(cipherText);
        return Pair.of(ivBase64Str, cipherTextBase64);
    }


    /**
     * @param ivAndCipherTextBase64
     * @param password
     * @param salt
     * @return
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public static String AESDecrypt(
            String ivAndCipherTextBase64,
            String password,
            String salt
    ) throws Exception {
        if (!isInitializedEncryption) throw new Exception("Encryption not initialized");
        byte[] ivAndCipherText = Base64.getDecoder().decode(ivAndCipherTextBase64);
        byte[] iv = new byte[GCM_IV_LENGTH];
        byte[] cipherText = new byte[ivAndCipherText.length - GCM_IV_LENGTH];

        System.arraycopy(ivAndCipherText, 0, iv, 0, iv.length);
        System.arraycopy(ivAndCipherText, GCM_IV_LENGTH, cipherText, 0, cipherText.length);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        SecretKey secretKey = AESGetKeyFromPassword(password, salt);
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING, "BC");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] plainTextBytes = cipher.doFinal(cipherText);
        return new String(plainTextBytes);
    }

    /**
     * @param ivBase64Str
     * @param cipherTextBase64Str
     * @param password
     * @param salt
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     * @throws NoSuchPaddingException
     */
    public static String AESDecrypt(
            String ivBase64Str,
            String cipherTextBase64Str,
            String password,
            String salt
    ) throws Exception {
        if (!isInitializedEncryption) throw new Exception("Encryption not initialized");
        byte[] iv = Base64.getDecoder().decode(ivBase64Str);
        byte[] cipherText = Base64.getDecoder().decode(cipherTextBase64Str);
        SecretKey secretKey = AESGetKeyFromPassword(password, salt);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING, "BC");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] plainTextBytes = cipher.doFinal(cipherText);
        return new String(plainTextBytes);
    }


    public static SecretKey AESGetKeyFromPassword(
            String password,
            String salt
    ) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256", "BC");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, AES_KEY_SIZE);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    /**
     * *********************************
     * *--------  RSA SECTION  --------*
     * *********************************
     */

    public static byte[] encrypt(
            PublicKey key, String plainText, String provider,
            String transformation
    ) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(transformation, provider);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plainText.getBytes());
    }

    public static byte[] decrypt(
            PrivateKey key, byte[] cipherArr, String provider,
            String transformation
    ) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(transformation, provider);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherArr);
    }

    public static String encryptToBase64(
            PublicKey key, String plainText, String provider,
            String transformation
    ) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(transformation, provider);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherArr = cipher.doFinal(StringUtility.toByteArray(plainText));
        return Base64.getEncoder().encodeToString(cipherArr);
    }

    public static String decryptFromBase64(
            PrivateKey key, String cipherText, String provider,
            String transformation
    ) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(transformation, provider);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] plainArr = cipher.doFinal(decodedBytes);
        return new String(plainArr);
    }

    public static String encryptToBase64(
            String publicKeyStr, String plainText, String provider,
            String transformation, String keyAlgo
    ) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(transformation, provider);
        Key publicKey = getKeyFromString(publicKeyStr, PublicKey.class, keyAlgo);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherArr = cipher.doFinal(StringUtility.toByteArray(plainText));
        return Base64.getEncoder().encodeToString(cipherArr);
    }

    public static String decryptFromBase64(
            String privateKeyStr, String cipherText, String provider,
            String transformation, String keyAlgo
    ) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(transformation, provider);
        Key privateKey = getKeyFromString(privateKeyStr, PrivateKey.class, keyAlgo);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] plainArr = cipher.doFinal(decodedBytes);
        return new String(plainArr);
    }

    private static <T> T getKeyFromString(
            String keyText, Class<T> clazz, String algorithm) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(keyText);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        if (PrivateKey.class.isAssignableFrom(clazz)) {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return clazz.cast(privateKey);
        } else if (PublicKey.class.isAssignableFrom(clazz)) {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return clazz.cast(publicKey);
        } else {
            throw new IllegalArgumentException("Unsupported key class: " + clazz.getName());
        }
    }


    public static String createSignature(
            PrivateKey key, String plainText, String algorithm,
            String provider
    ) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature signer = Signature.getInstance(algorithm, provider);
        signer.initSign(key);
        signer.update(plainText.getBytes());
        byte[] signatureResultArr = signer.sign();
        return Base64.getEncoder().encodeToString(signatureResultArr);
    }

    public static boolean isVerifiedSignFromBase64(
            PublicKey key, String plainText, String base64Signature, String algorithm,
            String provider
    ) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
        Signature signer = Signature.getInstance(algorithm, provider);
        signer.initVerify(key);
        signer.update(plainText.getBytes());
        return signer.verify(Base64.getDecoder().decode(base64Signature));
    }

    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

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
