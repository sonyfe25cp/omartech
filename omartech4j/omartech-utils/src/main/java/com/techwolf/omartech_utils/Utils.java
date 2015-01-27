package com.techwolf.omartech_utils;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import static java.lang.Math.min;

public class Utils {

    public static final String getResouce(String name) {
        try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(name)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException("not found " + name, e);
        }
    }

    public static final List<String> getResourceList(String name) {
        InputStream is = Utils.class.getClassLoader().getResourceAsStream(name);
        List<String> lines = null;
        try {
            lines = IOUtils.readLines(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static final String CHARSET = "charset=";

    public static final Charset ASCII = Charset.forName("US-ASCII");

    public static final Charset UTF_8 = Charset.forName("utf8");

    public static Charset detectCharset(String text) {
        byte[] body = text.getBytes();
        return detectCharset(body);
    }

    public static Charset detectCharset(byte[] bytes) {
        String s = new String(bytes, 0, min(512, bytes.length), ASCII);
        Charset c = guess(s, CHARSET);
        return c == null ? UTF_8 : c;
    }

    private static Charset guess(String html, String patten) {
        int idx = html.indexOf(patten);
        if (idx != -1) {
            int start = idx + patten.length();
            int end = html.indexOf('"', start);
            if (end != -1) {
                try {
                    return Charset.forName(html.substring(start, end));
                } catch (Exception ignore) {
                }
            }
        }
        return null;
    }


    public static void sortMapStringAndInteger(List<Entry<String, Integer>> relationList) {
        sortMapStringAndInteger(relationList, true);
    }

    /**
     * map按value大头朝下排序
     *
     * @param relationList
     */
    public static void sortMapStringAndInteger(List<Entry<String, Integer>> relationList, final boolean flag) {
        Collections.sort(relationList, new Comparator<Entry<String, Integer>>() {

            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                Integer c1 = o1.getValue();
                Integer c2 = o2.getValue();
                int res = 0;
                if (c1 > c2) {
                    res = 1;
                } else if (c1 == c2) {
                    return 0;
                } else {
                    res = -1;
                }
                if (flag) {
                    return res;
                } else {
                    return 0 - res;
                }
            }
        });
    }

    /**
     * map按value大头朝下排序
     *
     * @param relationList
     */
    public static void sortMapStringAndDouble(List<Entry<String, Double>> relationList, final boolean flag) {
        Collections.sort(relationList, new Comparator<Entry<String, Double>>() {

            @Override
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                Double c1 = o1.getValue();
                Double c2 = o2.getValue();
                int res = 0;
                if (c1 > c2) {
                    res = 1;
                } else if (c1 == c2) {
                    return 0;
                } else {
                    res = -1;
                }
                if (flag) {
                    return res;
                } else {
                    return 0 - res;
                }
            }
        });
    }


    /**
     * @param relationList
     * @param output
     * @throws IOException
     */
    public static void writeDownMapStringInteger(List<Entry<String, Integer>> relationList,
                                                 String output, String sep) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(output)));
        for (Entry<String, Integer> entry : relationList) {
            bw.write(entry.getKey() + sep + entry.getValue());
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }

    public static void writeDownMapStringInteger(List<Entry<String, Integer>> relationList,
                                                 String output) throws IOException {
        writeDownMapStringInteger(relationList, output, " ");
    }

    /**
     * @param text
     * @param n
     * @return
     */
    public static String[] getNgramSet(String text, int n) {
        StringBuilder sb = new StringBuilder();
        String spl = "，。，";
        for (int i = text.length(); i > 0; i--) {
            if (i - n >= 0) {
                sb.append(text.substring(i - n, i));
                sb.append(spl);
            }
        }
        return sb.toString().split(spl);
    }

    public static String arrayToString(String[] attr) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String at : attr) {
            sb.append(at);
            if (i != attr.length) {
                sb.append(",");
            }
            i++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] ngramSet = getNgramSet("南京市长江大桥在长江大桥上发表了关于江大桥同志的事迹讲话", 3);
        for (String tm : ngramSet) {
            System.out.println(tm);
        }
    }

    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) md5StrBuff.append("0")
                    .append(Integer.toHexString(0xFF & byteArray[i]));
            else md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }


    private static String[] encodings = {"UTF8", "gbk"};

    public static String readFileIgnoreEncoding(File file) throws IOException {
        int index = 0;
        boolean flag = true;
        String content = null;
        do {
            content = FileUtils.readFileToString(file, encodings[index]);

            if (content.contains("�")) {
                content = null;
                flag = true;
                index++;
            } else {
                flag = false;
            }
        } while (flag && index < encodings.length);
        return content;
    }
}
