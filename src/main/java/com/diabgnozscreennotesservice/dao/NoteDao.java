package com.diabgnozscreennotesservice.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.entity.NoteEntity;
import com.diabgnozscreennotesservice.exception.NoteIdSettingNotAllowedException;
import com.diabgnozscreennotesservice.exception.NoteNotFoundException;
import com.diabgnozscreennotesservice.exception.PatientIdMismatchException;
import com.diabgnozscreennotesservice.exception.UnknownPatientIdException;
import com.diabgnozscreennotesservice.mapper.NoteMapper;
import com.diabgnozscreennotesservice.model.Note;
import com.diabgnozscreennotesservice.repository.NoteRepository;

@Repository
public class NoteDao {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NoteMapper noteMapper;

	public Page<Note> getPatientHistory(Long patientId, Pageable pageable) throws UnknownPatientIdException {
		checkPatientId(patientId);
		Page<NoteEntity> patientHistoryEntity = noteRepository.findByPatientId(patientId, pageable);
		Page<Note> patientHistory = patientHistoryEntity.map(n -> noteMapper.noteEntityToNote(n));
		return patientHistory;
	}
	
	public List<Note> getPatientHistoryAsNotesList(Long patientId) {
		return noteMapper.noteEntitiesListToNotesList(noteRepository.findByPatientId(patientId));

	}

	public Note saveNote(Note noteToSave) throws NoteIdSettingNotAllowedException {
		preventResourceIdBreach(noteToSave);
		NoteEntity savedNote = noteRepository.save(noteMapper.noteToNoteEntity(noteToSave));
		return noteMapper.noteEntityToNote(savedNote);
	}

	public Note updateNote(Note noteToUpdate)
			throws NoteNotFoundException, PatientIdMismatchException {
		checkNoteBeforeUpdate(noteToUpdate);
		NoteEntity updatedNote = noteRepository.findById(noteToUpdate.getNoteId()).get();
		updatedNote.setNoteContent(noteToUpdate.getNoteContent());
		updatedNote = noteRepository.save(updatedNote);
		return noteMapper.noteEntityToNote(updatedNote);
	}

	private void checkNoteBeforeUpdate(Note noteToCheck)
			throws NoteNotFoundException, PatientIdMismatchException{
		checkNoteId(noteToCheck.getNoteId());
		checkPatientIdMismatch(noteToCheck);
	}

	private void checkPatientId(Long patientId) throws UnknownPatientIdException {
		if (noteRepository.findByPatientId(patientId).isEmpty())
			throw new UnknownPatientIdException();
	}

	private boolean checkNoteId(String noteId) throws NoteNotFoundException {
		boolean noteExist = noteRepository.findById(noteId).isPresent();
		if (!noteExist)
			throw new NoteNotFoundException();
		return noteExist;
	}

	private void preventResourceIdBreach(Note noteToCheck) throws NoteIdSettingNotAllowedException {
		if (noteToCheck.getNoteId() != null)
			throw new NoteIdSettingNotAllowedException();
	}

	private void checkPatientIdMismatch(Note noteToCheck) throws PatientIdMismatchException, NoteNotFoundException {
		Long expectedPatientId = noteRepository.findById(noteToCheck.getNoteId()).get().getPatientId();
		if (!expectedPatientId.equals(noteToCheck.getPatientId()))
			throw new PatientIdMismatchException();
	}

	
}
