package com.jorgediazp.kpopcalendar.home.search.presentation.model

import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationModel

sealed class SearchScreenState {

    data object ShowNothing : SearchScreenState()

    data object ShowError : SearchScreenState()

    data object ShowNoResults : SearchScreenState()

    data class ShowSongList(val songList: List<SongPresentationModel>) : SearchScreenState()
}
