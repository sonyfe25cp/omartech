package com.omartech.engine.service;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.util.Version;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by omar on 15-1-5.
 */
public class AIndexService {
    protected int cpu = 4;
    String indexPath;
    protected Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_48);

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    public static String formatTime(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }

    public static Date parseTime(String time) {
        try {
            return DateUtils.parseDate(time, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }
}
