package com.jorgediazp.kpopcalendar.home.liked.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.util.Event
import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.common.domain.usecase.DeleteLikedSongsUseCase
import com.jorgediazp.kpopcalendar.common.domain.usecase.GetLikedSongsUseCase
import com.jorgediazp.kpopcalendar.common.presentation.model.PresentationModelExtensions.Companion.toPresentationModel
import com.jorgediazp.kpopcalendar.common.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.home.liked.presentation.model.LikedScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedViewModel @Inject constructor(
    private val getLikedSongsUseCase: GetLikedSongsUseCase,
    private val deleteLikedSongsUseCase: DeleteLikedSongsUseCase
) : ViewModel() {

    var dataLoaded = false
    val state = MutableStateFlow<LikedScreenState>(LikedScreenState.ShowNothing)
    val showSnackBarEvent = MutableStateFlow(Event<Int?>(null))

    fun loadData() {
        viewModelScope.launch {
            getLikedSongsUseCase.getAllLikedSongs()
                .catch { throwable ->
                    Firebase.crashlytics.recordException(throwable)
                    state.value = LikedScreenState.ShowError

                }.collect { songDomainList ->
                    if (songDomainList.isNotEmpty()) {
                        state.value = LikedScreenState.ShowSongList(
                            songList = getSongPresentationList(songDomainList)
                        )

                    } else {
                        state.value = LikedScreenState.ShowEmpty
                    }
                }
        }
    }

    fun deleteLikedSong(song: SongPresentationModel) {
        viewModelScope.launch {
            val result = deleteLikedSongsUseCase.deleteLikedSong(song.id)
            if (result is DataResult.Success) {
                showSnackBarEvent.value = Event(R.string.liked_snackbar_song_removed)
            }
        }
    }

    private fun getSongPresentationList(domainList: List<SongDomainModel>): List<SongPresentationModel> {
        val presentationList = mutableListOf<SongPresentationModel>()
        var isOddRow = true
        domainList.forEach { songDomain ->
            try {
                presentationList.add(songDomain.toPresentationModel(isOddRow, true))
                isOddRow = !isOddRow
            } catch (e: Exception) {
                Firebase.crashlytics.recordException(e)
            }
        }
        return presentationList
    }
}