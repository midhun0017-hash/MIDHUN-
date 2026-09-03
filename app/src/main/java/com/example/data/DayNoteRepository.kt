package com.example.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DayNoteRepository(private val dao: DayNoteDao) {

    val allNotes: Flow<List<DayNote>> = dao.getAllNotes()

    fun getNotesForDate(date: String): Flow<List<DayNote>> = dao.getNotesByDate(date)

    fun searchNotes(query: String): Flow<List<DayNote>> = dao.searchNotes(query)

    suspend fun addNote(text: String): Long {
        val now = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val dateStr = dateFormat.format(now)
        val timeStr = timeFormat.format(now)

        val note = DayNote(
            date = dateStr,
            time = timeStr,
            note = text.trim(),
            timestamp = System.currentTimeMillis()
        )
        return dao.insertNote(note)
    }

    suspend fun updateNote(note: DayNote) {
        dao.updateNote(note)
    }

    suspend fun deleteNote(note: DayNote) {
        dao.deleteNote(note)
    }

    suspend fun deleteNoteById(id: Long) {
        dao.deleteNoteById(id)
    }
}
