package org.jbiz.service;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import fw.jbiz.ext.websocket.ZWsHandlerManager;

public class ServiceServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ServiceServlet.class);

	@Override
	public void init() {
		// websocket start
		logger.info("start ZWsHandlerManager.initialize()");
		ZWsHandlerManager.initialize();
		
		
	}
}
