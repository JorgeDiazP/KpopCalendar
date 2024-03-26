package com.jorgediazp.kpopcalendar.search.presentation.model

import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.common.presentation.model.SongPresentationModel

sealed class SearchScreenState {

    data object ShowNothing : SearchScreenState()

    data object ShowError : SearchScreenState()

    data object ShowNoResults : SearchScreenState()

    data class ShowSongList(
        val songList: List<SongPresentationModel>,
        val songDomainIndexedMap: Map<Int, SongDomainModel>,
        val update: Int = 0 // This is a patch because I don't understand StateFlow. Stateflow change is triggered when the new object is not equal to the previous one.
    ) : SearchScreenState()
}
