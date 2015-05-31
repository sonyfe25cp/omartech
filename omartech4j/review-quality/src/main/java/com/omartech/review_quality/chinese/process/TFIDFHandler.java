package com.omartech.review_quality.chinese.process;

import com.omartech.review_quality.chinese.DBService;
import com.omartech.review_quality.chinese.model.KCompany;
import com.omartech.review_quality.chinese.model.KReview;
import com.techwolf.omartech_utils.Utils;
import com.techwolf.omartech_utils.vocabulary.Vocabulary;
import org.apache.commons.io.FileUtils;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by OmarTech on 15-3-24.
 */
public class TFIDFHandler {
    static Logger logger = LoggerFactory.getLogger(TFIDFHandler.class);
    @Option(name = "-db", usage = "the ip:port of mysql ")
    private static String dbPath = "127.0.0.1:3306";

    @Option(name = "-u", usage = "the username of mysql")
    private static String username = "root";

    @Option(name = "-p", usage = "the password of mysql")
    private static String password = "";

    public static void main(String[] args) {
        TFIDFHandler tfidfHandler = new TFIDFHandler();
        try {
            tfidfHandler.compute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String vobFile = "kreviews";

    void compute() throws SQLException, IOException {
        Vocabulary vocabulary = null;
        File vob = new File("/tmp/" + vobFile + ".voc");
        Connection conn = db.get();
        List<KCompany> kCompanies = DBService.findKCompanyOrderByCompanyId(conn, 0, 200);
        if (vob.isFile() && vob.exists()) {
            vocabulary = Vocabulary.loadFromDB(vobFile);
        } else {
            vocabulary = new Vocabulary();
            vocabulary.setSingleWord(true);
            for (KCompany company : kCompanies) {
                List<KReview> kReviews = DBService.findKReviewsByCompanyId(conn, company.getCompany_id(), 0, 200000);
                for (KReview kReview : kReviews) {
                    String cons = kReview.getCons();
                    String pros = kReview.getPros();
                    String advices = kReview.getAdvice();
                    vocabulary.addText(cons);
                    vocabulary.addText(pros);
                    vocabulary.addText(advices);
                }
            }
            vocabulary.addOver();
//            vocabulary.adjust(0.1f, 0.9f);
            vocabulary.saveToDisk(vobFile);
        }

        Map<String, Integer> posMap = vocabulary.getPosMap();
        List<Map.Entry<String, Integer>> postList = new ArrayList<>(posMap.entrySet());
        Utils.sortMapStringAndInteger(postList);
        File posFile = new File("/tmp/posfile");
        if (posFile.exists()) {
            FileUtils.deleteQuietly(posFile);
        }
        for (Map.Entry<String, Integer> entry : postList) {
            FileUtils.write(posFile, entry.getValue() + " - " + entry.getKey() + "\n", true);
        }


        File trainDataFile = new File(trainData);
        if (trainDataFile.exists()) {
            FileUtils.deleteQuietly(trainDataFile);
        }
        File testDataFile = new File(testData);
        if (testDataFile.exists()) {
            FileUtils.deleteQuietly(testDataFile);
        }
        int ccc = 0;
        for (KCompany company : kCompanies) {
            int company_id = company.getCompany_id();
            List<KReview> kReviews = DBService.findKReviewsByCompanyId(conn, company_id, 0, 200000);

            boolean flag = (ccc++) % 5 == 1 ? true : false;

            for (KReview kReview : kReviews) {
                String cons = kReview.getCons();
                String pros = kReview.getPros();
                String advices = kReview.getAdvice();

                String string = cons + pros + advices;

                Map<Integer, Double> integerDoubleMap = vocabulary.generateVectorMap(string);
//                for (Map.Entry<Integer, Double> entry : integerDoubleMap.entrySet()) {
//                    System.out.println(entry.getKey() + " -- " + entry.getValue());
//                }
                int weight = kReview.getWeight();
                int rank = 0;
                switch (weight) {
                    case 1200:
                        rank = 5;
                        break;
                    case 800:
                        rank = 4;
                        break;
                    case 600:
                        rank = 3;
                        break;
                    case 400:
                        rank = 2;
                        break;
                    case 100:
                        rank = 1;
                        break;
                    default:
                        rank = 0;
                }
//                System.out.println(rank + " qid:" + company_id + " " + toTrainData(integerDoubleMap));

                integerDoubleMap = new HashMap<>();

                int cusor = posMap.size() + 1;
                //length
                int length = string.length();//length
                integerDoubleMap.put(cusor++, length / 10 + 0.0);

                //set
                Set<String> set = new HashSet<>();
                for (char c : string.toCharArray()) {
                    set.add(c + "");
                }
                int setFeature = set.size();
                integerDoubleMap.put(cusor++, setFeature / 10 + 0.0);

                int useful_num = kReview.getUseful_num();
                integerDoubleMap.put(cusor++, useful_num / 10 + 0.0);

                int rating = kReview.getRating();
                integerDoubleMap.put(cusor++, rating + 0.0);

                int employee_status = kReview.getEmployee_status();
                integerDoubleMap.put(cusor++, employee_status + 0.0);

                int recommend_friend = kReview.getRecommend_friend();
                integerDoubleMap.put(cusor++, recommend_friend + 0.0);

                String feature = rank + " qid:" + company_id + " " + toTrainData(integerDoubleMap) + " #" + kReview.getId();
                System.out.println(feature);
                if (!flag) {
                    FileUtils.write(trainDataFile, feature + "\n", true);
                } else {
                    FileUtils.write(testDataFile, feature + "\n", true);
                }
            }
            logger.info("company_id : {}, reviews: {}", company_id, kReviews.size());
        }
        logger.info("companys : {}", kCompanies.size());


    }


    static String trainData = "/tmp/train.data";
    static String testData = "/tmp/test.data";

    private String toTrainData(Map<Integer, Double> integerDoubleMap) {
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(integerDoubleMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o1.getKey() - o2.getKey();
            }
        });
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Double> entry : list) {
            sb.append((entry.getKey() + 1) + ":" + entry.getValue() + " ");
        }
        return sb.toString();
    }

    static ThreadLocal<Connection> db = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + dbPath + "/kanzhun", username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };
}
