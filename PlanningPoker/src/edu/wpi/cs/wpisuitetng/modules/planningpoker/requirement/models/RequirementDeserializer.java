/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 *
 */
public class RequirementDeserializer implements JsonDeserializer<Requirement> {

	@Override
	public Requirement deserialize(JsonElement requirementElement, Type requirementType,
			JsonDeserializationContext context) throws JsonParseException {
		 JsonObject deflated = requirementElement.getAsJsonObject();
		 System.err.println("Requirement: " + deflated);
		 
		 if (!deflated.has("id")) {
			 throw new JsonParseException("The serialized Requirement did not contain the required id field.");
		 }
		 
		 String id = null;
		 String name = null;
		 String description = null;
		 
		 if (deflated.has("id")) {
			 id = deflated.get("id").getAsString();
		 }
		 if (deflated.has("name")) {
			 name = deflated.get("name").getAsString();
		 }
		 if (deflated.has("description")) {
			 description = deflated.get("description").getAsString();
		 }
		 
		 Requirement inflated = new Requirement(name, description);
		 
		 inflated.setId(Integer.parseInt(id));
		 
		 return inflated;
	}
}
