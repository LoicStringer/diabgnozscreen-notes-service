package com.diabgnozscreennotesservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="Not allowed to set an Id value.")
public class NoteIdSettingNotAllowedException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoteIdSettingNotAllowedException() {
		super();
	}

}
