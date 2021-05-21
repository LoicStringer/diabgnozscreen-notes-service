package com.diabgnozscreennotesservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.mapper.NoteMapper;
import com.diabgnozscreennotesservice.service.NoteService;

@RestController
@RequestMapping("diabgnoz/report/")
public class ReportController {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoteMapper noteMapper;

	@GetMapping("/{patientId}")
	public ResponseEntity<List<NoteDto>> getPatientHistoryForReport(@PathVariable Long patientId){
		return ResponseEntity.ok(noteMapper.notesListToNoteDtosList(noteService.getPatientHistoryAsNotesList(patientId)));
	}
	
}
