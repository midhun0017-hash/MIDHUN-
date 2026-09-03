package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.DayNote
import com.example.data.DayNoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.example.ui.theme.AppThemeMode
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class NoteTab(val label: String) {
    TODAY("Today"),
    ALL("All Notes"),
    SEARCH("Search")
}

class DayNotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DayNoteRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = DayNoteRepository(db.dayNoteDao())
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val todayDateString: String = dateFormat.format(Date())

    private val displayDateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
    val todayFormattedDisplay: String = displayDateFormat.format(Date())

    private val shortDateFormat = SimpleDateFormat("MMMM d", Locale.getDefault())
    val todayShortDateDisplay: String = shortDateFormat.format(Date())

    private val _currentTab = MutableStateFlow(NoteTab.TODAY)
    val currentTab: StateFlow<NoteTab> = _currentTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val todayNotes: StateFlow<List<DayNote>> = repository.getNotesForDate(todayDateString)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allNotes: StateFlow<List<DayNote>> = repository.allNotes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<DayNote>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.allNotes
            } else {
                repository.searchNotes(query.trim())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _appTheme = MutableStateFlow(AppThemeMode.MIDNIGHT)
    val appTheme: StateFlow<AppThemeMode> = _appTheme.asStateFlow()

    private val _isThemePickerOpen = MutableStateFlow(false)
    val isThemePickerOpen: StateFlow<Boolean> = _isThemePickerOpen.asStateFlow()

    fun setTheme(theme: AppThemeMode) {
        _appTheme.value = theme
    }

    fun openThemePicker() {
        _isThemePickerOpen.value = true
    }

    fun closeThemePicker() {
        _isThemePickerOpen.value = false
    }

    private val _isAddDialogOpen = MutableStateFlow(false)
    val isAddDialogOpen: StateFlow<Boolean> = _isAddDialogOpen.asStateFlow()

    private val _editingNote = MutableStateFlow<DayNote?>(null)
    val editingNote: StateFlow<DayNote?> = _editingNote.asStateFlow()

    private val _notePendingDelete = MutableStateFlow<DayNote?>(null)
    val notePendingDelete: StateFlow<DayNote?> = _notePendingDelete.asStateFlow()

    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()

    fun selectTab(tab: NoteTab) {
        _currentTab.value = tab
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun openAddDialog() {
        _editingNote.value = null
        _isAddDialogOpen.value = true
    }

    fun openEditDialog(note: DayNote) {
        _editingNote.value = note
        _isAddDialogOpen.value = true
    }

    fun closeDialog() {
        _isAddDialogOpen.value = false
        _editingNote.value = null
    }

    fun saveNote(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) {
            return
        }

        val existing = _editingNote.value
        viewModelScope.launch {
            if (existing != null) {
                val updated = existing.copy(note = trimmed)
                repository.updateNote(updated)
                _snackbarEvent.emit("✓ Note updated!")
            } else {
                repository.addNote(trimmed)
                _snackbarEvent.emit("✓ Note saved!")
            }
            closeDialog()
        }
    }

    fun requestDeleteNote(note: DayNote) {
        _notePendingDelete.value = note
    }

    fun dismissDeleteDialog() {
        _notePendingDelete.value = null
    }

    fun confirmDeleteNote() {
        val note = _notePendingDelete.value ?: return
        viewModelScope.launch {
            repository.deleteNote(note)
            _notePendingDelete.value = null
            _snackbarEvent.emit("Note deleted")
        }
    }

    companion object {
        fun provideFactory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DayNotesViewModel(application) as T
                }
            }
    }
}
