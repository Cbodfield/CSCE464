package classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
/**
 * 
 * This is the main utils class used for reading/writing from our properties file
 * 
 *
 */
public class UserUtils {
	public String path;
	Properties p;
	ServletContext c;
	FileInputStream fis = null;

	
	String propFilePath;
	//String propFilePath = "C:\\Users\\Owner\\Documents\\Internet Systems 464\\464-HW1\\src\\user.properties";
  

    public UserUtils(ServletContext context) {  
		c = context;
    	

    	p = new Properties();

      

    	try { 
    		
    		propFilePath = c.getRealPath("/user.properties");
    	    fis = new FileInputStream(propFilePath);

    	    //p.load(fis);
    	    p.load(fis);

    	
    	    System.out.println("FileNotFound");

    	} catch (IOException e) {

    	    System.out.println("");

    	} finally {

    	    if (null != fis)

    	    {

    	        try

    	        {

    	            fis.close();

    	        }

    	        catch (Exception e)

    	        {

    	            e.printStackTrace();

    	        }

    	    }

    	}

    	

    

    }  

  

    public boolean userExist(String user, String passp) {  

    	boolean exists = false;

		String pass = p.getProperty(user);  

		System.out.println("Pass is :" + pass );

		
		if(pass == null){
			
			return false;
		}
		if(pass.equals(passp))
		{
			exists = true;
		}
			

			

			return exists;



    }  

    

    public boolean addUser(String usr, String password){

    	

    	p.setProperty(usr, password);

    	try {

			p.store(new FileOutputStream(propFilePath), null);
			return true;
		} catch (FileNotFoundException e) {
			
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}

    }
}
