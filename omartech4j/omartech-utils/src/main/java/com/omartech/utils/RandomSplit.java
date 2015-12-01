import java.util.*;

/**
 * Created by omar on 15/11/6.
 */
public class RandomSplit<T> {

    private List<T> list;
    private float percent;

    public RandomSplit(List<T> list, float percent) {
        this.list = list;
        this.percent = percent;

        init();
    }

    private int max = 0;
    private List<T> left;
    private int theta = 0;

    Set<Integer> set = new HashSet<>();


    void init() {
        max = list.size();
        theta = Math.round(max * percent);
        left = new ArrayList();
        for (int i = 0; i < max; i++) {
            set.add(i);
        }
        System.out.println("数组长度:" + max + ", 抽样比例:" + percent + ", 返回长度:" + theta);
    }

    public List<T> random() {
        List<T> result = new ArrayList<>();
        while (result.size() < theta) {
            Random random = new Random();
            float f = random.nextFloat();
            int current = Math.round(f * max);
            if (set.contains(current)) {
                result.add(list.get(current));
                set.remove(current);
            }
        }
        for (Integer leftNum : set) {
            left.add(list.get(leftNum));
        }
        return result;
    }

    public static void main(String[] args) {

//        List<String> strings = Arrays.asList(new String[]{"a", "b", "c", "d", "e", "f", "h", "aa", "aa", "bb", "c", "d"});

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 970; i++) {
            strings.add("a");
        }
        for (int i = 0; i < 1500; i++) {
            strings.add("b");
        }

        for (int i = 0; i < 1000; i++) {
            RandomSplit<String> randomSplit = new RandomSplit(strings, 0.6f);
            List<String> random = randomSplit.random();
//            System.out.println(random);
            List left1 = randomSplit.getLeft();
//            System.out.println(left1);


            int acount = 0;
            int bcount = 0;
            for (String tmp : random) {
                if (tmp.equals("a")) {
                    acount++;
                } else if (tmp.equals("b")) {
                    bcount++;
                }
            }

            System.out.println("比例:" + (acount / (bcount + 0.0)));

        }
    }

    public List getLeft() {
        return left;
    }

    public void setLeft(List left) {
        this.left = left;
    }
}
