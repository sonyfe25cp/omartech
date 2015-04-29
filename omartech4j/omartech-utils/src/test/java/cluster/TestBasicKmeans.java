package cluster;

import com.omartech.utils.cluster.BasicKmeans;
import com.omartech.utils.vocabulary.Distance;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by OmarTech on 15-4-28.
 */
public class TestBasicKmeans extends TestCase {
    static Logger logger = LoggerFactory.getLogger(TestBasicKmeans.class);

    public void testDistance() {
        double[] a = {1, 0};
        double[] b = {0, 1};
        double distance = Distance.euler(a, b);
        assertEquals(Math.sqrt(2), distance);
        logger.info("euler distance is right");
    }

    public void testIsOk() {

        Map<Integer, List<Integer>> map1 = new HashMap<>();

        map1.put(1, Arrays.asList(new Integer[]{1, 2, 3}));
        map1.put(2, Arrays.asList(new Integer[]{4, 5, 6}));

        Map<Integer, List<Integer>> map2 = new HashMap<>();
        map2.put(1, Arrays.asList(new Integer[]{1, 2, 3}));
        map2.put(2, Arrays.asList(new Integer[]{4, 5, 6}));

        assertEquals(true, BasicKmeans.isOk(map1, map2));

        Map<Integer, List<Integer>> map3 = new HashMap<>();
        map3.put(1, Arrays.asList(new Integer[]{1, 2, 3}));
        map3.put(2, Arrays.asList(new Integer[]{4, 5, 1}));

        assertEquals(false, BasicKmeans.isOk(map1, map3));

        Map<Integer, List<Integer>> map4 = new HashMap<>();
        map4.put(1, Arrays.asList(new Integer[]{1, 2, 3}));

        assertEquals(false, BasicKmeans.isOk(map1, map4));

        logger.info("the convergence condition is ok");
    }

    public void testFindNewCentric() {

        double[] a1 = {1, 0};
        double[] a2 = {2, 0};
        double[] a3 = {3, 0};
        double[] a4 = {4, 0};
        double[] a5 = {5, 0};

        List<double[]> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);

        List<Integer> ints = new ArrayList<>();
        ints.add(0);
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);

        int newCenter = BasicKmeans.findNewCenter(ints, list);

        assertEquals(2, newCenter);


        List<double[]> dots = new ArrayList<>();
        dots.add(new double[]{1, 0});
        dots.add(new double[]{1.1, 0});
        dots.add(new double[]{1.2, 0});

        dots.add(new double[]{2.0, 0});
        dots.add(new double[]{2.1, 0});
        dots.add(new double[]{2.2, 0});

        List<Integer> cluster1 = new ArrayList<>();
        cluster1.add(3);
        cluster1.add(4);
        cluster1.add(5);

        int newCenter1 = BasicKmeans.findNewCenter(cluster1, dots);
        assertEquals(4, newCenter1);

    }

    public void testClone() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(3, 3);
        map.put(2, 4);

        Map<Integer, Integer> map2 = map;
        map.put(4, 5);

        Integer integer = map2.get(4);
        System.out.println(integer);

        map = new HashMap<>();
        System.out.println(map2.get(1));
        System.out.println(map2.get(2));
        System.out.println(map.get(1));
//        assertEquals(false, map2.containsKey(4));
    }

    public void testKmeans() {
        List<double[]> dots = new ArrayList<>();
        dots.add(new double[]{1, 0});
        dots.add(new double[]{1.1, 0});
        dots.add(new double[]{1.2, 0});

        dots.add(new double[]{2.0, 0});
        dots.add(new double[]{2.1, 0});
        dots.add(new double[]{2.2, 0});

        Map<Integer, List<Integer>> kmeans = BasicKmeans.kmeans(dots, 2);

        logger.info(BasicKmeans.toString(kmeans));
    }


}
