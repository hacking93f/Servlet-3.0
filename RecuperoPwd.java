package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



// necessita driver sql jdbc + javax mail + activation(per javax mail)
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servlet implementation class RecuperoPwd
 * 
 * 
 * 
 * Coded by Neo0Hacker(hacking93f)
 * all right reserverd.
 * for more information contact me at: hacking93f@gmail.com
 * 
 */
@WebServlet("/recuperopwd")
public class RecuperoPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String getName;
	String getEmail;
	
	    private static String USER_NAME = "hacking93f@gmail.com";  // GMail user name (just the part before "@gmail.com")
	    private static String PASSWORD = "fdaniele93"; // GMail password
	    private static String RECIPIENT ;
	    //corpo mail
	    String subject = "Java send mail example";
        String body = "Welcome to JavaMail!";
        String from ;
        String pass;
        String[] to;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperoPwd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Mail Sucessfully sended ;) ! ");
		
		

		getName = request.getParameter("uname");
		getEmail = request.getParameter("email");
		
		
			
		try {
			
			
			
			Class.forName("org.postgresql.Driver");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dab", "user", "falsarone");
			
		
			
			
			
			
			Statement s = conn.createStatement();
			Statement ss = conn.createStatement();
		    ResultSet rs = s.executeQuery("select * from "+getName);
		    ResultSet rss = ss.executeQuery("select * from "+getName);
		   
			
			
		        if (rs.next() && rss.next()) {
				
			    
		     		
				    String recPwd = rs.getString("passwordrc");
					//colonna email tabella username
				    String recmail = rss.getString("email");
				    
				  
				
				
				
				
				
				String from = USER_NAME;
				String pass = PASSWORD;
				
		//		prendi info dal html
				String[] to = request.getParameterValues("email");
				//sogetto email
				String subject = "Java send mail example";
				//corpo email
				String body =  "La tua password è : "+recPwd ;
				
				
				
				//se l email inserita conincide con i dati del database continua mandando l email!! ;)
				if(recmail.contentEquals(getEmail)){
				
				
				//avvio il metodo sendmail
				sendFromGMail(from, pass, to, subject, body);
				
			}
				
				else {
					response.sendRedirect("Errore.jsp");
				}
				
		        }  
			    
		        
		
		
		
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				

				//	errore nome utente non trovato pagina html
				response.sendRedirect("Errore.jsp");
				
				
			}
			
			
				
			
			
          } 
			
		    
		   
		
		
		
		
	
	
		
		
		
		 private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
		        Properties props = System.getProperties();
		        String host = "smtp.gmail.com";
		        props.put("mail.smtp.starttls.enable", "true");
		        props.put("mail.smtp.host", host);
		        props.put("mail.smtp.user", from);
		        props.put("mail.smtp.password", pass);
		        props.put("mail.smtp.port", "587");
		        props.put("mail.smtp.auth", "true");

		        Session session = Session.getDefaultInstance(props);
		        MimeMessage message = new MimeMessage(session);

		        try {
		            message.setFrom(new InternetAddress(from));
		            InternetAddress[] toAddress = new InternetAddress[to.length];

		            // To get the array of addresses
		            for( int i = 0; i < to.length; i++ ) {
		                toAddress[i] = new InternetAddress(to[i]);
		            }

		            for( int i = 0; i < toAddress.length; i++) {
		                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		            }

		            message.setSubject(subject);
		            message.setText(body);
		            Transport transport = session.getTransport("smtp");
		            transport.connect(host, from, pass);
		            transport.sendMessage(message, message.getAllRecipients());
		            transport.close();
		        }
		        catch (AddressException ae) {
		            ae.printStackTrace();
		        }
		        catch (MessagingException me) {
		            me.printStackTrace();
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
