package com.omartech.review_quality.english.preprocessing;

import com.omartech.review_quality.english.model.Product;
import com.omartech.review_quality.english.model.Review;
import com.omartech.review_quality.english.service.DataService;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by OmarTech on 15-1-26.
 */
public class StructureRawData {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StructureRawData.class);


    @Option(name = "-f", usage = "the absolute path of file")
    private String filePath;

    @Option(name = "-t", usage = "0: review; 1: productinfo; default: 0")
    private int workType = 0;

    @Option(name = "-db", usage = "the ip:port of mysql ")
    private static String dbPath = "127.0.0.1:3306";

    @Option(name = "-u", usage = "the username of mysql")
    private static String username = "root";

    @Option(name = "-p", usage = "the password of mysql")
    private static String password = "";

    @Option(name = "-c", usage = "the nume of cpu")
    private int cpu = Runtime.getRuntime().availableProcessors();

    @Option(name = "-test", usage = "if not 0, break after 10000;default : 0")
    private int test = 0;

    static ThreadLocal<Connection> db = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + dbPath + "/reviewquality", username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

    public static void main(String[] args) {
        new StructureRawData().doMain(args);
    }


    public void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        // if you have a wider console, you could increase the value;
        // here 80 is also the default
        parser.setUsageWidth(80);

        try {
            // parse the arguments.
            parser.parseArgument(args);
            logger.info("work with {} cpu", cpu);
            long start = System.currentTimeMillis();
            if (workType == 0) {
                logger.info("process reviews");
                if (StringUtils.isEmpty(filePath)) {
                    filePath = "/Users/omar/data/opinion_spam/real/reviewsNew.txt";
                }
                structureReview(filePath);
            } else if (workType == 1) {
                logger.info("process product");
                if (StringUtils.isEmpty(filePath)) {
                    filePath = "/Users/omar/data/opinion_spam/real/productinfo.txt";
                }
                structureProduct(filePath);
            } else {
                logger.error("wrong arg about workType");
                return;
            }
            close();
            long end = System.currentTimeMillis();
            logger.info("it used : {} s", (end - start) / 1000.0);

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    ThreadPoolExecutor executor =
            new ThreadPoolExecutor(cpu, cpu, 1, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(cpu * 2), new ThreadPoolExecutor.CallerRunsPolicy());

    public void close() throws Exception {
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    public void structureProduct(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line = null;
        int counter = 0;

        boolean flag = false;
        List<String> product = null;
        while ((line = br.readLine()) != null) {

            if (line.equals("BREAK-REVIEWED")) {
                //a new product start
                if (product != null) {
//                    new StructureProductWorker(product).run();
                    executor.submit(new StructureProductWorker(product));
                }
                product = new ArrayList<>();
            } else {
                product.add(line);
            }
            counter++;
            if (counter % 10000 == 0) {
                logger.info("process {} line", counter);
                if (test != 0) {
                    break;
                }
            }
        }
        br.close();
    }

    class StructureProductWorker implements Runnable {
        List<String> lines;

        public StructureProductWorker(List<String> lines) {
            this.lines = lines;
        }

        @Override
        public void run() {
            Connection conn = db.get();
            String meta = lines.get(0);
            String[] split = meta.split("\t");
            Product product = new Product();
//            if(split.length != 8){
//                logger.info("line: {}, length:{}", meta, split.length);
//                return;
//            }
            product.setProductId(split[0]);
            product.setName(split[1]);
            product.setType(split[2]);
            if (split.length > 3) {
                product.setBrand(split[3]);
            }
            if (split.length > 4) {

                product.setSalesPrice(split[4]);
            }
            if (split.length > 5) {
                product.setListPrice(split[5]);
            }
            if (split.length > 7) {
                product.setDescription(split[7]);
            }

            StringBuilder categoryPath = new StringBuilder();
            for (int i = 1; i < lines.size(); i++) {
                lines.get(i);
                categoryPath.append(lines.get(i) + ";");
            }
            product.setCategoryPath(categoryPath.toString());

            try {
                DataService.insertProduct(product, conn);
            } catch (SQLException e) {
                logger.info(meta);
                e.printStackTrace();
            }
        }
    }

    public void structureReview(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line = null;
        int counter = 0;
        while ((line = br.readLine()) != null) {
//            new StructureReviewWorker(line).run();
            executor.submit(new StructureReviewWorker(line));
            counter++;
            if (counter % 10000 == 0) {
                logger.info("process {} line", counter);
                if (test != 0) {
                    break;
                }
            }
        }
        br.close();
    }

    class StructureReviewWorker implements Runnable {
        String line;

        public StructureReviewWorker(String line) {
            this.line = line;
        }

        @Override
        public void run() {

            Connection connection = db.get();

            String[] split = line.split("\t");
            Review review = new Review();
            review.setReviewerId(split[0]);
            review.setProductId(split[1]);
            String originDate = split[2];
            String createdAt = split[2];
//            try {
//                Date date = DateUtils.parseDate(originDate, new String[]{"MMM dd, yyyy"});//June 19, 2001
//                createdAt = DateFormatUtils.format(date, "yyyy-MM-dd");
//            } catch (Exception e) {
//                logger.info("origin: {}, split.size:{}", originDate, split.length);
//                logger.error("line: {}", line);
//                e.printStackTrace();
//                System.exit(0);
//            }
            review.setCreatedAt(createdAt);

            review.setHelpfulCount(Integer.parseInt(split[3]));
            review.setFeedbackCount(Integer.parseInt(split[4]));
            review.setRating(Math.round(Float.parseFloat(split[5])));
            review.setTitle(split[6]);
            review.setBody(split[7]);

            try {
                DataService.insertReview(review, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


}
