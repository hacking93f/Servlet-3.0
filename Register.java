package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;
import java.util.Random;


/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	Connection conn;

	String getMail;
	public static Integer idtoken;
	public static String SIDTOKEN = "";
	private static String USER_NAME = "hacking93f@gmail.com";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "metti password qui"; // GMail password
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
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
    //sendmail per email di conferma
    
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
    
    
    //Espressione Regolare Controllo Email--------------------
	public static boolean isValid(String a) {
		
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
		"[a-zA-Z0-9_+&*-]+)*@"+
				"(?:[a-zA-Z0-9-]+\\.)+[a-z"+
		"A-Z]{2,7}$";
				
		Pattern pat = Pattern.compile(emailRegex);//ho messo regex che sta per reg expression
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
		
		
		//inizializiamo il nostro fantomatico id token xD
		
		
		Random randidtoken = new Random();
		idtoken = randidtoken.nextInt(100000000-9000000) - 900000;//il range del id token, x non sforare le 10 cifre
		SIDTOKEN = idtoken.toString();
		
		
		
		String uname = request.getParameter("uname");
		String psw = request.getParameter("psw");
		//speriamo che non da l errore in caso in cui non da l errore mi devo ricordare di togliere commento
		getMail = request.getParameter("email");
		String getpsw_repeat = request.getParameter("psw-repeat");
		
		// inizializza la stringa form ecc-sendmail
		from = USER_NAME;
		pass = PASSWORD;
		
//		prendi info dal html
		to = request.getParameterValues("email");
		//sogetto email
		subject = "Java send mail example";
		//corpo email
		body =  "inserisci questo codice per confermare la tua email: "+idtoken;
		
		sendFromGMail(from,pass,to,subject,body);
		
		
		
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
		
		//se qualcosa va storto conn.rollback() del paradigma acid in poche parole o tutto va bene o niente modifiche al db
		if(getpsw_repeat.contentEquals(psw) && isValid(getMail)) { 
			
			
			
			
			//impostiamo qui il famoso fantomatico idtoken per identificare la sessione
			
			Login.session = request.getSession();
			Login.session.setAttribute("idtoken", SIDTOKEN);
			Login.session.setAttribute("isgood", "notgood");

			
			
		    //tabella recupero password db nome colonna passwordrc
			//a fine progetto settare not null la query dell email
            String qry = "CREATE TABLE "+uname+" (\r\n"
            		+ "   email VARCHAR ( 255 ) PRIMARY KEY,\r\n"
            		+ "   passwordrc character(20) not null,\r\n"
            		+ "   image_id VARCHAR(20) not null,\r\n"
            		+ "   emailchk character(1)"
            		+ " )";
            
            
	          
	         Statement stt = conn.createStatement();
	         stt.execute(qry);
	         String qry2 = "INSERT INTO "+uname+" VALUES (?,?,?,?)";
	         PreparedStatement sst = conn.prepareStatement(qry2);
	        sst.setString(1, getMail);
	        sst.setString(2, psw);
	        //qui impostiamo l immagine di profilo di default nel database
	        sst.setString(3, "uimages/Rem.png");
	        sst.setString(4, "n");
	        
	        
	        sst.execute();
	           

			conn.commit();
			
			//qui ce le settiamo per la sessione cosi da mandarci direttamente nella pagina da loggato
			
			Login.session.setAttribute("username", uname);
			Login.session.setAttribute("userimages", "uimages/Rem.png");
			Login.session.setMaxInactiveInterval(180);

			RequestDispatcher rq = request.getRequestDispatcher("controller.jsp");
			rq.forward(request, response);
			
			
		} 
		
		//naturalmente se cattura gli errori class not foun e sql excpetion va nel blocco catch 
		//quindi per l errore usernm gia esistente è stato sufficente fare csi ;)
		
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//nel blocco catch condizione per il roll back ;)
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
