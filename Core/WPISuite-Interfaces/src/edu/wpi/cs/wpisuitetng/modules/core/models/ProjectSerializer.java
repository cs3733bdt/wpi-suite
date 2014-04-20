package edu.wpi.cs.wpisuitetng.modules.core.models;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ProjectSerializer implements JsonSerializer<Project> {

	@Override
	public JsonElement serialize(Project src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject deflated = new JsonObject();
		
		deflated.addProperty("name", src.getName());
		deflated.addProperty("idNum", src.getIdNum());
		deflated.addProperty("owner", src.getOwner().toJSON());
		deflated.addProperty("supportedModules", "");
		
		return null;
	}

}
