package com.fixit.utility;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
