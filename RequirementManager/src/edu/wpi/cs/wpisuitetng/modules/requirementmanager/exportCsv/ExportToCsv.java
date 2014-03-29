package edu.wpi.cs.wpisuitetng.modules.requirementmanager.exportCsv;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/*
 * This class will be used to export requirements into CSV files. The class is incomplete
 * and not fully functional. Its build paths need to be configured. Needs to have a button
 * in Janeway to function.
 * 
 * @author Doruk Uzunoglu
 */
public class ExportToCsv  {

	
	private static void exportToCsvFile (String sourceFileName, int reqId) 
	{
		
		// Get the requested requirement
		RequirementModel requestedReqModel = null;
		Requirement requestedReq = requestedReqModel.getRequirement(reqId);
		  
		
		
		try
		{
			// Write the requested requirement's info to csv file
			FileWriter writer = new FileWriter(sourceFileName);
			writer.append(requestedReq.getName());
			writer.append(';');
			writer.append(requestedReq.getDescription());
			writer.append(';');
			//writer.append(requestedReq.getType());
			//writer.append(';');
			//writer.append(requestedReq.getPriority());
			//writer.append(requestedReq.getNotes());
			//writer.append(';');
			//writer.append(requestedReq.getHistory());
			//writer.append(';');
			
			writer.flush();
			writer.close();
		
			
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}
}
