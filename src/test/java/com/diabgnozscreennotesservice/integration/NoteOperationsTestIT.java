package com.diabgnozscreennotesservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.entity.NoteEntity;
import com.diabgnozscreennotesservice.exception.NoteIdSettingNotAllowedException;
import com.diabgnozscreennotesservice.exception.NoteNotFoundException;
import com.diabgnozscreennotesservice.exception.PatientIdMismatchException;

import com.diabgnozscreennotesservice.mapper.NoteMapper;
import com.diabgnozscreennotesservice.repository.NoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class NoteOperationsTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private NoteRepository noteRepository;
	
	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getPatientHistoryPageTest() throws Exception {
			mockMvc.perform(get("/diabgnoz/patient-history/4")).andExpect(status().isOk())
					.andExpect(jsonPath("$.content.length()").value(4))
					.andExpect(jsonPath("$.content.[0].patientLastName").value("TestEarlyOnset"));
		}

		@Test
		void addNoteTest() throws JsonProcessingException, Exception {
			NoteDto noteToAdd = new NoteDto();
			noteToAdd.setPatientLastName("Jordan");
			noteToAdd.setPatientId(5L);

			mockMvc.perform(post("/diabgnoz/patient-history/").content(objectMapper.writeValueAsString(noteToAdd))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
					.andExpect(jsonPath("$.patientId").value(5L));

			assertEquals("Jordan", noteRepository.findByPatientId(5L).get(0).getPatientLastName());
			
			NoteEntity noteToDelete = noteRepository.findByPatientId(5L).get(0);
			noteRepository.delete(noteToDelete);
		}

		@Test
		void updateNoteTest() throws JsonProcessingException, Exception {
			NoteDto noteToUpdate = 
					noteMapper.noteToNoteDto(noteMapper.noteEntityToNote(noteRepository.findByPatientId(4L).get(0)));
			String noteContent = noteToUpdate.getNoteContent();
			
			noteToUpdate.setNoteContent("Update test");
			
			mockMvc.perform(put("/diabgnoz/patient-history").content(objectMapper.writeValueAsString(noteToUpdate))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.noteContent").value("Update test"));

			assertEquals("Update test", noteRepository.findById(noteToUpdate.getNoteId()).get().getNoteContent());
			
			NoteEntity reloadedNote = noteRepository.findById(noteToUpdate.getNoteId()).get() ;
			reloadedNote.setNoteContent(noteContent);
			noteRepository.save(reloadedNote);
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenNoteIsNotFoundTest() throws JsonProcessingException, Exception {
			NoteDto noteToUpdate = 
					noteMapper.noteToNoteDto(noteMapper.noteEntityToNote(noteRepository.findByPatientId(4L).get(0)));
			noteToUpdate.setNoteId("a1b2");

			mockMvc.perform(put("/diabgnoz/patient-history").content(objectMapper.writeValueAsString(noteToUpdate))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoteNotFoundException));
		}
		
		@Test
		void isExpectedExceptionThrownWhenSettingIdBeforeSaveTest() throws JsonProcessingException, Exception {
			NoteDto noteToSave = new NoteDto();
			noteToSave.setNoteId("a1b2");
			
			mockMvc.perform(post("/diabgnoz/patient-history").content(objectMapper.writeValueAsString(noteToSave))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden())
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoteIdSettingNotAllowedException));
		}
		
		@Test
		void isExpectedExceptionThrownWhenPatientIdMismatchBeforeUpdateTest() throws JsonProcessingException, Exception {
			NoteDto noteToUpdate = 
					noteMapper.noteToNoteDto(noteMapper.noteEntityToNote(noteRepository.findByPatientId(4L).get(0)));
			noteToUpdate.setPatientId(7L);
			
			mockMvc.perform(put("/diabgnoz/patient-history").content(objectMapper.writeValueAsString(noteToUpdate))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof PatientIdMismatchException));

		}
	}
}
