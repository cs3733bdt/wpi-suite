package edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel;

import java.util.UUID;

public interface IStorageModel <T>{
	boolean copyFrom(T toCopyFrom);
	String getName();
	UUID getIdentity();
}
