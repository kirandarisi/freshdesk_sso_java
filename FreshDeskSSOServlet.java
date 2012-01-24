import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FreshDeskSSOServlet extends HttpServlet {

	private static final long serialVersionUID = 7027717204177362374L;
	private final String BASE_URL = "http://demo.freshdesk.com/login/sso";
	private final String sharedSecret = "df02b88d971c78807a644adeae260919";


	@Override

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		try {

				String url = this.getSSOURL();
				response.sendRedirect(url);

			} catch (Exception e) {
				//Handle appropriate code
			}
	}
	
	private static String getHash(String text) throws Exception {
		    MessageDigest m=MessageDigest.getInstance("MD5");
 	     	m.update(text.getBytes(),0,text.length());
   			return ""+new BigInteger(1,m.digest()).toString(16);
	}



	private String getSSOURL() {

		String hash;
		String url = null;
		User user; //Get the user details using your current authentication system
		String name = "kiran darisi";// Full name of the user
		String email = "kiran@freshdesk.com";// Email of the user	
		hash = getHash(name + email + sharedSecret);
		
		try {		
			
			url = BASE_URL + "?name="+name+"&email="+email+"&hash=" + hash; 
			
		}catch (Exception e) {
			//Handle appropriate code
			System.out.println("There is an exception while constructing the URL");
			e.printStackTrace();
		}	
		
		return url;
			
	}
}