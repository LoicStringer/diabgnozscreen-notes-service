package com.diabgnozscreennotesservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.diabgnozscreennotesservice.dao.NoteDao;
import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.exception.NoteIdSettingNotAllowedException;
import com.diabgnozscreennotesservice.exception.NoteNotFoundException;
import com.diabgnozscreennotesservice.exception.PatientIdMismatchException;
import com.diabgnozscreennotesservice.exception.UnknownPatientIdException;
import com.diabgnozscreennotesservice.model.Note;

@Service
public class NoteService {

	@Autowired
	private NoteDao noteDao;

	
	public Page<Note> getPatientHistory (Long patientId, Pageable pageable) throws UnknownPatientIdException{
		return noteDao.getPatientHistory(patientId,pageable);
	}
	
	public List<Note> getPatientHistoryAsNotesList(Long patientId) {
		return noteDao.getPatientHistoryAsNotesList(patientId);
	}
	
	public Note saveNote (Note noteToSave) throws NoteIdSettingNotAllowedException {
		return noteDao.saveNote(noteToSave);
	}
	
	public Note updateNote (Note noteToUpdate) throws NoteNotFoundException, PatientIdMismatchException {
		return noteDao.updateNote(noteToUpdate);
	}

}
