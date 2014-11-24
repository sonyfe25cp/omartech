package com.omartech.laibicanRobot.activities;

/**
 * Created by omar on 14/11/12.
 */
public class A1115 {

    public static void main(String[] args) {

//        String a = "这年头相亲不稀奇，遇到到奇葩的相亲对象那可就靠运气了，来晒一下你的奇葩相亲之旅吧\n";
//        String a ="一天扣21分的路过，原因是因为某一条街刚刚变成了单行道，而那天我刚好接送人走了好几趟，";
//        String a = "我有次相亲，那男的主动问我吃啥，我说啥都行，结果他讪讪的说：哎呀，这也不是吃饭点儿呀..";
        String a = "早上上班肚子疼，开车上路堵车，硬憋40分钟，深深觉得括约肌已经快绷断了⋯⋯ ";
        createText(a);

    }

    static void createText(String text){
        System.out.println("长度: " + text.length());
        for(char c : text.toCharArray()){
            String str = "<span>"+c+"</span>";
            System.out.print(str);
        }
    }
}
