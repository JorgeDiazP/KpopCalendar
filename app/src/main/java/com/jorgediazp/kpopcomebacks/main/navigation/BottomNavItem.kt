package com.jorgediazp.kpopcomebacks.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jorgediazp.kpopcomebacks.R

sealed class BottomNavItem(val title: Int, val icon: ImageVector, val screenRoute: String) {

    object Calendar : BottomNavItem(
        title = R.string.navigation_calendar,
        icon = Icons.Default.CalendarMonth,
        screenRoute = "calendar"
    )
    object Search : BottomNavItem(
        title = R.string.navigation_search,
        icon = Icons.Default.Search,
        screenRoute = "search"
    )
    object About : BottomNavItem(
        title = R.string.navigation_about,
        icon = Icons.Default.Info,
        screenRoute = "about"
    )
}
