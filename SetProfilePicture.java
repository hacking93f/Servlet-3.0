package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class SetProfilePicture
 * 
 * 
 * Profile picture System coded by Neo0Hacker / Hacking93f
 * for more information contact me at hacking93f@gmail.com
 * 
 */
@WebServlet("/setprofilepicture")
public class SetProfilePicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String qry ;
	RequestDispatcher rd ;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetProfilePicture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	

		String emilia = request.getParameter("emilia");
		String rem = request.getParameter("rem");
		String rem1 = request.getParameter("rem1");
		String rem1jpg = request.getParameter("rem1jpg");
		String rem2 = request.getParameter("rem2");
		HttpSession session = request.getSession();
		String uname = session.getAttribute("username").toString();
		session.setMaxInactiveInterval(600);
		
try {
	Class.forName("org.postgresql.Driver");
	Connection conn;
	conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dab", "user", "falsarone");
	
			
		if ( emilia != null ) {
			
			qry = "UPDATE "+uname+"\r\n"
					+ "SET image_id = 'uimages/emilia.png' ";
			
			
			
			session.setAttribute("userimages", "uimages/emilia.png");
			
			Statement s1 = conn.createStatement();
			s1.executeUpdate(qry);
			
			rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			
			
		} else if( rem != null) {
			
			qry = "UPDATE "+uname+"\r\n"
					+ "SET image_id = 'uimages/Rem.png' ";
			
			
			
			session.setAttribute("userimages", "uimages/Rem.png");
			
			Statement s1 = conn.createStatement();
			s1.executeUpdate(qry);
			
			rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			
		}else if( rem1 != null) {
			
			qry = "UPDATE "+uname+"\r\n"
					+ "SET image_id = 'uimages/rem1.png' ";
			
			
			
			session.setAttribute("userimages", "uimages/rem1.png");
			
			Statement s1 = conn.createStatement();
			s1.executeUpdate(qry);
			
			rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			
		}else if( rem1jpg != null) {
			
			qry = "UPDATE "+uname+"\r\n"
					+ "SET image_id = 'uimages/rem1.jpg' ";
			
			
			
			session.setAttribute("userimages", "rem1.jpg");
			
			Statement s1 = conn.createStatement();
			s1.executeUpdate(qry);
			
			rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			
		}else if( rem2 != null) {
			
			qry = "UPDATE "+uname+"\r\n"
					+ "SET image_id = 'uimages/rem2.jpg' ";
			
			
			
			session.setAttribute("userimages", "uimages/rem2.jpg");
			
			Statement s1 = conn.createStatement();
			s1.executeUpdate(qry);
			
			rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			
		}

		
} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
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
