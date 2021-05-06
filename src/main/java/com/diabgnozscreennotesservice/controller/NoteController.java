package com.diabgnozscreennotesservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.entity.NoteEntity;
import com.diabgnozscreennotesservice.mapper.NoteMapper;
import com.diabgnozscreennotesservice.model.Note;
import com.diabgnozscreennotesservice.repository.NoteRepository;
import com.diabgnozscreennotesservice.service.NoteService;

@RestController
@RequestMapping("/diabgnoz/patient-history")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private NoteRepository noteRepository;

	@GetMapping("/{patientId}")
	public ResponseEntity<Page<NoteDto>> getPatientHistory(@PathVariable Long patientId, Pageable pageable) {
		Page<Note> patientHistoryModel = noteService.getPatientHistory(patientId, pageable);
		Page<NoteDto> patientHistory = patientHistoryModel.map(n -> noteMapper.noteToNoteDto(n));
		return ResponseEntity.ok(patientHistory);
	}

	

}
