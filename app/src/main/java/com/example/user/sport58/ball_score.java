package com.example.user.sport58;

///拆解比分---------------------------------------------------------///////////////////////////
public class ball_score
{
    public static String[] score(String scorebox)
    {
        char[] score;
        String score2;
        String[]  score3;
        score = scorebox.toCharArray();
        if(scorebox.isEmpty()) {score3=new String[30];}else{score3=new String[score.length];}
        int j=0;

        for(int i=0;i<scorebox.length();i++)
        {
            score3[i]="";
             score2= (String) scorebox.subSequence(i,i+1);
            if(scorebox.isEmpty())
            {
                score3[i]="-";
            }
            else
                {
                  if(Character.isDigit(score[i]))
                  {
                      score3[j]=score3[j]+score[i];
                  }
                  else
                      {
                          if(score2.matches(","))
                          {
                              j++;

                          }
                          if(score2.matches("-"))
                          {
                              j++;
                              score3[j]="-";
                              i++;
                              score3[i]="-";
                          }
                      }
                }
        }





     return   score3;
    }
}
