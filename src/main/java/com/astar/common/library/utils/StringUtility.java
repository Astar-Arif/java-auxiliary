package com.astar.common.library.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;

//TODO ADD MORE
public abstract class StringUtility extends StringUtils {
    public static boolean isContainEmoji(String content){
        if (content == null) return false;
        return EmojiManager.containsEmoji(content);
    }

    public static boolean isMobileNumber(String mobileNo){
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
