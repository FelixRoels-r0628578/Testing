package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
		Role[] roles = {Role.CUSTOMER, Role.ADMINISTRATOR};
		checkRole(request, roles);

		Map<String, String> errors = new HashMap<>();

		Adres adres = new Adres();
		setStraat(request, errors, adres);
		setNummer(request, errors, adres);
		setBus(request, errors, adres);
		setPostcode(request, errors, adres);
		setPlaats(request, errors, adres);

		request.setAttribute("adres", adres);

		//TODO adressen linken aan session of in een repo steken tijdelijk?
		if(errors.size() > 0){
			request.setAttribute("errors", errors);
			this.handlerFactory.getHandler("shopCartOverview").handleRequest(request, response);
		}


		RequestDispatcher view = request.getRequestDispatcher("shopOrderOverview.jsp");
		view.forward(request, response);
	}

	private void setPlaats(HttpServletRequest request, Map<String, String> errors, Adres adres) {
		String plaats = request.getParameter("plaats");
		try{
			adres.setPlaats(plaats);
		} catch (ModelException e) {
			errors.put("plaats", e.getMessage());
		}
	}

	private void setPostcode(HttpServletRequest request, Map<String, String> errors, Adres adres) {
		try{
			int code = Integer.parseInt(request.getParameter("postcode"));
			adres.setPostcode(code);
		}catch (NumberFormatException ex){
			errors.put("code", "code not valid");
		} catch (ModelException e){
			errors.put("code", e.getMessage());
		}

	}

	private void setBus(HttpServletRequest request, Map<String, String> errors, Adres adres) {
		try {
			int bus = Integer.parseInt(request.getParameter("bus"));
			adres.setBus(bus);
		}catch (NumberFormatException ex){
			errors.put("bus", "bus not valid");
		} catch(ModelException e){
			errors.put("bus", e.getMessage());
		}
	}

	private void setNummer(HttpServletRequest request, Map<String, String> errors, Adres adres) {
		String nummer = request.getParameter("bus");
		try{
			adres.setNummer(nummer);
		} catch(ModelException e){
			errors.put("nummer", e.getMessage());
		}
	}

	private void setStraat(HttpServletRequest request, Map<String, String> errors, Adres adres) {
		String straat = request.getParameter("straat");
		try{
			adres.setStraat(straat);
		} catch (ModelException e){
			errors.put("straat", e.getMessage());
		}
	}


}
