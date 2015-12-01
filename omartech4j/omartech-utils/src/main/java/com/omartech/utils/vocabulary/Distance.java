package com.omartech.utils.vocabulary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;


/**
 * kinds of distance
 *
 * @author Chen Jie
 * @date 5 Jul, 2014
 */
public class Distance {

    static Logger logger = LoggerFactory.getLogger(Distance.class);

    public static int editDistance(String word1, String word2) {
      int row = word1.length()+1;
      int col = word2.length()+1;
      int[][] matrix = new int[row][col];
      for(int i = 0; i < row; i ++){
        matrix[i][0] = i;
      }
      for(int j = 0; j < col; j ++){
        matrix[0][j] = j;
      }
      for(int i = 1; i < row; i ++){
        for(int j = 1; j < col; j ++){
          if(word1.charAt(i-1) == word2.charAt(j-1)){
            matrix[i][j] = matrix[i-1][j-1];
          }else{
            matrix[i][j] = matrix[i-1][j-1]+1;
          }
          matrix[i][j] = Math.min(matrix[i][j], Math.min(matrix[i-1][j] + 1, matrix[i][j-1] + 1 ));
        }
      }
      return matrix[row-1][col-1];
    }

    public static double cosine(double[] a, double[] b) {
        if (a.length != b.length) {
            logger.error("length not match");
            System.exit(0);
        }
        double dotValue = dot(a, b);
        double moa = mo(a);
        double mob = mo(b);
        double fenmu = moa * mob;
        if (fenmu == 0) {
            return 0;
        }
        return dotValue / (moa * mob);
    }

    public static double mo(double[] a) {
        double res = 0;
        for (double aa : a) {
            res += aa * aa;
        }
        return Math.sqrt(res);
    }

    public static double dot(double[] a, double[] b) {
        if (a.length != b.length) {
            logger.error("dot : length not match");
            System.exit(0);
        }
        double res = 0;
        for (int i = 0; i < a.length; i++) {
            res += (a[i] * b[i]);
        }
        return res;
    }

    public static double euler(double[] feature, double[] feature2) {
        if (feature.length != feature2.length) {
            logger.error("euler : length not match");
            System.exit(0);
        }
        double result = 0;
        for (int i = 0; i < feature.length; i++) {
            double x1 = feature[i];
            double x2 = feature2[i];
            double v = (x2 - x1) * (x2 - x1);
            result += v;
        }
        result = Math.sqrt(result);
        return result;
    }

    public static double jaccard(String title, String compare) {
        Set<Character> a = new HashSet<>();
        Set<Character> b = new HashSet<>();
        Set<Character> ab = new HashSet<>();
        for (char cha : title.toCharArray()) {
            a.add(cha);
            ab.add(cha);
        }
        for (char cha : compare.toCharArray()) {
            b.add(cha);
            ab.add(cha);
        }
        int jiao = a.size() + b.size() - ab.size();
        int union = ab.size();
        double sim = (double) jiao / union;
        return sim;
    }
}
