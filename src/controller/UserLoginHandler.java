package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import exception.ModelException;
import model.User;
import service.ShopService;

public class UserLoginHandler extends RequestHandler {

	public UserLoginHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		try {
			User u = this.shopService.getUserIfAuthenticated(id, password);
			if(u != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", u);
				response.sendRedirect("Controller");
			} else {
				Map<String, String> errors = new HashMap<String, String>();
				errors.put("User not found", "Username or password incorrect");
				request.setAttribute("errors", errors);
				RequestDispatcher view = request.getRequestDispatcher("index.jsp");
				view.forward(request, response);
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | ModelException e) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("Login password algorithm error", e.getMessage());
			request.setAttribute("errors", errors);
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		}
	}

}
