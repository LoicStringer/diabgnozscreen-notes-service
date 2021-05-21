package com.diabgnozscreennotesservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="Unknown patient id.")
public class UnknownPatientIdException extends Exception{

	private static final long serialVersionUID = 1L;

	public UnknownPatientIdException() {
		super();
	}

}
