package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	//fatto, ho creato l atra porzione di codice nel jsp (controller.jsp) quindi quando carichero su github dovro ricordarmi
	//di allegarlo
	
	
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		

		
		HttpSession session = request.getSession();
		
		String uidtoken = request.getParameter("uidtoken");
		String idtoken = session.getAttribute("idtoken").toString();
	
		
		
		
		if(idtoken.contentEquals(uidtoken)) {
			
			
			session.setAttribute("isgood", "isgood");
			String uname = session.getAttribute("username").toString();
			
			session.setAttribute("chkusername", uname);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			//da qui devo mandare la spunta mail checked nel db.
			//e creare nella classe login statement che cointrolla il mail checked

			
			try {
				Class.forName("org.postgresql.Driver");
				
				Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dab", "user", "falsarone");

				Statement s = conn.createStatement();
				s.executeUpdate("UPDATE "+uname+"\r\n"
						+ "SET emailchk = 's' ");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		}else {
			
				RequestDispatcher rd = request.getRequestDispatcher("controller.jsp");
				rd.forward(request, response);
			
			
		
			
		}
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
