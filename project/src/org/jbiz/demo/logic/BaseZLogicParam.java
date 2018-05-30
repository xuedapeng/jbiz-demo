package org.jbiz.demo.logic;

import javax.servlet.http.HttpServletRequest;

import fw.jbiz.logic.ZLogicParam;

public class BaseZLogicParam extends ZLogicParam {

	public BaseZLogicParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);

	}

}
