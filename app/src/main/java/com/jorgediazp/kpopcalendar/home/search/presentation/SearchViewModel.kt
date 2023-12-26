package com.jorgediazp.kpopcalendar.home.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.util.Event
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.DeleteLikedSongsUseCase
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.GetLikedSongsUseCase
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.GetSongsUseCase
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.InsertLikedSongsUseCase
import com.jorgediazp.kpopcalendar.home.common.presentation.model.PresentationModelExtensions.Companion.toPresentationModel
import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationType
import com.jorgediazp.kpopcalendar.home.search.presentation.model.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val getLikedSongsUseCase: GetLikedSongsUseCase,
    private val insertLikedSongsUseCase: InsertLikedSongsUseCase,
    private val deleteLikedSongsUseCase: DeleteLikedSongsUseCase
) : ViewModel() {

    val state = MutableStateFlow<SearchScreenState>(SearchScreenState.ShowNothing)
    val showSnackBarEvent = MutableStateFlow(Event<Int?>(null))

    private var currentQueryId = 0

    fun loadSongListByQuery(query: String) {
        currentQueryId++
        val queryId = currentQueryId

        if (query.length > 1) {
            viewModelScope.launch {
                val result = getSongsUseCase.getSongMapByQuery(query)
                if (queryId >= currentQueryId) {
                    // Queries finished late are not shown
                    if (result is DataResult.Success && result.data != null) {
                        if (result.data.isNotEmpty()) {
                            var likedSongIds = listOf<Int>()
                            val likedResult = getLikedSongsUseCase.getAllLikedSongIds()
                            if (likedResult is DataResult.Success && likedResult.data != null) {
                                likedSongIds = likedResult.data
                            }

                            state.value =
                                SearchScreenState.ShowSongList(
                                    songList = getSongPresentationList(result.data, likedSongIds),
                                    songDomainIndexedMap = result.data
                                )

                        } else {
                            state.value = SearchScreenState.ShowNoResults
                        }
                    } else {
                        state.value = SearchScreenState.ShowError
                    }
                }
            }
        } else {
            state.value = SearchScreenState.ShowNothing
        }
    }

    fun insertOrDeleteLikedSong(songPresentation: SongPresentationModel) {
        viewModelScope.launch {
            if (state.value is SearchScreenState.ShowSongList) {
                (state.value as SearchScreenState.ShowSongList).let { showSongListState ->

                    showSongListState.songDomainIndexedMap[songPresentation.id]?.let { songDomain ->
                        val result: DataResult<Nothing>
                        val snackBarResId: Int
                        if (!songPresentation.liked) {
                            result = insertLikedSongsUseCase.insertLikedSong(songDomain)
                            snackBarResId = R.string.liked_snackbar_song_added
                        } else {
                            result = deleteLikedSongsUseCase.deleteLikedSong(songDomain.id)
                            snackBarResId = R.string.liked_snackbar_song_removed
                        }

                        if (result is DataResult.Success) {
                            songPresentation.liked = !songPresentation.liked
                            state.value =
                                showSongListState.copy(update = showSongListState.update + 1)
                            showSnackBarEvent.value = Event(snackBarResId)
                        }
                    }
                }
            }
        }
    }

    private fun getSongPresentationList(
        domainMap: Map<Int, SongDomainModel>,
        likedSongIds: List<Int>
    ): List<SongPresentationModel> {
        val presentationList = mutableListOf<SongPresentationModel>()
        var isOddRow = true
        domainMap.values.forEach { songDomain ->
            try {
                val presentationSong =
                    songDomain.toPresentationModel(isOddRow, likedSongIds.contains(songDomain.id))
                if (songDomain.ost == null && presentationSong.type == SongPresentationType.RELEASED) {
                    // Do not add songs from ost or unreleased
                    presentationList.add(presentationSong)
                    isOddRow = !isOddRow
                }
            } catch (e: Exception) {
                Firebase.crashlytics.recordException(e)
            }
        }
        return presentationList
    }
}