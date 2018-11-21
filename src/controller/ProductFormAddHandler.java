package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HandlerFactory;
import controller.RequestHandler;
import exception.NotAuthorizedException;
import model.Role;
import service.ShopService;

public class ProductFormAddHandler extends RequestHandler {

	public ProductFormAddHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotAuthorizedException {
		Role[] roles = {Role.ADMINISTRATOR};
		checkRole(request, roles);

		RequestDispatcher view = request.getRequestDispatcher("productFormAdd.jsp");
		view.forward(request, response);
	}
	
}
