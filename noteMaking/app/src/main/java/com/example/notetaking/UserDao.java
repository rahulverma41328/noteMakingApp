package com.example.notetaking;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertRecord(User users);

    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query("SELECT EXISTS(SELECT * FROM User WHERE uid) = :userId")
    Boolean is_exist(int userId);

    @Query("SELECT * FROM User")
    List<User> getAllData();

    @Query("DELETE FROM User WHERE Uid= :id")
    void deleteById(int id);



    @Query("UPDATE User SET first_name= :firstName, last_name= :lastName WHERE uid = :id")
    void updateById(String firstName,String lastName,int id);

    @Query("SELECT uid FROM User WHERE first_name = :firstName AND last_name = :lastName")
    int getUidByNames(String firstName, String lastName);

    @Update
    void updateUser(User user);
}
