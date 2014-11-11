package com.omartech.laibicanRobot.action;

/**
 * Created by omar on 14/11/11.
 */
public class SHA1 {
    private final int[] abcde = { 0x67452301, 0xefcdab89, 0x98badcfe,
            0x10325476, 0xc3d2e1f0 };
    // ÕªÒªÊý¾Ý´æ´¢Êý×é
    private int[] digestInt = new int[5];
    // ¼ÆËã¹ý³ÌÖÐµÄÁÙÊ±Êý¾Ý´æ´¢Êý×é
    private int[] tmpData = new int[80];

    // ¼ÆËãsha-1ÕªÒª
    private int process_input_bytes(byte[] bytedata) {
        // ³õÊÔ»¯³£Á¿
        System.arraycopy(abcde, 0, digestInt, 0, abcde.length);
        // ¸ñÊ½»¯ÊäÈë×Ö½ÚÊý×é£¬²¹10¼°³¤¶ÈÊý¾Ý
        byte[] newbyte = byteArrayFormatData(bytedata);
        // »ñÈ¡Êý¾ÝÕªÒª¼ÆËãµÄÊý¾Ýµ¥Ôª¸öÊý
        int MCount = newbyte.length / 64;
        // Ñ­»·¶ÔÃ¿¸öÊý¾Ýµ¥Ôª½øÐÐÕªÒª¼ÆËã
        for (int pos = 0; pos < MCount; pos++) {
            // ½«Ã¿¸öµ¥ÔªµÄÊý¾Ý×ª»»³É16¸öÕûÐÍÊý¾Ý£¬²¢±£´æµ½tmpDataµÄÇ°16¸öÊý×éÔªËØÖÐ
            for (int j = 0; j < 16; j++) {
                tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4));
            }
            // ÕªÒª¼ÆËãº¯Êý
            encrypt();
        }
        return 20;
    }

    // ¸ñÊ½»¯ÊäÈë×Ö½ÚÊý×é¸ñÊ½
    private byte[] byteArrayFormatData(byte[] bytedata) {
        // ²¹0ÊýÁ¿
        int zeros = 0;
        // ²¹Î»ºó×ÜÎ»Êý
        int size = 0;
        // Ô­Ê¼Êý¾Ý³¤¶È
        int n = bytedata.length;
        // Ä£64ºóµÄÊ£ÓàÎ»Êý
        int m = n % 64;
        // ¼ÆËãÌí¼Ó0µÄ¸öÊýÒÔ¼°Ìí¼Ó10ºóµÄ×Ü³¤¶È
        if (m < 56) {
            zeros = 55 - m;
            size = n - m + 64;
        } else if (m == 56) {
            zeros = 63;
            size = n + 8 + 64;
        } else {
            zeros = 63 - m + 56;
            size = (n + 64) - m + 64;
        }
        // ²¹Î»ºóÉú³ÉµÄÐÂÊý×éÄÚÈÝ
        byte[] newbyte = new byte[size];
        // ¸´ÖÆÊý×éµÄÇ°Ãæ²¿·Ö
        System.arraycopy(bytedata, 0, newbyte, 0, n);
        // »ñµÃÊý×éAppendÊý¾ÝÔªËØµÄÎ»ÖÃ
        int l = n;
        // ²¹1²Ù×÷
        newbyte[l++] = (byte) 0x80;
        // ²¹0²Ù×÷
        for (int i = 0; i < zeros; i++) {
            newbyte[l++] = (byte) 0x00;
        }
        // ¼ÆËãÊý¾Ý³¤¶È£¬²¹Êý¾Ý³¤¶ÈÎ»¹²8×Ö½Ú£¬³¤ÕûÐÍ
        long N = (long) n * 8;
        byte h8 = (byte) (N & 0xFF);
        byte h7 = (byte) ((N >> 8) & 0xFF);
        byte h6 = (byte) ((N >> 16) & 0xFF);
        byte h5 = (byte) ((N >> 24) & 0xFF);
        byte h4 = (byte) ((N >> 32) & 0xFF);
        byte h3 = (byte) ((N >> 40) & 0xFF);
        byte h2 = (byte) ((N >> 48) & 0xFF);
        byte h1 = (byte) (N >> 56);
        newbyte[l++] = h1;
        newbyte[l++] = h2;
        newbyte[l++] = h3;
        newbyte[l++] = h4;
        newbyte[l++] = h5;
        newbyte[l++] = h6;
        newbyte[l++] = h7;
        newbyte[l++] = h8;
        return newbyte;
    }

    private int f1(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private int f2(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private int f3(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    private int f4(int x, int y) {
        return (x << y) | x >>> (32 - y);
    }

    // µ¥ÔªÕªÒª¼ÆËãº¯Êý
    private void encrypt() {
        for (int i = 16; i <= 79; i++) {
            tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14]
                    ^ tmpData[i - 16], 1);
        }
        int[] tmpabcde = new int[5];
        for (int i1 = 0; i1 < tmpabcde.length; i1++) {
            tmpabcde[i1] = digestInt[i1];
        }
        for (int j = 0; j <= 19; j++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[j] + 0x5a827999;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int k = 20; k <= 39; k++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[k] + 0x6ed9eba1;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int l = 40; l <= 59; l++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[l] + 0x8f1bbcdc;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int m = 60; m <= 79; m++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[m] + 0xca62c1d6;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int i2 = 0; i2 < tmpabcde.length; i2++) {
            digestInt[i2] = digestInt[i2] + tmpabcde[i2];
        }
        for (int n = 0; n < tmpData.length; n++) {
            tmpData[n] = 0;
        }
    }

    // 4×Ö½ÚÊý×é×ª»»ÎªÕûÊý
    private int byteArrayToInt(byte[] bytedata, int i) {
        return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16)
                | ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff);
    }

    // ÕûÊý×ª»»Îª4×Ö½ÚÊý×é
    private void intToByteArray(int intValue, byte[] byteData, int i) {
        byteData[i] = (byte) (intValue >>> 24);
        byteData[i + 1] = (byte) (intValue >>> 16);
        byteData[i + 2] = (byte) (intValue >>> 8);
        byteData[i + 3] = (byte) intValue;
    }

    // ½«×Ö½Ú×ª»»ÎªÊ®Áù½øÖÆ×Ö·û´®
    private static String byteToHexString(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    // ½«×Ö½ÚÊý×é×ª»»ÎªÊ®Áù½øÖÆ×Ö·û´®
    private static String byteArrayToHexString(byte[] bytearray) {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexString(bytearray[i]);
        }
        return strDigest;
    }

    // ¼ÆËãsha-1ÕªÒª£¬·µ»ØÏàÓ¦µÄ×Ö½ÚÊý×é
    public byte[] getDigestOfBytes(byte[] byteData) {
        process_input_bytes(byteData);
        byte[] digest = new byte[20];
        for (int i = 0; i < digestInt.length; i++) {
            intToByteArray(digestInt[i], digest, i * 4);
        }
        return digest;
    }

    // ¼ÆËãsha-1ÕªÒª£¬·µ»ØÏàÓ¦µÄÊ®Áù½øÖÆ×Ö·û´®
    public String getDigestOfString(byte[] byteData) {
        return byteArrayToHexString(getDigestOfBytes(byteData));
    }

    public static void main(String[] args) {
        String data = "123456";
        System.out.println(data);
        String digest = new SHA1().getDigestOfString(data.getBytes());
        System.out.println(digest);

        // System.out.println( ToMD5.convertSHA1(data).toUpperCase());
    }
}
