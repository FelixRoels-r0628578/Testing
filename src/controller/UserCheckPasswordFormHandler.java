package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HandlerFactory;
import controller.RequestHandler;
import service.ShopService;

public class UserCheckPasswordFormHandler extends RequestHandler {

	public UserCheckPasswordFormHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("userCheckPasswordForm.jsp");
		view.forward(request, response);
	}

}
