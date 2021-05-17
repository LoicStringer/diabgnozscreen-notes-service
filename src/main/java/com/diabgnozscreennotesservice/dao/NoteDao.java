package com.diabgnozscreennotesservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.diabgnozscreennotesservice.entity.NoteEntity;
import com.diabgnozscreennotesservice.exception.NoteNotFoundException;
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
		checkPatientId(patientId, pageable);
		Page<NoteEntity> patientHistoryEntity = noteRepository.findByPatientId(patientId, pageable);
		Page<Note> patientHistory = patientHistoryEntity.map(n -> noteMapper.noteEntityToNote(n));
		return patientHistory;
	}

	public Note saveNote(Note noteToSave) {
		NoteEntity savedNote = noteRepository.save(noteMapper.noteToNoteEntity(noteToSave));
		return noteMapper.noteEntityToNote(savedNote);
	}
	
	public Note updateNote (Note noteToUpdate) throws NoteNotFoundException {
		checkNoteId(noteToUpdate.getNoteId());
		NoteEntity updatedNote = noteRepository.findById(noteToUpdate.getNoteId()).get();
		updatedNote.setNoteContent(noteToUpdate.getNoteContent());
		updatedNote = noteRepository.save(updatedNote);
		return noteMapper.noteEntityToNote(updatedNote);
	}
	
	private void checkPatientId(Long patientId, Pageable pageable) throws UnknownPatientIdException {
		if(!noteRepository.findByPatientId(patientId, pageable).hasContent()) 
			throw new UnknownPatientIdException();
	}
	
	private void checkNoteId (String noteId) throws NoteNotFoundException {
		if(!noteRepository.findById(noteId).isPresent()) 
			throw new NoteNotFoundException();
		
	}
}
