package com.omartech.utils.cluster;

import com.omartech.utils.vocabulary.Distance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 输入：
 * 一个数组，向量用double[]
 * 一个数字k
 * 输出：k个分组结果
 * Created by OmarTech on 15-4-28.
 */
public class BasicKmeans {

    static Logger logger = LoggerFactory.getLogger(BasicKmeans.class);

    public static Map<Integer, List<Integer>> kmeans(List<double[]> dots, int k) {

        logger.info("begin to compute kmeans, dots.length:{}, k:{}", dots.size(), k);

        int size = dots.size();
        if (k > size) {
            logger.error("k > dots length, exit");
            System.exit(1);
        }
        Map<Integer, List<Integer>> seeds = new HashMap<>();
        Map<Integer, List<Integer>> copySeeds = null;
        int run = 1;
        do {
            copySeeds = copy(seeds);
            if (seeds.size() == 0) {//初始时需要随机选seed
                do {
                    Random random = new Random();
                    float v = random.nextFloat();
                    int pos = Math.round(v * (dots.size() - 1));
                    seeds.put(pos, new ArrayList<Integer>());
                } while (seeds.size() != k);

            } else {//其他时候需要重新计算seed
                seeds = new HashMap<>();
                for (Map.Entry<Integer, List<Integer>> entry : copySeeds.entrySet()) {
                    List<Integer> cluster = entry.getValue();
                    int newseed = findNewCenter(cluster, dots);
                    seeds.put(newseed, null);
                }
            }
            logger.info("run: {}, seed:{}", run, Arrays.toString(seeds.keySet().toArray()));
            for (int i = 0; i < dots.size(); i++) {
                double minDis = Double.MAX_VALUE;
                double[] current = dots.get(i);
                int cluster = -1;
                for (Map.Entry<Integer, List<Integer>> entry : seeds.entrySet()) {
                    Integer key = entry.getKey();
                    List<Integer> value = entry.getValue();
                    double[] seed = dots.get(key);
                    double distance = Distance.euler(seed, current);
                    if (distance <= minDis) {
                        cluster = key;
                        minDis = distance;
                    }
                    logger.info("current: {} and seed: {}, dis:{}", new String[]{Arrays.toString(current), Arrays.toString(seed), distance + ""});
                }
                logger.info("current: {} -> seed:{}", Arrays.toString(current), cluster);
                List<Integer> members = seeds.get(cluster);
                if (members == null) {
                    members = new ArrayList<>();
                }
                members.add(i);
                seeds.put(cluster, members);
            }
            logger.info("run: {} over", run);
            logger.info("seed: {}", toString(seeds));
            run++;
        } while (!isOk(seeds, copySeeds));//判断是否达到结束条件

        for (Integer seed : seeds.keySet()) {
            logger.info("final seed is : {}", seed);
        }

        return seeds;
    }

    static Map<Integer, List<Integer>> copy(Map<Integer, List<Integer>> input) {
        Map<Integer, List<Integer>> map = new HashMap<>(input.size());
        for (Map.Entry<Integer, List<Integer>> entry : input.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static int findNewCenter(List<Integer> cluster, List<double[]> dots) {
        double[] means = null;
        for (Integer integer : cluster) {
            double[] doubles = dots.get(integer);
            if (means == null) {
                means = new double[doubles.length];
            }
            for (int i = 0; i < means.length; i++) {
                means[i] = means[i] + doubles[i];
            }
        }
        for (int i = 0; i < means.length; i++) {
            means[i] = means[i] / cluster.size();
        }

        logger.info("mean point : {}", Arrays.toString(means));

        int newcenter = -1;
        double minDis = Double.MAX_VALUE;
        for (Integer integer : cluster) {
            double[] doubles = dots.get(integer);
            double distance = Distance.euler(doubles, means);
            logger.info("{} and {}, distance: {}", new String[]{Arrays.toString(doubles), Arrays.toString(means), distance + ""});
            if (distance < minDis) {
                newcenter = integer;
                minDis = distance;
                logger.info("newcenter : {}", newcenter);
            }
        }
        return newcenter;
    }

    //如果两个map完全一样才返回true
    public static boolean isOk(Map<Integer, List<Integer>> results1, Map<Integer, List<Integer>> results2) {
        boolean flag = true;
        for (Map.Entry<Integer, List<Integer>> entry : results1.entrySet()) {
            Integer key = entry.getKey();
            List<Integer> value = entry.getValue();

            List<Integer> integers = results2.get(key);
            if (integers != null) {
                Set<Integer> set = new HashSet<>(integers);
                if (set.size() == value.size()) {
                    for (Integer pos : value) {
                        boolean contains = set.contains(pos);
                        flag = flag && contains;
                    }
                }
            } else {
                flag = flag && false;
            }
        }
        return flag;
    }

    public static String toString(Map<Integer, List<Integer>> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            Integer key = entry.getKey();
            List<Integer> value = entry.getValue();
            stringBuilder.append("\ngroup: " + key + "=>{");
            for (Integer v : value) {
                stringBuilder.append(v + ", ");
            }
            stringBuilder.append("}");
        }
        return stringBuilder.toString();
    }

}
