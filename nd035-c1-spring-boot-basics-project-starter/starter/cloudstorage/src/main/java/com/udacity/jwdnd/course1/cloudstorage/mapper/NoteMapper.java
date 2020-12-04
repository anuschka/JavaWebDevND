package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    @Results({
            @Result(column = "notetitle", property = "title"),
            @Result(column = "notedescription", property = "description")
    })
    List<Note> findNotes(final Integer userId);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) " +
            "VALUES(#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int createNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} " +
            "WHERE noteid = #{noteId}")
    void updateNote(Note note);
}