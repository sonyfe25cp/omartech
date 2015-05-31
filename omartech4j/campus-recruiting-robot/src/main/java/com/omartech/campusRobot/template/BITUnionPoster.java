package com.omartech.campusRobot.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.omartech.data.gen.Job;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BITUnionPoster {
    static Logger logger = LoggerFactory.getLogger(BITUnionPoster.class);

    public static String create(List<Job> jds) {
        StringBuilder sb = new StringBuilder();

        Map<String, List<Job>> groupMap = new HashMap<>();
        for (Job jd : jds) {

            String site = jd.getSiteName();
            site = StringUtils.deleteWhitespace(site);
            if (!(site.contains("北京") || site.contains("首都") ||
                    site.contains("中央") || site.contains("中国") ||
                    site.contains("大学") || site.contains("学院") ||
                    site.contains("BBS") || site.contains("bbs") ||
                    site.contains("就业指导")
            )) {
                logger.error("{} passed ", site);
//                continue;
                site = "其他";
                jd.setSiteName(site);
            }
            if (site.contains("公司")) {
                site = "公司直投";
                jd.setSiteName(site);
            }
            if (site.endsWith("大学")) {
                site = site + "就业指导中心";
                jd.setSiteName(site);
            }

            if (jd.getUrl().contains("job.njtu.edu.cn")) {
                continue;
            }
//            if(jd.getUrl().contains("job.ncss.org.cn")){
//                continue;
//            }

//            String url = jd.getUrl();
//            String shortIt = ShortUrl.shortIt(url);
//            jd.setUrl(shortIt);

            List<Job> tmpList = groupMap.get(site);
            if (tmpList == null) {
                tmpList = new ArrayList<>();
            }
            tmpList.add(jd);
            groupMap.put(site, tmpList);
        }

//List<Job> qt = groupMap.get("其他");
//logger.info("qt : {}", qt.size());

        List<Entry<String, List<Job>>> sortList = new ArrayList<>(groupMap.entrySet());
        Collections.sort(sortList, new Comparator<Entry<String, List<Job>>>() {
            @Override
            public int compare(Entry<String, List<Job>> o1, Entry<String, List<Job>> o2) {
                int size = o1.getValue().size();
                int size2 = o2.getValue().size();
                return size2 - size;
            }

//			@Override
//			public int compare(Entry<String, List<Job>> o1,
//					Entry<String, List<Job>> o2) {
//				return o1.getKey().compareTo(o2.getKey());
//			}

        });

        for (Entry<String, List<Job>> entry : sortList) {
            String siteName = entry.getKey();
            List<Job> tmpJds = entry.getValue();
            if (tmpJds.size() < 3) {
                continue;
            }
            sb.append("[size=6]" + siteName + "[/size]");
            sb.append("[list=1]");
            for (Job jd : tmpJds) {
                StringBuilder lineBuilder = new StringBuilder();
                lineBuilder.append("[*]");
                lineBuilder.append("[url=");//[url=bbbbbbb]aaaaaa[/url]
                lineBuilder.append(jd.getUrl().replaceAll("jsessionid=.*", ""));
                lineBuilder.append("]");
                lineBuilder.append(jd.getTitle());
                if (jd.getCompany() != null) {
                    lineBuilder.append("\n\t公司: " + jd.getCompany());
                }
                lineBuilder.append("[/url]");
                sb.append(lineBuilder.toString() + "\n");
            }
            sb.append("[/list]");
//            }
        }

        return sb.toString();
    }


}
