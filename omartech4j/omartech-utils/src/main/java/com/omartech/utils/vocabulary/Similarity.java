package com.omartech.utils.vocabulary;


/**
 *
 * @author Chen Jie
 * @date 7 Jul, 2014
 */
public class Similarity {
    public String pair;
    
    public String a;
    public String b;

    public double sim;

    public Similarity(String a, String b, double sim) {
        super();
        this.a = a;
        this.b = b;
        this.pair = key(a, b);
        this.sim = sim;
    }
    
    public static String key(String a, String b) {
        return a + "-" + b;
    }

    @Override
    public String toString() {
        return "Similarity [pair=" + pair + ", sim=" + sim + "]";
    }
    
}
