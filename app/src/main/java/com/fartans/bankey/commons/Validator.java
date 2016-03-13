package com.fartans.bankey.commons;

import java.util.List;

/**
 * Created by hitesh on 14/03/16.
 * @author hitesh.sethiya
 * Self explanatory methods
 */
public class Validator {

    public static Boolean isBlankOrNa(String string) {
        return (string == null || string.trim().isEmpty() || "NA".equals(string) || "na".equals(string) || "NULL".equalsIgnoreCase(string));
    }

    public static Boolean isNullOrEmpty(List list) {
        return (list == null || list.isEmpty());
    }

    public static Boolean contains(String check,List<String> objects) {
        if(isNullOrEmpty(objects) || isBlankOrNa(check)) return Boolean.FALSE;
        for(String object : objects) {
            if(check.equalsIgnoreCase(object)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
