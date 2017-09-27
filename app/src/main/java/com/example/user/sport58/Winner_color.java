package com.example.user.sport58;

/**
 * Created by USER on 2017/8/9.
 */

public class Winner_color {
    public static int winner_color(String undersocre,String upscore)
    {
        if(undersocre!=null&&upscore!=null&&!undersocre.matches("")&&!upscore.matches(""))
        {
            if(Integer.parseInt(undersocre)>Integer.parseInt(upscore))
            {
             return 0;
            }
            else
            {
                return 1;
            }
        }
        else
            {
                return 2;
            }

    }
}
