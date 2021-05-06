package com.diabgnozscreennotesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.diabgnozscreennotesservice.dao.NoteDao;
import com.diabgnozscreennotesservice.model.Note;

@Service
public class NoteService {

	@Autowired
	private NoteDao noteDao;
	
	public Page<Note> getPatientHistory (Long patientId, Pageable pageable){
		return noteDao.getPatientHistory(patientId,pageable);
	}
	
}
