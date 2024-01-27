package com.jorgediazp.kpopcalendar.home.liked.presentation.model

import com.jorgediazp.kpopcalendar.common.presentation.model.SongPresentationModel

sealed class LikedScreenState {

    data object ShowNothing : LikedScreenState()

    data object ShowError : LikedScreenState()

    data object ShowEmpty : LikedScreenState()

    data class ShowSongList(
        val songList: List<SongPresentationModel>,
        val update: Int = 0 // This is a patch because I don't understand StateFlow. Stateflow change is triggered when the new object is not equal to the previous one
    ) : LikedScreenState()
}