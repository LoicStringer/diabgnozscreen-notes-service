package com.diabgnozscreennotesservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT,reason="Patient id mismatching: patient id has been modified.")
public class PatientIdMismatchException extends Exception {

	private static final long serialVersionUID = 1L;

	public PatientIdMismatchException() {
		super();
	}

	
}
