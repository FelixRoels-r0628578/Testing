package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HandlerFactory;
import exception.ControllerException;
import exception.DBException;
import exception.NotAuthorizedException;
import service.ShopService;


@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ShopService shopService; 
    private HandlerFactory handlerFactory;

    public Controller() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	
    	ServletContext context = getServletContext();
    	
    	Properties properties = new Properties();
    	Enumeration<String> parameterNames = context.getInitParameterNames();
    	while(parameterNames.hasMoreElements()) {
    		String popertyName = parameterNames.nextElement();
    		properties.setProperty(popertyName, context.getInitParameter(popertyName));
    	}
    	
    	try {
			this.shopService = new ShopService(properties);
			InputStream input = context.getResourceAsStream("/WEB-INF/handler.xml");
			Properties handlerProperties = new Properties();
			handlerProperties.loadFromXML(input);
			this.handlerFactory = new HandlerFactory(handlerProperties, this.shopService);
		} catch (DBException e) {
			System.out.println("Error making new ShopService");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading handler properties");
			e.printStackTrace();
		} catch (ControllerException e) {
			System.out.println("Error creating new handler: " + e.getMessage());
			e.printStackTrace();
		}
    	
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		setColour(request, response);
		
		String action = request.getParameter("action");
		if(action == null) {
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
			
		} else {
			if(action.equals("changeColour")) {
				action = changeColour(request, response);
			}
			
			try {
				this.handlerFactory.getHandler(action).handleRequest(request, response);
			} catch (NotAuthorizedException e) {
				Map<String, String> errors = new HashMap<String, String>();
				errors.put("Authorization", e.getMessage());
				request.setAttribute("errors", errors);
				try {
					this.handlerFactory.getHandler("userLogout").handleRequest(request, response);
				} catch (NotAuthorizedException ignore) {
					//TODO: Almost impossible to get this exception here
					ignore.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * COOKIE & COLOUR
	 */
	
	private Cookie getColourCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			 return createCookie("colourCookie", "greenAccent.css", response);
		} else {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("colourCookie")) {
					return cookies[i];
				}
			}
			
			return createCookie("colourCookie", "greenAccent.css", response);
		}		
	}	
	
	private Cookie createCookie(String name, String value, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		response.addCookie(cookie);
		return cookie;
	}

	private void setColour(HttpServletRequest request, HttpServletResponse response) {
		Cookie colourCookie = getColourCookie(request, response);
		request.setAttribute("cssFile", colourCookie.getValue());
	}

	private String changeColour(HttpServletRequest request, HttpServletResponse response) {
		Cookie colourCookie = getColourCookie(request, response);
		
		String newValue;
		
		if(colourCookie.getValue().equals("greenAccent.css")) {
			newValue = "redAccent.css";
		} else {
			newValue = "greenAccent.css";
		}
		
		createCookie("colourCookie", newValue, response);
		request.setAttribute("cssFile", newValue);

		return request.getParameter("currentPage");
	}
	
}
