package com.omartech.laibicanRobot;

/**
 * Created by omar on 14/11/12.
 */
public class A1115 {

    public static void main(String[] args) {

        String a = "不要騙我";
        createText(a);
    }

    static void createText(String text){
        for(char c : text.toCharArray()){
            String str = "<span>"+c+"</span>";
            System.out.print(str);
        }
    }
}
