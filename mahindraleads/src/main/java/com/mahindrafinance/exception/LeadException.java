package com.mahindrafinance.exception;

import java.util.List;

public class LeadException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LeadException(String message, List<String> errorMessages) {
		super(message);
	}

}