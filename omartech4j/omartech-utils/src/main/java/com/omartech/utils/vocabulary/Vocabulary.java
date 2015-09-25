package com.omartech.utils.vocabulary;

import com.google.gson.Gson;
import com.omartech.utils.Utils;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vocabulary {

    static Logger logger = LoggerFactory.getLogger(Vocabulary.class);

    private Map<String, AtomicInteger> wordCountTotal = new ConcurrentHashMap<>();//全部unit中的数量

    private Map<String, AtomicInteger> wordCountPerUnit = new ConcurrentHashMap<>();//不同词出现的unit数

    private Map<String, Integer> posMap = new ConcurrentHashMap<>(); //词典顺序

    private Map<String, Double> weightMap = new ConcurrentHashMap<>();//词重

    private Set<String> removedWords = new HashSet<>();

    private boolean overFlag = false;

    private boolean debug = false;

    private boolean filterStopWords = false;//是否过滤停词

    private boolean filterSingleWord = false;//是否过滤单字

    private boolean autoFilter = true;//是否自动过滤高频和低频词

    private static Set<String> stopWordsSet = new HashSet<String>();

    public AtomicInteger fileCount = new AtomicInteger();

    public Vocabulary() {
        super();
    }

    public void getMeta() {
        logger.info("总词典.size:{}", wordCountTotal.size());
    }

    public void saveToDisk() {
        String defaultName = Vocabulary.class.getName();
        saveToDisk(defaultName);
    }

    public void saveToDisk(String fileName) {
        if (!overFlag) {
            logger.error("必须计算完毕才能保存到本地文件");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(this);
        String output = "/tmp/" + fileName + ".voc";
        try {
            FileUtils.write(new File(output), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把词按照频率排序返回，用于查看高频词或低频词
     * 便于加入停词中
     *
     * @return
     */
    public List<Entry<String, Integer>> showFrequencyWords(boolean flag) {
        Map<String, Integer> tMap = new HashMap<>();
        for (Entry<String, AtomicInteger> entry : wordCountPerUnit.entrySet()) {
            String key = entry.getKey();
            int i = entry.getValue().get();
            tMap.put(key, i);
        }

        ArrayList<Entry<String, Integer>> countList = new ArrayList<>(tMap.entrySet());
        Utils.sortMapStringAndInteger(countList, flag);
        return countList;
    }

    /**
     * 把词按照频率排序返回，用于查看高频词或低频词
     * 便于加入停词中
     *
     * @return
     */
    public List<Entry<String, Integer>> showFrequencyWordsWithWeight(boolean flag) {
        ArrayList<Entry<String, Integer>> countList = new ArrayList<>(posMap.entrySet());
        Utils.sortMapStringAndInteger(countList, flag);
        return countList;
    }

    /**
     * 把词按照权重排序返回
     *
     * @return
     */
    public List<Entry<String, Double>> showWordsWeight(boolean flag) {
        ArrayList<Entry<String, Double>> countList = new ArrayList<>(weightMap.entrySet());
        Utils.sortMapStringAndDouble(countList, flag);
        return countList;
    }

    public static Vocabulary loadFromDB() {
        String defaultName = Vocabulary.class.getName();
        return loadFromDB(defaultName);
    }

    public static Vocabulary loadFromDB(String fileName) {
        String input = "/tmp/" + fileName + ".voc";
        File file = new File(input);
        if (!file.exists()) {
            logger.error("词典文件不存在, {}", input);
        }
        String text;
        ToAnalysis.parse("hello world for acc load speed");
        try {
            text = FileUtils.readFileToString(file);
            Gson gson = new Gson();
            Vocabulary voc = gson.fromJson(text, Vocabulary.class);
            return voc;
        } catch (IOException e) {
            logger.error("gson转化失败");
            return null;
        }
    }

    public int size() {
        if (!overFlag) {
            logger.error("先调用addOver()");
            return 0;
        }
        return weightMap.size();
    }

    public Map<Integer, Double> generatePositionVectionMap(String sentence) {
        Map<Integer, Double> map = new HashMap<>();
        Map<String, Double> stringDoubleMap = generateStringVectorMap(sentence);
        for (Entry<String, Double> entry : stringDoubleMap.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();

            Integer integer = posMap.get(key);
            map.put(integer, value);
        }
        return map;
    }

    public Map<String, Double> generateStringVectorMap(String sentence) {
        if (!overFlag) {
            logger.error("先调用addOver()");
            return null;
        }
        Map<String, Double> map = new HashMap<>();
        HashMap<String, Integer> counter = wordCount(sentence);

        int currentSize = 0;
        for (Entry<String, Integer> entry : counter.entrySet()) {
            Integer value = entry.getValue();
            currentSize += value;
        }
        if (debug) {
            logger.info("{} 包含 {}个词", sentence, currentSize);
        }
        for (Entry<String, Integer> entry : counter.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();

            Double weight = weightMap.get(word);

            if (weight == null || weight == 0) {
                continue;
            }

            double value = ((double) count / currentSize) * weight;
            if(debug){
                logger.info("词:{}, 本句中出现次数:{}, idf:{}", new String[]{word, count + "", weight + ""});
            }
            map.put(word, value);
        }
        return map;
    }


    public double[] generateVector(String sentence) {
        if (!overFlag) {
            logger.error("先调用addOver()");
            return null;
        }
        double[] vector = new double[size()];

        Map<Integer, Double> posVecMap = generatePositionVectionMap(sentence);

        for (Entry<Integer, Double> entry : posVecMap.entrySet()) {
            Integer pos = entry.getKey();
            Double value = entry.getValue();
            vector[pos] = value;
        }
        return vector;
    }

    private HashMap<String, Integer> wordCount(String sentence) {
        List<String> terms = cut(sentence);
        HashMap<String, Integer> counter = new HashMap<>();
        for (String word : terms) {
            Integer c = counter.get(word);
            if (c == null) {
                c = 0;
            }
            c++;
            counter.put(word, c);
        }
        return counter;
    }

    public Integer getPosition(String word) {
        if (!overFlag) {
            logger.error("先调用addOver()");
            return null;
        }
        Integer pos = posMap.get(word);
        if (pos == null) {
            logger.error("在字典中找不到: {}", word);
            return null;
        } else {
            return pos.intValue();
        }
    }

    public Double getWordWeight(String word) {
        Double weight = weightMap.get(word);
        if (weight == null) {
            logger.error("字典中没有这个词: {}", word);
            return null;
        } else {
            return weight;
        }
    }

    final static Pattern pureNum = Pattern.compile("\\d+");

    public List<String> cut(String text) {
        List<Term> terms = ToAnalysis.parse(text);
        List<String> array = new ArrayList<>();
        StringBuilder sbDebug = new StringBuilder();
        for (Term term : terms) {
            String word = term.getName().trim();
            if (word.length() == 0) {
                continue;
            }
//            System.out.println(word);
            if (filterSingleWord) {//过滤单字
                if (word.length() < 2) {
                    continue;
                }
            }
            if (filterStopWords) {
                if (stopWordsSet.contains(word)) {
                    continue;
                }
            }
            Matcher matcher = pureNum.matcher(word);//含数字的词都不要
            if (matcher.find()) {
                continue;
            }
            array.add(word);

            if (debug) {
                sbDebug.append(word + ",");
            }
        }
        if (debug) {
            logger.info("cut {} into {}", text, sbDebug.toString());
        }
        return array;
    }

    AtomicInteger pos = new AtomicInteger();//词序的位置

    /**
     * 每次一个描述单位(句子/文章)
     *
     * @param text
     */
    public void addText(String text) {
        if (overFlag) {
            logger.info("重新打开输入，需要再次结束才可以继续使用");
            overFlag = false;
        }
        fileCount.incrementAndGet();
        List<String> words = cut(text);
        if (words.size() == 0) {
            return;
        }
        Map<String, Integer> subMapForUnit = new HashMap<>();//记录每个词在句子中的出现次数
        for (String word : words) {
            Integer subCount = subMapForUnit.get(word);
            if (subCount == null) {
                subCount = 0;
            }
            subCount++;
            subMapForUnit.put(word, subCount);
        }

        for (Entry<String, Integer> entry : subMapForUnit.entrySet()) {
            String word = entry.getKey();
            Integer count = entry.getValue();

            //for unit 记录包含每个词的句子数
            AtomicInteger unitCount = wordCountPerUnit.get(word);
            if (unitCount == null) {
                unitCount = new AtomicInteger();
            }
            unitCount.incrementAndGet();
            wordCountPerUnit.put(word, unitCount);

            //for total 记录每个词在数据集中总共出现的次数
            AtomicInteger totalCount = wordCountTotal.get(word);
            if (totalCount == null) {
                totalCount = new AtomicInteger();
            }
            totalCount.addAndGet(count);
            wordCountTotal.put(word, totalCount);
        }
    }

    /**
     * compute after add
     */
    public void addOver() {
        overFlag = true;
        if (autoFilter) {
            adjust(defaultLow, defaultHigh);
        } else {
            computeOriginWeight();
        }
    }


    private void computeOriginWeight() {

        int totalFileCount = fileCount.get();
        if (debug) {
            logger.info("总文件数: {}", totalFileCount);
        }

        posMap = new ConcurrentHashMap<>();
        //记录默认词序
        int i = 0;
        for (Entry<String, AtomicInteger> wordsCount : wordCountPerUnit.entrySet()) {
            String word = wordsCount.getKey();
            posMap.put(word, i);
            i++;
        }
        //计算idf = filecount/file count with this word
        for (Entry<String, Integer> entry : posMap.entrySet()) {
            String word = entry.getKey();
            double idf = 0d;
            int unitCount = wordCountPerUnit.get(word).get();
            double iidf = (double) totalFileCount / (unitCount + 0);
            idf = Math.log(iidf);
            if (debug) {
                logger.info("词: {}, 包含该词的文件数:{}, 未log前的idf值:{}, idf:{}", new String[]{word, unitCount+"", iidf + "", idf + ""});
            }
            weightMap.put(word, idf);
        }
        logger.info("voc words count is {} (after filter the low and high frequency words)", (weightMap.size()));
        logger.info("word weight compute over");
    }

    private float defaultLow = 1 / 10000.0f;
    private float defaultHigh = 1 / 3f;

    /**
     * 频次低于low && 高于high
     *
     * @param low
     * @param high
     */
    public void adjust(int low, int high) {
        if (!overFlag) {
            logger.error("先调用addOver,才能够调整");
            return;
        }
        int totalSize = fileCount.get();
        logger.info("voc filecount size:{}", totalSize);

        posMap = new ConcurrentHashMap<>();

        int i = 0;
        for (Entry<String, AtomicInteger> wordsCount : wordCountPerUnit.entrySet()) {
            String word = wordsCount.getKey();
            Integer count = wordsCount.getValue().get();
            if (count >= high || count <= low) {
                removedWords.add(word);//低频词或者高频词去死
            } else {
                posMap.put(word, i);
                i++;
            }
        }

        logger.info("{} words are too low or too high frequency will be filted.", removedWords.size());

        for (Entry<String, Integer> entry : posMap.entrySet()) {
            String word = entry.getKey();
            double idf = 0d;
            int unitCount = wordCountPerUnit.get(word).get();
            double iidf = (double) fileCount.get() / (unitCount + 0);
            idf = Math.log(iidf);
            logger.info("word:{}, iidf:{}, idf:{}", new String[]{word, iidf + "", idf + ""});
            weightMap.put(word, idf);
        }
        logger.info("voc words count is {} (after filter the low and high frequency words)", (weightMap.size()));
        logger.info("word weight compute over");
    }

    /**
     * low 是百分比
     *
     * @param low
     * @param high
     */
    public void adjust(float low, float high) {
        int totalSize = fileCount.get();
        int lowNum = Math.round(totalSize * low);
        int highNum = Math.round(totalSize * high);
        adjust(lowNum, highNum);
    }


    public void loadStopwords() {
        List<String> lines = Utils.getResourceList("stopwords-utils.txt");
        for (String line : lines) {
            stopWordsSet.add(line);
        }
        logger.info("stopWordSize is {}", stopWordsSet.size());
    }

    public Map<String, Integer> getPosMap() {
        return posMap;
    }

    public Map<String, Double> getWeightMap() {
        return weightMap;
    }

    public Map<String, AtomicInteger> getWordCountTotal() {
        return wordCountTotal;
    }

    public Map<String, AtomicInteger> getWordCountPerUnit() {
        return wordCountPerUnit;
    }

    public void setFilterStopWords(boolean filterStopWords) {
        this.filterStopWords = filterStopWords;
        if (filterStopWords) {
            loadStopwords();
        }
    }

    public void setFilterSingleWord(boolean filterSingleWord) {
        this.filterSingleWord = filterSingleWord;
    }

    public void addStopWords(List<String> stopwords) {
        stopWordsSet.addAll(stopwords);
        this.filterStopWords = true;
    }

    public void setAutoFilter(boolean autoFilter) {
        this.autoFilter = autoFilter;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
