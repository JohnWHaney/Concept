// package com.siemens.polarion.ws.example.projectfinder;
 
  //
 // Found on Slack - Andy Holton post 
//
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import com.polarion.alm.ws.client.WebServiceFactory;
import com.polarion.alm.ws.client.projects.ProjectWebService;
import com.polarion.alm.ws.client.session.SessionWebService;
import com.polarion.alm.ws.client.tracker.TrackerWebService;
import com.polarion.alm.ws.client.types.projects.Project;
import javax.xml.rpc.ServiceException;

public class ProjectFinder 
{
 public static void main(String[] args) throws Exception 
 {
		int i;
		 
        String puser = "jwhaney_req";
        String pw = "demo";
        String serverUrl = "https://concept.polarion.com";
		 
        //Enter data using BufferReader 
        BufferedReader readinput;

		FileHandler fh = null;
		
	try
	{
		Logger logger = Logger.getLogger("ProjectFinder");  		
	    fh = new FileHandler("ProjectFinder.log",true);  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  	
				
		logger.info("Begin"); 		

		logger.info("Collecting inputs..."); 		
		logger.info(" Enter serverUrl"); 		
		
        System.out.print("\n");  
        System.out.print("- enter in the Polarion URL to connnect to\n ");  
        System.out.print("- example: " + serverUrl + " \n");  
        System.out.print("->");  		
		readinput =  new BufferedReader(new InputStreamReader(System.in)); 
        serverUrl = new String(readinput.readLine()); 

		logger.info(" serverUrl ->" + serverUrl + "<-"); 		
		logger.info(" Enter puser"); 		

  		
        System.out.print("- enter in the Polarion Username to log in with \n");  
        System.out.print("- example: " + puser + " \n");  
        System.out.print("->");  
		readinput =  new BufferedReader(new InputStreamReader(System.in)); 
        puser = new String(readinput.readLine()); 

		logger.info(" puser ->" + puser + "<-"); 		
		logger.info(" Enter pw"); 		


        System.out.print("- enter in the password for " + puser + " \n");  
        System.out.print("- example: " + pw + " \n");  
        System.out.print("->");  
		readinput =  new BufferedReader(new InputStreamReader(System.in)); 
        pw = new String(readinput.readLine()); 

		logger.info(" pw ->" + pw + "<-"); 		

 
        System.out.print("------ \n");
        System.out.print(" - Connecting to " + serverUrl + "\n");  
        System.out.print(" - with username: " + puser + " \n");  
        System.out.print(" - with password: " + pw + " \n");  
        System.out.print("--- \n");  
 	           
		logger.info("Connecting..."); 		           
        try {
                WebServiceFactory factory = new WebServiceFactory(serverUrl + "/polarion/ws/services/");
                
                SessionWebService sessionService = factory.getSessionService();
                TrackerWebService trackerService = factory.getTrackerService();
                ProjectWebService projectService = factory.getProjectService();
                
                sessionService.logIn(puser, pw);
				
				//  
				// This method is used currently only for Teamcenter Security Services:
				//
				// void logInWithToken(java.lang.String mechanism,
                //    java.lang.String username,
                //    java.lang.String token)
                // throws java.rmi.RemoteException
				//
				// mechanism - the mechanism which client request for authentication (server must be configured for given mechanism otherwise login will be rejected)
				// username - the name of the user to log-in.
				// token - the token of the user to log-in.
				//

			logger.info("Connected"); 		           
			System.out.print(" - Connected \n");  
			System.out.print("--- \n");  
			System.out.print(" - Listing Projects... \n\n");  
			logger.info("Listing Projects..."); 		           
					
                Project[] projects = projectService.getDeepContainedProjects(projectService.getRootProjectGroup().getUri());
                
				for ( 
						i = 0; i < projects.length; i++) 
						{ 
							Project project = projects[i]; 
							if ( project.getId() != null)
						    System.out.print(project.getId() + "\n"); 
						} 

			logger.info("Projects listed"); 		           

			System.out.print("\n");  
			System.out.print("--- \n");  
			System.out.print(" - Finished. \n");            
			System.out.print("------ \n");  

			logger.info("End"); 		  
           } catch (MalformedURLException | ServiceException | RemoteException e) 
			{
              e.printStackTrace();
			  logger.severe(e.getMessage());
			  logger.log(Level.SEVERE, e.getMessage(), e);
//			  logger.severe(e.getStackTrace().toString());
			  
			  System.out.println("\n");
//			  System.out.println("!!!!!!!!!!!!");
//			  System.out.println("!!! Check the URL and User and password.");
//			  System.out.println("!!! Use the following syntax to set the Polarion user and password used to connect to the Polarion URL.");
//			  System.out.println("!!!   java [-options] ProjectFinder -url Polarion_url -user Polarion_user -password password");
//			  System.out.println("!!!!!!!!!!!!\n\n");			   				
			}
	} catch (IOException e)
			{
			 e.printStackTrace();
			} finally 
				{
					if (fh !=null ) fh.close();
				}
 }
}
