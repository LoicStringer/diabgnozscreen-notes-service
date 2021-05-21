package com.diabgnozscreennotesservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.diabgnozscreennotesservice.entity.NoteEntity;


public interface NoteRepository extends MongoRepository<NoteEntity, String>{
	
	Page<NoteEntity> findByPatientId(Long patientId, Pageable pageable);
	List<NoteEntity> findByPatientId(Long patientId);

	
}
