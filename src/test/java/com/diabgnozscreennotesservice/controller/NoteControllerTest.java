package com.diabgnozscreennotesservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.exception.NoteIdSettingNotAllowedException;
import com.diabgnozscreennotesservice.exception.NoteNotFoundException;
import com.diabgnozscreennotesservice.exception.PatientIdMismatchException;
import com.diabgnozscreennotesservice.exception.UnknownPatientIdException;
import com.diabgnozscreennotesservice.mapper.NoteMapper;
import com.diabgnozscreennotesservice.model.Note;
import com.diabgnozscreennotesservice.service.NoteService;

@DisplayName("Note controller unit tests")
@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

	@Mock
	private NoteMapper noteMapper;

	@Mock
	private NoteService noteService;

	@InjectMocks
	private NoteController noteController;

	private static NoteDto testedNoteDto;
	private static NoteDto testedNoteDtoTwo;
	private static List<NoteDto> testedNoteDtosList;
	private static Page<NoteDto> testedNoteDtosPage;

	private static Note testedNote;
	private static Note testedNoteTwo;
	private static List<Note> testedNotesList;
	private static Page<Note> testedNotesPage;

	private static Pageable testedPageable;

	@BeforeAll
	static void setUpForTests() {
		initTestDtoBeans();
		initTestBeans();
		testedNotesPage = new PageImpl<>(testedNotesList);
		testedNoteDtosPage = new PageImpl<>(testedNoteDtosList);
		testedPageable = PageRequest.of(0, 1);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getPatientHistoryPageTest() throws UnknownPatientIdException {
			when(noteService.getPatientHistory(1L, testedPageable)).thenReturn(testedNotesPage);
			when(noteMapper.noteToNoteDto(testedNote)).thenReturn(testedNoteDto);
			when(noteMapper.noteToNoteDto(testedNoteTwo)).thenReturn(testedNoteDtoTwo);
			assertEquals(ResponseEntity.ok(testedNoteDtosPage), noteController.getPatientHistory(1L, testedPageable));
		}

		@Test
		void addNoteTest() throws NoteIdSettingNotAllowedException {
			when(noteMapper.noteDtoToNote(testedNoteDto)).thenReturn(testedNote);
			when(noteService.saveNote(testedNote)).thenReturn(testedNote);
			when(noteMapper.noteToNoteDto(testedNote)).thenReturn(testedNoteDto);
			assertEquals(ResponseEntity.ok(testedNoteDto), noteController.addNote(testedNoteDto));
		}

		@Test
		void updateNoteTest() throws NoteNotFoundException, PatientIdMismatchException {
			when(noteMapper.noteDtoToNote(testedNoteDto)).thenReturn(testedNote);
			when(noteService.updateNote(testedNote)).thenReturn(testedNote);
			when(noteMapper.noteToNoteDto(testedNote)).thenReturn(testedNoteDto);
			assertEquals(ResponseEntity.ok(testedNoteDto), noteController.updateNote(testedNoteDto));
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectetdExceptionThrownWhenPatientIdNotRegisteredTest() throws UnknownPatientIdException {
			when(noteService.getPatientHistory(any(Long.class), any(Pageable.class))).thenThrow(UnknownPatientIdException.class);
			assertThrows(UnknownPatientIdException.class, ()->noteController.getPatientHistory(10L, testedPageable));
		}
		
		@Test
		void isExpectedExceptionThrownWhenNoteNotFoundTest() throws NoteNotFoundException, PatientIdMismatchException {
			when(noteMapper.noteDtoToNote(testedNoteDto)).thenReturn(testedNote);
			when(noteService.updateNote(testedNote)).thenThrow(NoteNotFoundException.class);
			assertThrows(NoteNotFoundException.class,()-> noteController.updateNote(testedNoteDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenSettingIdBeforeSaveTest() throws NoteIdSettingNotAllowedException {
			when(noteMapper.noteDtoToNote(testedNoteDto)).thenReturn(testedNote);
			when(noteService.saveNote(testedNote)).thenThrow(NoteIdSettingNotAllowedException.class);
			assertThrows(NoteIdSettingNotAllowedException.class,()-> noteController.addNote(testedNoteDto));
		}	
		
		@Test
		void isExpectedExceptionThrownWhenPatientIdTest() throws NoteNotFoundException, PatientIdMismatchException {
			when(noteMapper.noteDtoToNote(testedNoteDto)).thenReturn(testedNote);
			when(noteService.updateNote(testedNote)).thenThrow(PatientIdMismatchException.class);
			assertThrows(PatientIdMismatchException.class,()-> noteController.updateNote(testedNoteDto));
		}
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
