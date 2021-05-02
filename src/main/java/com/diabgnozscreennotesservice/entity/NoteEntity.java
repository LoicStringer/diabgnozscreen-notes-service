package com.diabgnozscreennotesservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Note")
public class NoteEntity {
	
	@Id
	private Long noteId;
	
	private Long patientId;
	private String patientName;
	private String noteContent;
	
	public NoteEntity() {
	}

	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	@Override
	public String toString() {
		return "NoteEntity [noteId=" + noteId + ", patientId=" + patientId + ", patientName=" + patientName
				+ ", noteContent=" + noteContent + "]";
	}
	
	
}
