package com.bookapp.model.exceptions;

public class DataAccessException extends RuntimeException {
	private static final long serialVersionUID = -7398644024842208011L;

	public DataAccessException(String message) {
		super(message);

	}
}
