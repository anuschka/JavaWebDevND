package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credentials> getCredsByUserId(long userId);

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credentials credentials);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} " +
            "WHERE credentialId = #{credentialId}")
    int updateCredential(Credentials credentials);


    @Delete("DELETE  FROM CREDENTIALS WHERE credentialId = #{credentialId} AND userId = #{userId}")
    int deleteCredential(int credentialId, int userId);
}
