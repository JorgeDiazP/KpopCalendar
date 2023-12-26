package com.jorgediazp.kpopcalendar.home.liked.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.GetLikedSongsUseCase
import com.jorgediazp.kpopcalendar.home.common.presentation.model.PresentationModelExtensions.Companion.toPresentationModel
import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.home.liked.presentation.model.LikedScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedViewModel @Inject constructor(
    private val getLikedSongsUseCase: GetLikedSongsUseCase
) : ViewModel() {

    var dataLoaded = false
    val state = MutableStateFlow<LikedScreenState>(LikedScreenState.ShowNothing)

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