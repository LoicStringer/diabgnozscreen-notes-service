package com.diabgnozscreennotesservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="This note is not registered.")
public class NoteNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoteNotFoundException() {
		super();
	}

}
