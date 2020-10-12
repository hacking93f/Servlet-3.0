package com;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Servlet implementation class UploadImage
 */
@WebServlet("/uploadimage")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
maxFileSize = 1024 * 1024 * 10,
maxRequestSize = 1024 * 1024 * 50)

public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Integer imgid = 1 ; 
	static String fileName ;
	static String s;
	
	
	
	
private String extractFileName(Part part) {
		
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for  (String s : items) {
			if(s.trim().endsWith("jpg") || s.trim().endsWith("bmp") || s.trim().endsWith("swf") || s.trim().endsWith("gif") || s.trim().endsWith("png")) { //nome file
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
		fileName = extractFileName(part);
		String savePath = "C:\\Users\\HackinG\\eclipse-java-web\\sito\\WebContent\\uimages\\"+fileName;
		File fileSaveDir = new File(savePath);
	
		part.write(savePath+File.separator); //nome file uploadato
		
		String uname = Login.session.getAttribute("username").toString();
		Login.session.setMaxInactiveInterval(600);
				
				
				try {
					
					Class.forName("org.postgresql.Driver");
					
					//a fine progetto ovviamente la query dovra diventare update!
					Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dab", "user", "falsarone");
					
					
					PreparedStatement ps = conn.prepareStatement("INSERT INTO image"+uname+" VALUES (?,?)");
					ps.setString(1, savePath);
					ps.setInt(2, 1);
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		
	}

}
