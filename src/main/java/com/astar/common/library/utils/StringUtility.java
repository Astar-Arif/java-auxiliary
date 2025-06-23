package com.astar.common.library.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import net.fellbaum.jemoji.EmojiManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO ADD MORE
public abstract class StringUtility extends StringUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(StringUtility.class);

    public static int countWords(String text) {
        int res = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') res++;
        }
        return (res == 0) ? 1 : res;

    }

    //    TODO TEST THIS
    public static boolean isContainEmoji(String text) {
        if (text == null) return false;
        return EmojiManager.containsAnyEmoji(text);
    }

    public static boolean isMobileNumber(String mobileNo) {
        if (mobileNo == null) return false;
        if (!mobileNo.startsWith("+")) mobileNo = '+' + mobileNo;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber;
        try {
            phoneNumber = phoneNumberUtil.parse(mobileNo,
                                                Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
        } catch (NumberParseException e) {
            LOGGER.error("", e);
            return false;
        }
        return phoneNumberUtil.isValidNumber(phoneNumber);
    }

    public static byte[] toByteArray(String str) {
        return str.getBytes();
    }

    public static String toCamelCase(String str) {
        return null;
    }

    public static String to_snake_case(String str) {
        byte caseType = detectCase(str);
        return null;
    }

    public static String to_kebab_case(String str) {
        byte caseType = detectCase(str);
        return null;
    }

    public static String ToPascalCase(String str) {
        byte caseType = detectCase(str);
        return null;
    }

    private static byte detectCase(String str) {
        return 0;
    }




}
