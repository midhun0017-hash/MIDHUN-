package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayNoteDao {

    @Query("SELECT * FROM day_notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<DayNote>>

    @Query("SELECT * FROM day_notes WHERE date = :date ORDER BY timestamp DESC")
    fun getNotesByDate(date: String): Flow<List<DayNote>>

    @Query("SELECT * FROM day_notes WHERE note LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchNotes(query: String): Flow<List<DayNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: DayNote): Long

    @Update
    suspend fun updateNote(note: DayNote)

    @Delete
    suspend fun deleteNote(note: DayNote)

    @Query("DELETE FROM day_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)
}
