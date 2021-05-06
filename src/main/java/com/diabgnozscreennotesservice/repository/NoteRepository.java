package com.diabgnozscreennotesservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.diabgnozscreennotesservice.entity.NoteEntity;
import com.diabgnozscreennotesservice.model.Note;

public interface NoteRepository extends MongoRepository<NoteEntity, Long>{
	
	Page<NoteEntity> findByPatientId(Long patientId, Pageable pageable);


	
}
