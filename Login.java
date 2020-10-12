package com;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import org.apache.tomcat.jni.User;


/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	static String username;
	static String passwo;
	static String getName;
	static String getPs;
	static HttpSession session;

	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login () {
		   super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		getName = request.getParameter("uname");
		getPs = request.getParameter("psw");
		
		//connessione al database
		try {
			Class.forName("org.postgresql.Driver");
	
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dab", "user", "falsarone");
		
		
		String qr = "select * from utenti where username=? and password=?";
		
				

		PreparedStatement s = conn.prepareStatement(qr);
        java.sql.Statement st = conn.createStatement();
        ResultSet rss = st.executeQuery("SELECT image_id FROM "+getName); //ottieni tutta la colonna (ma il db è strutturato per avere un solo dato in questa tabella ;)
		java.sql.Statement stchk = conn.createStatement();
		ResultSet rschk = stchk.executeQuery("select emailchk from "+getName);
		
		s.setString(1, getName);
		s.setString(2, getPs);
		ResultSet rs = s.executeQuery();//scorri i dati nel db
		
		//condizione che controlla se l'username e password sono presenti nel database 
		
		if(rs.next() && rss.next() && rschk.next()) {

			
       
			session = request.getSession();

            String userimages = rss.getString("image_id");//ottieni Stringa nella colonna
            session.setAttribute("username", getName);
            session.setMaxInactiveInterval(600);
            session.setAttribute("userimages", userimages);
         
            String mailchk = rschk.getString("emailchk");
            
         
			System.out.println("[0]logged");
			System.out.println("[1]Sessione Abilitata !");
			System.out.println("[2]Cookie non abilitati");
			
			 if (mailchk.contentEquals("s")) {
				 
				 session.setAttribute("chkusername", getName);
		         
		   	    	RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		   		    rd.forward(request, response);
		   		    

		            }else {
		            	response.sendRedirect("erroremail.jsp");
		            }

			
		}else {
			System.out.println("no user or password find");
			//inserisci il redirect di errore qui ;)
			response.sendRedirect("Errorenouser.jsp");
		}			
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			response.sendRedirect("Errorenouser.jsp");

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
