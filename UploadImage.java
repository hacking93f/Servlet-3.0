package com;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


/**
 * Servlet implementation class UploadImage
 */
@WebServlet("/uploadimage")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2 mb
maxFileSize = 1024 * 1024 * 30, // 30 mb
maxRequestSize = 1024 * 1024 * 50)

public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String fileName ;
	static String s;

	
	
	//il nome del file uploadato è contenuto nel content-disposition header tipo:
	//from data; name="dataFile"; filename="FOTO.JPG";
	//lascio il metodo ma non lo uso, da problemi ----------------------------------------------------------
	//ho fatto sotto un metodo tramite le sottostringhe,
	//parto dall ultima lettera dell url semplicemente in poi facile facile , senza metodi impicciati come questo 
private String extractFileName(Part part) {
		
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for  (String s : items) {
			if(s.trim().startsWith("filename")) { //nome file conenuto nel header content disposition
					return s.substring(s.indexOf("=") + 2, s.length() -1);
				}
			}
		return "";
	}


       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		response.setContentType("text/html/;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//voglio vedermi bene tutta la classe part per i servlet
	    Part part = request.getPart("file");
	    
	    // fileName = extractFileName(part);
	    fileName = part.getSubmittedFileName();
	    
	    //questo è il "metodo" sottostringha che ho menzionato al commento del metodo sopra
	    //ovviamente cambiando percorso cambia la lunghezza dell url e quindi 
	    //dovro cambiare anche questo start index della sottostringa P.S FIXATO PER SEMPRE!
	    //(lascio cosi anche se ogni persona avrà percorsi differenti alla fine 
	    // la stringa ottenuta da part.getSubmittedFileName, ti ritorna senza i vari / slash.
	    // e piu caratteri identificativi consente di immagazinare piu immagini senza bug
	    //in eventuali problemi futuri ho il metodo  extract file name qui sopra, basta che tolgo il commento
	    
	    
	    // fileName = fileName.substring(1); //sembra non servire piu lol. in caso torna il problema togliere commento
	   
	    
		String savePath = "C:\\Users\\HackinG\\eclipse-java-web\\sito\\WebContent\\uimages\\"+fileName;
		File fileSaveDir = new File(savePath);
		//lascio il system out per debugging lo tolgo poi
		System.out.println(fileName);
		
		//developer folder location
		//C:\\Users\\HackinG\\eclipse-java-web\\sito\\WebContent\\uimages\\
		//final location ovviamente sul mio pc 
	    //C:\\Users\\HackinG\\xampp\\tomcat\\webapps\\ROOT\\uimages\\
		
		part.write(savePath); //nome file uploadato
		HttpSession session = request.getSession();
		String uname = session.getAttribute("username").toString();
		session.setMaxInactiveInterval(600);
				
				try {
					
					Class.forName("org.postgresql.Driver");
					
					//a fine progetto ovviamente la query dovra diventare update!
					Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dab", "user", "falsarone");
					
					
					Statement ps = conn.createStatement();
					ps.executeUpdate("UPDATE "+uname+"\r\n"
							+ "SET image_id = 'uimages/"+fileName+"' ");
					
					
					RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
					rd.forward(request, response);
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		
	}

}
