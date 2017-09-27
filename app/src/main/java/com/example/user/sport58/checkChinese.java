package com.example.user.sport58;

import java.util.regex.Pattern;


class checkChinese {
    public static boolean isChineseChar(String inputString) {


        Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");

        return pattern.matcher(inputString).matches();

    }




}
