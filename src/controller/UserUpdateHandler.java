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

import model.Role;
import model.User;
import service.ShopService;

public class UserUpdateHandler extends RequestHandler {

	public UserUpdateHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, NotAuthorizedException, ServletException {
		Role[] roles = {Role.ADMINISTRATOR, Role.CUSTOMER};
		checkRole(request, roles);
		
		Map<String, String> errors = new HashMap<String, String>();
		
		String id = request.getParameter("id");
		
		User u = null;
		try {
			u = this.shopService.getUser(id);
			setFirstName(request, errors, u);
			setLastName(request, errors, u);
			setEmail(request, errors, u);
			setPassword(request, errors, u);
			
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			if(!u.getId().equals(user.getId())) {
				setRole(request, errors, u);
			}
			
			request.setAttribute("user", u);
			
			if(errors.size() > 0) {
				request.setAttribute("errors", errors);
				this.handlerFactory.getHandler("userFormUpdate").handleRequest(request, response);
			} else {
				try {
					this.shopService.updateUser(u);
					response.sendRedirect("Controller?action=userOverview");
				} catch (DBException e) {
					errors.put("Update error", e.getMessage());
					request.setAttribute("errors", errors);
					this.handlerFactory.getHandler("userFormUpdate").handleRequest(request, response);
				}	
			}
		}  catch(ModelException | DBException e) {
			errors.put("Update error", e.getMessage());
			request.setAttribute("errors", errors);
			this.handlerFactory.getHandler("userOverview").handleRequest(request, response);
		}
	}

}
