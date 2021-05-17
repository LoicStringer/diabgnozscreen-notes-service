package com.diabgnozscreennotesservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.diabgnozscreennotesservice.controller.NoteController;
import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.exception.NoteNotFoundException;
import com.diabgnozscreennotesservice.exception.UnknownPatientIdException;
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
	private NoteController noteController;
	
	@Test
	void getPatientHistoryPageTest() throws Exception {
		mockMvc.perform(get("/diabgnoz/patient-history/4"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(4))
				.andExpect(jsonPath("$.content.[0].patientLastName").value("TestEarlyOnset"));	
	}
	
	@Test
	void addNoteTest() throws JsonProcessingException, Exception {
		NoteDto noteToAdd = new NoteDto();
		noteToAdd.setPatientLastName("Jordan");
		noteToAdd.setPatientId(5L);
		
		mockMvc.perform(post("/diabgnoz/patient-history/")
				.content(objectMapper.writeValueAsString(noteToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.patientId").value(5L));
		
		assertEquals("Jordan", 
				noteController.getPatientHistory(5L, PageRequest.of(0, 1)).getBody().getContent().get(0).getPatientLastName());
	}
	
	@Test
	void updateNoteTest() throws JsonProcessingException, Exception {
		NoteDto noteToAdd = new NoteDto();
		noteToAdd.setPatientLastName("Tyson");
		noteToAdd.setPatientId(6L);
		NoteDto addedNote = noteController.addNote(noteToAdd).getBody();
		addedNote.setNoteContent("Update test");
		
		mockMvc.perform(put("/diabgnoz/patient-history")
				.content(objectMapper.writeValueAsString(addedNote))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.noteContent").value("Update test"));
		
		assertEquals("Update test",noteController.getPatientHistory(6L, PageRequest.of(0, 1)).getBody().getContent().get(0).getNoteContent());
	}
	
	@Test
	void isExpectedExceptionAndStatusThrownWhenPatientIsNotFoundTest() throws Exception {
		mockMvc.perform(get("/diabgnoz/patient-history/10"))
		.andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof UnknownPatientIdException));
	}
	
	@Test
	void isExpectedExceptionThrownWhenNoteIsNotFoundTest() throws JsonProcessingException, Exception {
		NoteDto noteToUpdate = new NoteDto();
		noteToUpdate.setNoteId("a1b2");
		
		mockMvc.perform(put("/diabgnoz/patient-history")
				.content(objectMapper.writeValueAsString(noteToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoteNotFoundException));
	}
}
