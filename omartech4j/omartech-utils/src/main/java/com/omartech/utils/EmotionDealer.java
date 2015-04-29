package com.omartech.utils;

/**
 * Created by OmarTech on 15-4-8.
 */
public class EmotionDealer {
    public static String dealUTF8(String text) {
        byte[] b_text = text.getBytes();

        for (int i = 0; i < b_text.length; i++) {

            if ((b_text[i] & 0x80) == 0) {
                continue;
            } else if ((b_text[i] & 0xE0) == 0xC0) {
                i += 1;
            } else if ((b_text[i] & 0xF0) == 0xE0) {
                i += 2;
            } else if ((b_text[i] & 0xF8) == 0xF0) {
                for (int j = 0; j < 4; j++) {
                    b_text[i + j] = 0x30;
                }
                i += 3;
            } else if ((b_text[i] & 0xFC) == 0xF8) {
                for (int j = 0; j < 5; j++) {
                    b_text[i + j] = 0x30;
                }
                i += 4;
            } else if ((b_text[i] & 0xFE) == 0xFC) {
                for (int j = 0; j < 6; j++) {
                    b_text[i + j] = 0x30;
                }
                i += 5;
            }
        }
        text = new String(b_text);
        return text;
    }
}
