package com.astar.common.library.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.vdurmont.emoji.EmojiManager;
import org.apache.commons.lang3.StringUtils;

//TODO ADD MORE
public abstract class StringUtility extends StringUtils {

    public static int countWords(String text){
        int res = 0;
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if (c == ' ')res++;
        }
        return (res == 0) ? 1 : res;

    }
    public static boolean isContainEmoji(String text) {
        if (text == null) return false;
        return EmojiManager.containsEmoji(text);
    }

    public static boolean isMobileNumber(String mobileNo) {
        if (mobileNo == null) return false;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber;
        try {
            phoneNumber = phoneNumberUtil.parse(mobileNo,
                                                Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
        } catch (NumberParseException e) {
            return false;
        }
        return phoneNumberUtil.isValidNumber(phoneNumber);
    }
}
