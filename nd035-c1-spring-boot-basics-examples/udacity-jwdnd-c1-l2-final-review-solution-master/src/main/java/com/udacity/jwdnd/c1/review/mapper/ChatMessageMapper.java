package com.udacity.jwdnd.c1.review.mapper;

import com.udacity.jwdnd.c1.review.model.ChatMessage;
import com.udacity.jwdnd.c1.review.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface ChatMessageMapper {
    @Select("SELECT * FROM MESSAGES WHERE username = #{username}")
    ChatMessage getUsername(String username);

    @Insert("INSERT INTO MESSAGES (username, messagetext) " +
            "VALUES(#{username}, #{messagetext})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    int insert(ChatMessage chatMessage);

}
