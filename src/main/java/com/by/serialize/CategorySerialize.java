package com.by.serialize;

import java.io.IOException;

import com.by.model.RuleCategory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CategorySerialize extends JsonSerializer<RuleCategory> {

	@Override
	public void serialize(RuleCategory value, JsonGenerator jgen,
						  SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeNumber(value.getId());
	}

}
