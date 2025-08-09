package com.astar.java.library.utils;

import com.astar.java.library.enums.ChecksumType;
import com.astar.java.library.pojo.Base;
import org.apache.commons.codec.digest.DigestUtils;


import javax.xml.crypto.Data;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class DataUtility {

    public static String generateChecksum(ChecksumType type, String data) throws NoSuchAlgorithmException {
        return switch(type) {
            case MD5 -> genMD5Checksum(data);
            case SHA_1 -> genSHA_1Checksum(data);
            case SHA_256 -> genSHA_256Checksum(data);
            case SHA_512 -> genSHA_512Checksum(data);
            case CRC32 -> genCRC32Checksum(data);
            case null, default -> throw new InvalidParameterException();
        };
    }

    public static String generateChecksum(ChecksumType type, String data, int length) throws NoSuchAlgorithmException {
        String checksum = switch(type) {
            case MD5 -> genMD5Checksum(data);
            case SHA_1 -> genSHA_1Checksum(data);
            case SHA_256 -> genSHA_256Checksum(data);
            case SHA_512 -> genSHA_512Checksum(data);
            case CRC32 -> genCRC32Checksum(data);
            case null, default -> throw new InvalidParameterException();
        };
        return checksum.substring(0, Math.min(checksum.length(), length));
    }

    private static String genMD5Checksum(String data){
        return DigestUtils.md5Hex(data);
    }
    private static String genSHA_1Checksum(String data){
        return DigestUtils.sha1Hex(data);
    }
    private static String genSHA_256Checksum(String data){
        return DigestUtils.sha256Hex(data);
    }
    private static String genSHA_512Checksum(String data){
        return DigestUtils.sha512Hex(data);
    }
    private static String genCRC32Checksum(String data){
        CRC32 crc = new CRC32();
        crc.update(data.getBytes());
        return String.valueOf(crc.getValue());
    }
    public static String toBase(Base base, String data) {
        return null;
    }
}
