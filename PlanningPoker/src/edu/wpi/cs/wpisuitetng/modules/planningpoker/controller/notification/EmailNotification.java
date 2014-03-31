package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

//import javax.mail;

/*
 * This class contains the methods used to notify users by email when a planning
 * poker session has started and/or ended.
 */

public class EmailNotification {
	
	Session session;
	
	public void SendEmails (Session session) 
	{
		Project project = session.getProject();
		User[] users = project.getTeam();
		
		for (int i = 0; i < users.length; i++) {
			SendEmail(users[i]);
		}
	}
	
	public void SendEmail(User user) {
		
	}
}
