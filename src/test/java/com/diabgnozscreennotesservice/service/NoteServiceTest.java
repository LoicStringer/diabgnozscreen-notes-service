package com.diabgnozscreennotesservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.diabgnozscreennotesservice.dao.NoteDao;
import com.diabgnozscreennotesservice.exception.NoteIdSettingNotAllowedException;
import com.diabgnozscreennotesservice.exception.NoteNotFoundException;
import com.diabgnozscreennotesservice.exception.PatientIdMismatchException;
import com.diabgnozscreennotesservice.model.Note;

@DisplayName("NoteService unit tests")
@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

	@InjectMocks
	private NoteService noteService;

	@Mock
	private NoteDao noteDao;

	private static Note testedNote;
	private static Note testedNoteTwo;
	private static List<Note> testedNotesList;
	private static Page<Note> testedNotesPage;
	private static Pageable testedPageable;

	@BeforeAll
	static void setUpForTests() {
		initTestBeans();
		setUpTestBeans();
		testedNotesList.add(testedNote);
		testedNotesList.add(testedNoteTwo);
		testedNotesPage = new PageImpl<>(testedNotesList);
		testedPageable = PageRequest.of(0, 1);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getPatientHistoryPageTest() {
			when(noteDao.getPatientHistory(1L, testedPageable)).thenReturn(testedNotesPage);
			assertEquals(testedNotesPage, noteService.getPatientHistory(1L, testedPageable));
		}

		@Test
		void getPatientHistoryAsListTest() {
			when(noteDao.getPatientHistoryAsNotesList(1L)).thenReturn(testedNotesList);
			assertEquals(testedNotesList, noteService.getPatientHistoryAsNotesList(1L));
		}

		@Test
		void saveNoteTest() throws NoteIdSettingNotAllowedException {
			when(noteDao.saveNote(testedNote)).thenReturn(testedNote);
			assertEquals(1L, noteService.saveNote(testedNote).getPatientId());
		}

		@Test
		void updateNoteTest() throws NoteNotFoundException, PatientIdMismatchException {
			when(noteDao.updateNote(testedNote)).thenReturn(testedNote);
			assertEquals(1L, noteService.updateNote(testedNote).getPatientId());
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenNoteNotFoundTest() throws NoteNotFoundException, PatientIdMismatchException {
			when(noteDao.updateNote(testedNote)).thenThrow(NoteNotFoundException.class);
			assertThrows(NoteNotFoundException.class, () -> noteService.updateNote(testedNote));
		}

		@Test
		void isExpectedExceptionThrownWhenSettingAnIdBeforeSaveTest() throws NoteIdSettingNotAllowedException {
			when(noteDao.saveNote(testedNote)).thenThrow(NoteIdSettingNotAllowedException.class);
			assertThrows(NoteIdSettingNotAllowedException.class, () -> noteService.saveNote(testedNote));
		}

		@Test
		void isExpectedExceptionThrownWhenPatientIdMismatchBeforeUpdateTest()
				throws NoteNotFoundException, PatientIdMismatchException {
			when(noteDao.updateNote(testedNote)).thenThrow(PatientIdMismatchException.class);
			assertThrows(PatientIdMismatchException.class, () -> noteService.updateNote(testedNote));
		}

	}

	private static void setUpTestBeans() {
		testedNote.setPatientId(1L);
		testedNoteTwo.setPatientId(1L);
	}

	private static void initTestBeans() {
		testedNote = new Note();
		testedNoteTwo = new Note();
		testedNotesList = new ArrayList<Note>();
	}

}
