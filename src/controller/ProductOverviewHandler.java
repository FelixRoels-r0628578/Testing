package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HandlerFactory;
import controller.RequestHandler;
import exception.DBException;
import exception.NotAuthorizedException;
import model.Product;
import model.Role;
import service.ShopService;

public class ProductOverviewHandler extends RequestHandler {

	public ProductOverviewHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException, ServletException, IOException {
		Role[] roles = {Role.ADMINISTRATOR, Role.CUSTOMER};
		checkRole(request, roles);
		
		List<Product> products;
		try {
			products = this.shopService.getProducts();
			request.setAttribute("products", products);
			RequestDispatcher view = request.getRequestDispatcher("productOverview.jsp");
			view.forward(request, response);
		} catch (DBException e) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("ShopService getProducts() error", e.getMessage());
			request.setAttribute("errors", errors);
		}	
	}
	
}
