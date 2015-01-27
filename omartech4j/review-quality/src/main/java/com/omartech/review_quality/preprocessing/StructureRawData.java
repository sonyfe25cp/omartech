package com.omartech.review_quality.preprocessing;

import com.omartech.review_quality.model.Product;
import com.omartech.review_quality.model.Review;
import com.omartech.review_quality.service.DataService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by OmarTech on 15-1-26.
 */
public class StructureRawData {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StructureRawData.class);

    public static void main(String[] args) {
        StructureRawData srd = new StructureRawData();
        try {
//            srd.structureReview("/Users/omar/data/opinion_spam/real/reviewsNew.txt");
            srd.structureProduct("/Users/omar/data/opinion_spam/real/productinfo.txt");
            srd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int cpu = Runtime.getRuntime().availableProcessors();

    //    ThreadPoolExecutor executor =
//            new ThreadPoolExecutor(cpu, cpu, 1, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(cpu * 2), new ThreadPoolExecutor.CallerRunsPolicy());
//
    public void close() throws Exception {
//        executor.shutdown();
//        executor.awaitTermination(1, TimeUnit.DAYS);
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
                    new StructureProductWorker(product).run();
                }
                product = new ArrayList<>();
            } else {
                product.add(line);
            }
            counter++;
            if (counter % 10000 == 0) {
                logger.info("process {} line", counter);
                break;
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
            new StructureReviewWorker(line).run();
//            executor.submit(new StructureReviewWorker(line));
            counter++;
            if (counter % 10000 == 0) {
                logger.info("process {} line", counter);
                break;
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


    static ThreadLocal<Connection> db = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/reviewquality", "root", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };


}
