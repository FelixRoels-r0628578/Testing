package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.NotAuthorizedException;
import model.User;
import service.ShopService;

public class UserSignUpHandler extends RequestHandler {

	public UserSignUpHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, NotAuthorizedException, ServletException {
		Map<String, String> errors = new HashMap<String, String>();
		
		User u = new User();
		
		setUserID(request, errors, u);
		setFirstName(request, errors, u);
		setLastName(request, errors, u);
		setEmail(request, errors, u);
		setPassword(request, errors, u);
		setRole(request, errors, u);
		
		request.setAttribute("user", u);
		
		if(errors.size() > 0) {
			request.setAttribute("errors", errors);
			this.handlerFactory.getHandler("userFormSignUp").handleRequest(request, response);
		} else {
			try {
				this.shopService.addUser(u);
				response.sendRedirect("Controller");
			} catch(Exception e) {
				errors.put("Add error", e.getMessage());
				request.setAttribute("errors", errors);
				this.handlerFactory.getHandler("userFormSignUp").handleRequest(request, response);
			}		
		}
	}

}
