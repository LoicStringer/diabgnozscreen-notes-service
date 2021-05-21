package com.diabgnozscreennotesservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.mapper.NoteMapper;
import com.diabgnozscreennotesservice.model.Note;
import com.diabgnozscreennotesservice.service.NoteService;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

	@Mock
	private NoteMapper noteMapper;
	
	@Mock
	private NoteService noteService;
	
	@InjectMocks
	private ReportController reportController;
	
	private static NoteDto testedNoteDto;
	private static NoteDto testedNoteDtoTwo;
	private static List<NoteDto> testedNoteDtosList;

	private static Note testedNote;
	private static Note testedNoteTwo;
	private static List<Note> testedNotesList;

	@BeforeAll
	static void setUpForTests() {
		initTestDtoBeans();
		initTestBeans();
	}

	@Test
	void getPatientHistoryAsListTest() {
		when(noteService.getPatientHistoryAsNotesList(1L)).thenReturn(testedNotesList);
		when(noteMapper.notesListToNoteDtosList(testedNotesList)).thenReturn(testedNoteDtosList);
		assertEquals(ResponseEntity.ok(testedNoteDtosList), reportController.getPatientHistoryForReport(1L));
	}

	private static void initTestDtoBeans() {
		testedNoteDto = new NoteDto();
		testedNoteDtoTwo = new NoteDto();
		testedNoteDtosList = new ArrayList<NoteDto>();
		testedNoteDto.setPatientId(1L);
		testedNoteDtoTwo.setPatientId(1L);
		testedNoteDtosList.add(testedNoteDto);
		testedNoteDtosList.add(testedNoteDtoTwo);
	}

	private static void initTestBeans() {
		testedNote = new Note();
		testedNoteTwo = new Note();
		testedNotesList = new ArrayList<Note>();
		testedNote.setPatientId(1L);
		testedNoteTwo.setPatientId(1L);
		testedNotesList.add(testedNote);
		testedNotesList.add(testedNoteTwo);
	}
}
