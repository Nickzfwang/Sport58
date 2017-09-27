package com.example.user.sport58;


//大小分判斷------------------------------------------------------------------------------------------------------------------//////////////////
public class big_or_small {
    public static int big_or_small(String upscore, String underscore, String start, String ncbigs) {
        if (upscore.equals("") && underscore.equals("") && start.equals("已開賽")) {
            return 0;
        } else {
            if(ncbigs.equals("")){return 3;}
            else{
                if (((Float.parseFloat(underscore) + Float.parseFloat(upscore)) > (Float.parseFloat(ncbigs)))) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }

    }
}