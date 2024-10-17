package com.claz.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SuccessResponse {
	private String message;
	private String userId; // Only if you want to include userId in the response

	// Constructor with just a message
	public SuccessResponse(String message) {
		this.message = message;
	}

	// Constructor with message and userId
	public SuccessResponse(String message, String userId) {
		this.message = message;
		this.userId = userId; // Optional
	}

	// Getters and Setters
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
