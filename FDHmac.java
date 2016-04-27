import java.io.IOException;
import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream ;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class FDHmac
{


    private static final long serialVersionUID = 7027717204177362374L;
    private static final String BASE_URL = "https://demo.freshdesk.com/login/sso";
    private static final String sharedSecret = "75b5deb96tthbf2bb9e72f55ae22a0ffb";

    public static String hashToHexString(byte[] byteData)
    {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            // NB! E.g.: Integer.toHexString(0x0C) will return "C", not "0C"            
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String getHMACHash(String name,String email,long timeInMillis) throws Exception {
        byte[] keyBytes = sharedSecret.getBytes();
        String movingFact =name+sharedSecret+email+timeInMillis;
        byte[] text = movingFact.getBytes();
        
        String hexString = "";
        Mac hmacMD5;
        try {
          hmacMD5 = Mac.getInstance("HmacMD5");
          SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
          hmacMD5.init(macKey);
          byte[] hash =  hmacMD5.doFinal(text);
          hexString = hashToHexString(hash);

        } catch (Exception nsae) {
            System.out.println("Caught the exception");
        }
                return hexString;
    }

    public static void main(String[] args)
      throws NoSuchAlgorithmException, InvalidKeyException

    {

        String hash;
        String url = null;
        //User user; //Get the user details using your current authentication system
        String name = "Freshdesk";// Full name of the user
        String email = "kiran@freshdesk.com";// Email of the user
        long timeInSeconds = System.currentTimeMillis()/1000; 
        
        
        try {       
            
            hash = getHMACHash(name,email,timeInSeconds);
            url = BASE_URL + "?name="+encodeUrl(name)+"&email="+encodeUrl(email)+"&timestamp="+timeInSeconds+"&hash=" + hash; 
            
        }catch (Exception e) {
            //Handle appropriate code
            System.out.println("There is an exception while constructing the URL");
            e.printStackTrace();
        }   
        System.out.println(url);
    }
    
    static String encodeUrl(String uri) {
      try {
        return URLEncoder.encode(uri, "UTF8");
      } 
      catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
    
}
