package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HandlerFactory;
import controller.RequestHandler;
import exception.ModelException;
import exception.NotAuthorizedException;
import model.Adres;
import model.Role;
import service.ShopService;

public class ShopOrderOverviewHandler extends RequestHandler {

	public ShopOrderOverviewHandler(ShopService shopService, HandlerFactory handlerFactory) {
		super(shopService, handlerFactory);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException, IOException, ServletException {
		String straat = request.getParameter("straat");
		String nummer = request.getParameter("nummer");
		String bus = request.getParameter("bus");
		String postcode = request.getParameter("postcode");
		String plaats =request.getParameter("plaats");
		int busInt=Integer.parseInt(bus);
		int postcodeInt=Integer.parseInt(postcode);
		try {
			Adres adres = new Adres(straat,plaats,nummer,busInt,postcodeInt);
			request.setAttribute("adres",adres);
		} catch (ModelException e) {
			e.printStackTrace();
		}



		RequestDispatcher view = request.getRequestDispatcher("shopOrderOverview.jsp");
		view.forward(request, response);

	}


}
