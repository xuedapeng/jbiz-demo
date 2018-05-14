package org.jbiz.service;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;


public class ServiceServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ServiceServlet.class);

	@Override
	public void init() {

		// do anything once on start
		
		
	}
}
