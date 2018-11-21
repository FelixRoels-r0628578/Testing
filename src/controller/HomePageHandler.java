package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.NotAuthorizedException;
import service.ShopService;

public class HomePageHandler extends RequestHandler {

	public HomePageHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, NotAuthorizedException, ServletException {
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);
	}

}
