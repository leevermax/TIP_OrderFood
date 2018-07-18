package com.tip.orderfood.Support;

public class SuaDuLieu {
    public SuaDuLieu() {
    }

    public String toiUuChuoi(String inPut){
        String s = inPut;
        s = s.trim();
        char []arrs = s.toCharArray();
        char c = ' ';
        for (int i = 0; i < s.length(); i++){
            if( i == 0){
                arrs[i] = Character.toUpperCase(arrs[i]);
            }

            if ((int)arrs[i] == (int)c){
                arrs[i+1] = Character.toUpperCase(arrs[i+1]);
            }
        }
        String t = new String(arrs);

        String []arr = t.split(" ");

        String sToiUuu = "";
        for (String tu: arr){
            if (tu.trim().length() != 0)
                sToiUuu += tu + " ";
        }

        sToiUuu = sToiUuu.trim();
        return sToiUuu;
    }
}
