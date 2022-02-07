package com.ercanpinar.exampleapplication.data.local.user


import androidx.room.*

@Dao
interface UserDao {

    /**
     * Get all users
     * @return List of User
     */
    @Suppress("unused")
    @Transaction
    @Query("SELECT * FROM user_table ORDER BY id")
    suspend fun getUserData(): List<UserEntity>

    /**
     * Get User item by UserId
     * @return User
     */
    @Suppress("unused")
    @Transaction
    @Query("SELECT * FROM user_table WHERE id =:userId")
    suspend fun getUserById(userId: Int): UserEntity

    /**
     *  Get all user size
     * @return List of User Count
     */
    @Suppress("unused")
    @Transaction
    @Query("SELECT Count(*) FROM user_table")
    suspend fun getUserSize(): Int

    /**
     * Insert all Users
     * @param userList inserted into user database
     */
    @Suppress("unused")
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUserItem(userList: List<UserEntity>)

    /**
     * Delete all User data
     */
    @Suppress("unused")
    @Query("DELETE FROM user_table")
    suspend fun deleteAllUserData()
}