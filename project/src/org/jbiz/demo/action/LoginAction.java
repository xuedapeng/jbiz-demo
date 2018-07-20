package org.jbiz.demo.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.jbiz.demo.logic.LoginLogic;
import org.jbiz.demo.logic.param.LoginLogicParam;


@Produces("application/json;charset=UTF-8")
@Path("login")
public class LoginAction extends BaseZAction {

	static Logger logger = Logger.getLogger(LoginAction.class);

	@POST
	@Path("login.do")
	public String login(
			@FormParam("account") String account,
			@FormParam("password") String password,
			@Context HttpServletRequest request) {
		
		LoginLogicParam logicParam = new LoginLogicParam(null, null, request);
		
		return new LoginLogic().process(logicParam);
	}
}
