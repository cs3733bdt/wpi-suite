package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

public class EmptyDeckException extends Exception {
	public EmptyDeckException() {
		super();
	}
	
	public EmptyDeckException(String message) {
		super(message);
	}
}
