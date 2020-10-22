package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ResetPassword
 * 
 */
@WebServlet("/resetpassword")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		String uName = request.getParameter("uname");
		String psw = request.getParameter("psw");
		String rptPsw = request.getParameter("rptpsw");
		String newPsw = request.getParameter("newpsw");
		String rptNewPsw = request.getParameter("rptnewpsw");

		
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dab","user","falsarone");
			
			
			PreparedStatement ps = conn.prepareStatement("select * from utenti where username=? and password=?");
			ps.setString(1, uName);
			ps.setString(2, psw);
			
			if(ps.execute()) {
				
				
				if(newPsw.contentEquals(rptNewPsw)) {
					
            //resetta la password nella tabella utenti solo nella riga del nome utente!
			PreparedStatement statement = conn.prepareStatement("UPDATE utenti SET password=? WHERE username=?");
			statement.setString(1, newPsw);
			statement.setString(2, uName);
			statement.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("UPDATE "+uName+" SET passwordrc=?");
			ps2.setString(1, newPsw);
			
			response.sendRedirect("login-register.jsp");
			
				}else {
					
					response.sendRedirect("errorepassword.jsp");
				}
			
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			//errore password non trovata 
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
