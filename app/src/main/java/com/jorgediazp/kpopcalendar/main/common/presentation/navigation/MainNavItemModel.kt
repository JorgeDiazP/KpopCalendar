package com.jorgediazp.kpopcalendar.main.common.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jorgediazp.kpopcalendar.R

sealed class MainNavItemModel(val title: Int, val icon: ImageVector, val route: String) {

    object Calendar : MainNavItemModel(
        title = R.string.navigation_calendar,
        icon = Icons.Default.CalendarMonth,
        route = "calendar"
    )
    object Search : MainNavItemModel(
        title = R.string.navigation_search,
        icon = Icons.Default.Search,
        route = "search"
    )
    object About : MainNavItemModel(
        title = R.string.navigation_about,
        icon = Icons.Default.Info,
        route = "about"
    )
}
