package com.crd.demo.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import lombok.Getter;

/**@Description: 性别枚举
 * @Author: C.J
 * @Since: 2017年3月30日 下午4:33:08
 */
@Getter
public enum GenderSysEnum implements JsonSerializable{
	MALE("男"),FEMALE("女"),OTHER("保密");

	private String text;

	private GenderSysEnum(String text) {
		this.text = text;
	}

	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("gender");
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
