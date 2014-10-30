package com.omartech.campusRobot.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.techwolf.campusrecruiting.model.JD;

public class BITUnionPoster {
	
	
	public static String create(List<JD> jds){
		StringBuilder sb = new StringBuilder();
		
		Map<String, List<JD>> groupMap = new HashMap<>();
		for(JD jd : jds){
			String site = jd.getSiteName();
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
			
			sb.append("[size=6]"+siteName+"[/size]");
			sb.append("[list=1]");

			for(JD jd : tmpJds){
				StringBuilder lineBuilder = new StringBuilder();
				lineBuilder.append("[*]");
				lineBuilder.append("[url=");//[url=bbbbbbb]aaaaaa[/url]
				lineBuilder.append(jd.getUrl());
				lineBuilder.append("]");
				lineBuilder.append(jd.getTitle());
				lineBuilder.append("[/url]");
				sb.append(lineBuilder.toString()+"\n");
			}
		}
		sb.append("[/list]");
		
		return sb.toString();
	}
	

}
