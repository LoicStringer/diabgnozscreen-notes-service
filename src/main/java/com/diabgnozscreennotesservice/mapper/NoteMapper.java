package com.diabgnozscreennotesservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.diabgnozscreennotesservice.dto.NoteDto;
import com.diabgnozscreennotesservice.entity.NoteEntity;
import com.diabgnozscreennotesservice.model.Note;

@Mapper(componentModel = "spring")
public interface NoteMapper {

	Note noteEntityToNote (NoteEntity noteEntity);
	NoteEntity noteToNoteEntity (Note note);
	
	NoteDto noteToNoteDto (Note note);
	Note noteDtoToNote (NoteDto noteDto);
	
	List<NoteDto> notesListToNoteDtosList (List<Note> notesList);
	List<Note> noteDtosListToNotesList (List<NoteDto> noteDtosList);
}
