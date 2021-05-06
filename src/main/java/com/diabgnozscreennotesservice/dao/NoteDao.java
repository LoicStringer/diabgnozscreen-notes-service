package com.diabgnozscreennotesservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.diabgnozscreennotesservice.entity.NoteEntity;
import com.diabgnozscreennotesservice.mapper.NoteMapper;
import com.diabgnozscreennotesservice.model.Note;
import com.diabgnozscreennotesservice.repository.NoteRepository;

@Repository
public class NoteDao {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NoteMapper noteMapper;

	public Page<Note> getPatientHistory(Long patientId, Pageable pageable) {
		Page<NoteEntity> patientHistoryEntity = noteRepository.findByPatientId(patientId, pageable);
		Page<Note> patientHistory = patientHistoryEntity.map(n -> noteMapper.noteEntityToNote(n));
		return patientHistory;
	}

}
