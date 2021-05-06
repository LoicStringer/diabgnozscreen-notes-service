package com.diabgnozscreennotesservice.model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class Note {
	
	private ObjectId noteId;
	private Long patientId;
	private String patientLastName;
	private String noteContent;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
	
	public Note() {
	}

	public ObjectId getNoteId() {
		return noteId;
	}

	public void setNoteId(ObjectId noteId) {
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", patientId=" + patientId + ", patientLastName=" + patientLastName
				+ ", noteContent=" + noteContent + ", createdDate=" + createdDate + ", lastModifiedDate="
				+ lastModifiedDate + "]";
	}
	
}
