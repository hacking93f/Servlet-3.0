package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;

	String getMail;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    //Espressione Regolare Controllo Email--------------------
	public static boolean isValid(String a) {
		
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
		"[a-zA-Z0-9_+&*-]+)*@"+
				"(?:[a-zA-Z0-9-]+\\.)+[a-z"+
		"A-Z]{2,7}$";
				
		Pattern pat = Pattern.compile(emailRegex);
		if(a == null)
	
		
		return false;
		
		return pat.matcher(a).matches();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Username o email non corretti");
		
		
		String uname = request.getParameter("uname");
		String psw = request.getParameter("psw");
		getMail = request.getParameter("email");
		String getpsw_repeat = request.getParameter("psw-repeat");
		
		try {
			Class.forName("org.postgresql.Driver");
		 
		
		String nm="user";
		String ps="falsarone";
		String url="jdbc:postgresql://localhost:5432/dab";
		
		conn = DriverManager.getConnection(url, nm, ps);
		
		conn.setAutoCommit(false);
		
		
		//login
		String sql = "INSERT INTO utenti VALUES (?,?)";
		
		PreparedStatement st= conn.prepareStatement(sql);
		st.setString(1, uname);
		st.setString(2, psw);
		int a = st.executeUpdate();
		
		if(getpsw_repeat.contentEquals(psw) && isValid(getMail)) { 
			
			
		    //tabella recupero password db nome colonna passwordrc
			//a fine progetto settare not null la query delll email
            String qry = "CREATE TABLE "+uname+" (\r\n"
            		+ "   email VARCHAR ( 255 ) PRIMARY KEY,\r\n"
            		+ "   passwordrc character(20)"
            		+ " )";
            
            
            
         
	          
	         Statement stt = conn.createStatement();
	         stt.execute(qry);
	         String qry2 = "INSERT INTO "+uname+" VALUES (?,?)";
	         PreparedStatement sst = conn.prepareStatement(qry2);
	        sst.setString(1, getMail);
	        sst.setString(2, psw);
	        
	        
	        sst.execute();
	           

			conn.commit();
			

			RequestDispatcher rq = request.getRequestDispatcher("index.html");
			rq.forward(request, response);
		} //qui inserisci che c'è stato un errore ;)
		
		//naturalmente se cattura gli errori class not foun e sql excpetion va nel blocco catch 
		//quindi per l errore usernm gia esistente è stato sufficente fare csi ;)
		
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(conn != null) {
				try {
					
					//creare una pagina apposta per l'errore username e password gia esistenti?? 
					//cmq vabbè per adesso ti lascio questo 
					response.sendRedirect("Errore.jsp");

					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			
			}
			
			
			
			
			
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
