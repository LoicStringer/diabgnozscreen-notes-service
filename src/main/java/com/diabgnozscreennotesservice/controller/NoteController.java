package com.diabgnozscreennotesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.exception.NoteIdSettingNotAllowedException;
import com.diabgnozscreennotesservice.exception.NoteNotFoundException;
import com.diabgnozscreennotesservice.exception.PatientIdMismatchException;
import com.diabgnozscreennotesservice.exception.UnknownPatientIdException;
import com.diabgnozscreennotesservice.mapper.NoteMapper;
import com.diabgnozscreennotesservice.model.Note;
import com.diabgnozscreennotesservice.service.NoteService;

@RestController
@RequestMapping("/diabgnoz/patient-history")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteMapper noteMapper;

	@GetMapping("/{patientId}")
	public ResponseEntity<Page<NoteDto>> getPatientHistory(@PathVariable Long patientId, Pageable pageable) throws UnknownPatientIdException {
		Page<Note> patientHistoryModel = noteService.getPatientHistory(patientId, pageable);
		Page<NoteDto> patientHistory = patientHistoryModel.map(n -> noteMapper.noteToNoteDto(n));
		return ResponseEntity.ok(patientHistory);
	}

	@PostMapping("")
	public ResponseEntity<NoteDto> addNote (@RequestBody NoteDto noteDtoToSave) throws NoteIdSettingNotAllowedException{
		Note savedNote = noteService.saveNote(noteMapper.noteDtoToNote(noteDtoToSave));
		return ResponseEntity.ok(noteMapper.noteToNoteDto(savedNote));
	}
	
	@PutMapping("")
	public ResponseEntity<NoteDto> updateNote (@RequestBody NoteDto noteDtoToUpdate) throws NoteNotFoundException, PatientIdMismatchException{
		Note updatedNote = noteService.updateNote(noteMapper.noteDtoToNote(noteDtoToUpdate));
		return ResponseEntity.ok(noteMapper.noteToNoteDto(updatedNote));
	}

}
