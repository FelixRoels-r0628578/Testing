package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.ModelException;
import exception.NotAuthorizedException;
import model.Product;
import model.Role;
import model.User;
import service.ShopService;

public abstract class RequestHandler {

	protected ShopService shopService;
	protected HandlerFactory handlerFactory;
	
	public RequestHandler(ShopService shopService, HandlerFactory handlerFactory) {
		this.shopService = shopService;
		this.handlerFactory = handlerFactory;
	}
	
	public abstract void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, NotAuthorizedException, ServletException;
		
	protected void checkRole(HttpServletRequest request, Role[] roles) throws NotAuthorizedException {
		boolean notAuthorized = true;
		
		if(roles.length == 0) {
			notAuthorized = false;
		}
		else {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			Role userRole = (user == null ? null : user.getRole());
			
			if(userRole == null) {
				notAuthorized = true;
			} else {
				for(Role r : roles) {
					if(r.equals(userRole)) {
						notAuthorized = false;
					}
				}
			}
		}
		
		if(notAuthorized) {
			throw new NotAuthorizedException("Insufficient rights");
		}
	}
	
	//PRODUCT Helpers
	
	protected void setProductId(HttpServletRequest request, Map<String, String> errors, Product p) {
		String id = request.getParameter("id");
		try {
			p.setId(id);
		} catch(IllegalArgumentException e) {
			errors.put("id", e.getMessage());
		}
	}

	protected void setDescription(HttpServletRequest request, Map<String, String> errors, Product p) {
		String description = request.getParameter("description");
		try {
			p.setDescription(description);
		} catch(IllegalArgumentException e) {
			errors.put("description", e.getMessage());
		}
	}

	protected void setPrice(HttpServletRequest request, Map<String, String> errors, Product p) {
		String priceString = request.getParameter("price");
		if(priceString == null || priceString.trim().equals("")) {
			errors.put("price", "No price given");
		} else {
			try {
				double price = Double.parseDouble(priceString);
				p.setPrice(price);
			} catch(NumberFormatException e) {
				errors.put("price", "Price not valid");
			} catch(IllegalArgumentException e) {
				errors.put("price", e.getMessage());
			}
		}
	}
	
	//USER Helpers
	
	protected void setUserID(HttpServletRequest request, Map<String, String> errors, User u) {
		String id = request.getParameter("id");
		try {
			u.setId(id);
		} catch(ModelException e) {
			errors.put("id", e.getMessage());
		}
	}
	
	protected void setFirstName(HttpServletRequest request, Map<String, String> errors, User u) {
		String firstName = request.getParameter("firstName");
		try {
			u.setFirstName(firstName);
		} catch(ModelException e) {
			errors.put("firstName", e.getMessage());
		}
	}
	
	protected void setLastName(HttpServletRequest request, Map<String, String> errors, User u) {
		String lastName = request.getParameter("lastName");
		try {
			u.setLastName(lastName);
		} catch(ModelException e) {
			errors.put("lastName", e.getMessage());
		}
	}
	
	protected void setEmail(HttpServletRequest request, Map<String, String> errors, User u) {
		String email = request.getParameter("email");
		try {
			u.setEmail(email);
		} catch(ModelException e) {
			errors.put("email", e.getMessage());
		}
	}
	
	protected void setPassword(HttpServletRequest request, Map<String, String> errors, User u) {
		String password = request.getParameter("password");
		try {
			u.setAndHashPassword(password);
		} catch(ModelException e) {
			errors.put("password", e.getMessage());
		} catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
			errors.put("password", "There was a problem while saving your password. Please contact the webmaster.");		}
	}
	
	protected void setRole(HttpServletRequest request, Map<String, String> errors, User u) {
		String roleString = request.getParameter("role");
		Role role = Role.valueOf(roleString);
		try {
			u.setRole(role);
		} catch(ModelException e) {
			errors.put("role", e.getMessage());
		}
	}
	
}
