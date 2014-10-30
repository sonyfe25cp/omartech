package com.omartech.campusRobot.tools;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omartech.campusRobot.template.BITUnionPoster;
import com.techwolf.campusrecruiting.model.JD;
import com.techwolf.campusrecruiting.service.JDService;

public class AutoPoster {
	static Logger logger = LoggerFactory.getLogger(AutoPoster.class);
	
	JD jd;
	
	public static void main(String[] args) {
		AutoPoster ap = new AutoPoster();
		ap.gogogo();
	}
	
	public void gogogo(){
		String today = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		today = "2014-08-21";
		
		logger.info(today);
		
		
		JDService jdService = new JDService();
		
		List<JD> list = jdService.findByDate(today);
		
		logger.info("list size : {}, at {}",list.size(), today);
		
		String listHtml = BITUnionPoster.create(list);
		
		logger.info(listHtml);
		
	}
	
	

}
