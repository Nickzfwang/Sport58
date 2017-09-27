package com.example.user.sport58;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class checkPhone {
    public static boolean isPhone(String phone) {
        String expression = "^(09)\\d{8}$";//開頭09+任意8碼
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }




}
