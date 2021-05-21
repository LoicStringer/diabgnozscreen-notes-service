package com.diabgnozscreennotesservice.model;

import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class Note {
	
	private String noteId;
	private Long patientId;
	private String patientLastName;
	private String noteContent;
	private Instant createdDate;
	private Instant lastModifiedDate;
	
	public Note() {
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", patientId=" + patientId + ", patientLastName=" + patientLastName
				+ ", noteContent=" + noteContent + ", createdDate=" + createdDate + ", lastModifiedDate="
				+ lastModifiedDate + "]";
	}
	
}
