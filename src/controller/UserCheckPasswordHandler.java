package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import exception.NotAuthorizedException;
import service.ShopService;

public class UserCheckPasswordHandler extends RequestHandler {

	public UserCheckPasswordHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, NotAuthorizedException, ServletException {
		String password = request.getParameter("password");
		String id = request.getParameter("id");
		
		System.out.println("Id is here:" + id);
		
		try {
			boolean correct = this.shopService.checkPassword(password, id);
			request.setAttribute("correct", correct);
			request.setAttribute("id", id);
			RequestDispatcher view = request.getRequestDispatcher("userCheckPassword.jsp");
			view.forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("password", e.getMessage());
			request.setAttribute("errors", errors);
			request.setAttribute("id", id);
			this.handlerFactory.getHandler("userCheckPasswordForm").handleRequest(request, response);
		}
	}

}
