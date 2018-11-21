package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HandlerFactory;
import controller.RequestHandler;
import exception.NotAuthorizedException;
import model.Product;
import model.Role;
import service.ShopService;

public class ProductConfirmDeleteHandler extends RequestHandler {

	public ProductConfirmDeleteHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, NotAuthorizedException, ServletException {
		Role[] roles = {Role.ADMINISTRATOR};
		checkRole(request, roles);
		
		String id = request.getParameter("id");
		
		try {
			Product p = this.shopService.getProduct(id);
			request.setAttribute("product", p);
			RequestDispatcher view = request.getRequestDispatcher("productConfirmDelete.jsp");
			view.forward(request, response);
		} catch(Exception e) {
			this.handlerFactory.getHandler("productOverview").handleRequest(request, response);
		}
	}
	
}
