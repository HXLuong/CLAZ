package com.claz.models.momo;

import com.claz.constants.Constant;
import com.google.gson.annotations.SerializedName;

public enum Language {

	@SerializedName("vi")
	VI(Constant.LANGUAGE_VI),

	@SerializedName("en")
	EN(Constant.LANGUAGE_EN);

	private final String value;

	Language(String value) {
		this.value = value;
	}

	public static Language findByName(String name) {
		for (Language type : values()) {
			if (type.getLanguage().equals(name)) {
				return type;
			}
		}
		return null;
	}

	public String getLanguage() {
		return value;
	}

}
