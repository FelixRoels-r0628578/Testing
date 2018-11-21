package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HandlerFactory;
import controller.RequestHandler;
import exception.DBException;
import exception.NotAuthorizedException;
import model.Role;
import service.ShopService;

public class ProductDeleteHandler extends RequestHandler {

	public ProductDeleteHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, NotAuthorizedException {
		Role[] roles = {Role.ADMINISTRATOR};
		checkRole(request, roles);
		
		String id = request.getParameter("id");
		try {
			this.shopService.deleteProduct(id);
		} catch (DBException e) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("ShopService deleteProduct() error", e.getMessage());
			request.setAttribute("errors", errors);
		}

		response.sendRedirect("Controller?action=productOverview");
	}
	
}
