package com.fixit.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RandomConverter {

	public String alphaNumericGenerator(int any) {
		String alphaNumericString = RandomStringUtils.randomAlphabetic(any);

		return alphaNumericString;
	}

	public String stringASCIIGenerator(int any) {
		String asciiString = RandomStringUtils.randomAlphabetic(any);
		if(asciiString.length()>50){
		asciiString = asciiString.substring(0, 50);
		}
		return asciiString;
	}
	
	public String hashStringToUrlConverter(String hashKey,String baseUrl) {
		String hashcode =baseUrl+"email?emailCode="+hashKey;
		return hashcode;
	}
}
