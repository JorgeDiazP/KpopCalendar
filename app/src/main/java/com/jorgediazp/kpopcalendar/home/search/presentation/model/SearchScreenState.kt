package com.jorgediazp.kpopcalendar.home.search.presentation.model

sealed class SearchScreenState {

    data object ShowNothing : SearchScreenState()

    data object ShowError : SearchScreenState()

    data object ShowNoResults : SearchScreenState()
}
