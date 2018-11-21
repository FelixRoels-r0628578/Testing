package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.Role;
import service.ShopService;

public class UserFormSignUpHandler extends RequestHandler {

	public UserFormSignUpHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("roles", Role.values());
		
		RequestDispatcher view = request.getRequestDispatcher("userFormSignUp.jsp");
		view.forward(request, response);
	}

}
