package com.jorgediazp.kpopcalendar.home.common.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jorgediazp.kpopcalendar.R

sealed class MainNavItemModel(val title: Int, val icon: ImageVector, val route: String) {

    data object Calendar : MainNavItemModel(
        title = R.string.navigation_calendar,
        icon = Icons.Outlined.CalendarMonth,
        route = "calendar"
    )

    data object Search : MainNavItemModel(
        title = R.string.navigation_search,
        icon = Icons.Outlined.Search,
        route = "search"
    )

    data object Liked : MainNavItemModel(
        title = R.string.navigation_liked,
        icon = Icons.Outlined.FavoriteBorder,
        route = "liked"
    )

    data object About : MainNavItemModel(
        title = R.string.navigation_about,
        icon = Icons.Outlined.Info,
        route = "about"
    )
}
