package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.HandlerFactory;
import controller.RequestHandler;
import exception.DBException;
import exception.ModelException;
import exception.NotAuthorizedException;
import model.Product;
import model.Cart;
import model.ProductOrder;
import model.Role;
import service.ShopService;

public class ShopCartAddHandler extends RequestHandler {

	public ShopCartAddHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException, IOException, ServletException {
		Role[] roles = {Role.CUSTOMER, Role.ADMINISTRATOR};
		checkRole(request, roles);
		
		Map<String, String> errors = new HashMap<String, String>();
		
		//Parse quantity String as integer
		String quantityString = request.getParameter("quantity");
		int quantity = 0;
		try {
			quantity = Integer.parseInt(quantityString);
		} catch (NumberFormatException e) {
			errors.put("Add to cart error", "Please enter a valid integer as quantity.");
			request.setAttribute("errors", errors);
			this.handlerFactory.getHandler("productOverview").handleRequest(request, response);
			return;
		}
		
		//Get poduct ID
		String id = request.getParameter("id");
		
		//Get Cart from session
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart == null) {
			cart = new Cart();
		}
		
		ProductOrder productOrder = cart.getOrder(id);
		if(productOrder == null) {
			//If the order didn't exist, create a new one
			try {
				Product p = this.shopService.getProduct(id);
				cart.addProduct(p, quantity);
			} catch (ModelException | DBException e) {
				errors.put("Add to cart error", e.getMessage());
				request.setAttribute("errors", errors);
				this.handlerFactory.getHandler("productOverview").handleRequest(request, response);
				return;
			} 
		} else {
			//If the order already exists, edit the quantity
			try {
				cart.replaceQuantityOrdered(id, quantity);
			} catch (ModelException e) {
				errors.put("Add to cart error", e.getMessage());
				request.setAttribute("errors", errors);
				this.handlerFactory.getHandler("productOverview").handleRequest(request, response);
				return;
			}
		}
		
		//Add cart (back) to the session
		session.setAttribute("cart", cart);
		
		//Go to cart view
		response.sendRedirect("Controller?action=shopCartOverview");
	}
	
}
