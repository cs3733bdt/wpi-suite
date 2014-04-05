package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

public interface Chatable {

	/**
	    * Called when an unknown TOC event occurs
	    *
	    * @param str The TOC event
	    */
	   public void unknown(String str);

	   /**
	    * Called when a TOC error occurs
	    *
	    * @param str The TOC error
	    * @param var An optional vailable to insert into the error message
	    */
	   public void error(String str,String var);

	   /**
	    * Called when an IM is received
	    *
	    * @param from Who is the IM from
	    * @param message What is the message
	    */
	   public void im(String from,String message);
	
}
