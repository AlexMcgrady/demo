package com.crd.demo.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum EnableSysEnum implements JsonSerializable{
	ENABLED("启用"), DISABLED("禁用");

	private String text;

	private EnableSysEnum(String text) {
		this.text = text;
	}

	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("enableStatus");
		generator.writeString(toString());

		generator.writeFieldName("text");
		generator.writeString(text);
		generator.writeEndObject();

	}
	@Override
	public void serializeWithType(JsonGenerator generator, SerializerProvider provider,
			TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
	}
}
