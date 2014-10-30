package com.omartech.campusRobot.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.techwolf.campusrecruiting.model.JD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BITUnionPoster {
    static Logger logger = LoggerFactory.getLogger(BITUnionPoster.class);

    public static String create(List<JD> jds){
		StringBuilder sb = new StringBuilder();
		
		Map<String, List<JD>> groupMap = new HashMap<>();
		for(JD jd : jds){

            String site = jd.getSiteName();
            if(!(site.contains("北京") || site.contains("首都") || site.contains("中央") || site.contains("中国") || site.contains("大学"))){
                logger.error("{} passed ", site);
                continue;
            }
            if(jd.getUrl().contains("job.njtu.edu.cn") || jd.getUrl().contains("job.ncss.org.cn")){
                continue;
            }
            List<JD> tmpList = groupMap.get(site);
			if(tmpList == null){
				tmpList = new ArrayList<>();
			}
			tmpList.add(jd);
			groupMap.put(site, tmpList);
		}
		
		List<Entry<String, List<JD>>> sortList = new ArrayList<>(groupMap.entrySet());
		Collections.sort(sortList, new Comparator<Entry<String, List<JD>>>(){

			@Override
			public int compare(Entry<String, List<JD>> o1,
					Entry<String, List<JD>> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
			
		});
		
		for(Entry<String, List<JD>> entry : sortList){
			String siteName = entry.getKey();
			List<JD> tmpJds = entry.getValue();
            if(tmpJds.size() < 10){
                continue;
            }
			
			sb.append("[size=6]"+siteName+"[/size]");
			sb.append("[list=1]");

			for(JD jd : tmpJds){
				StringBuilder lineBuilder = new StringBuilder();
				lineBuilder.append("[*]");
				lineBuilder.append("[url=");//[url=bbbbbbb]aaaaaa[/url]
				lineBuilder.append(jd.getUrl().replaceAll("jsessionid=.*", ""));
				lineBuilder.append("]");
				lineBuilder.append(jd.getTitle());
                if(jd.getCompany() != null) {
                    lineBuilder.append("\t公司: " + jd.getCompany());
                }
				lineBuilder.append("[/url]");
				sb.append(lineBuilder.toString()+"\n");
			}
		    sb.append("[/list]");
		}

		return sb.toString();
	}
	

}
