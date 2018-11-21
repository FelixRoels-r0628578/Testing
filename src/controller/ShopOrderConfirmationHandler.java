package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.HandlerFactory;
import controller.RequestHandler;
import exception.NotAuthorizedException;
import model.Role;
import service.ShopService;

public class ShopOrderConfirmationHandler extends RequestHandler {

	public ShopOrderConfirmationHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException, IOException, ServletException {
		Role[] roles = {Role.CUSTOMER, Role.ADMINISTRATOR};
		checkRole(request, roles);

		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		
		RequestDispatcher view = request.getRequestDispatcher("shopOrderConfirmation.jsp");
		view.forward(request, response);
	}
	
}
