package com.jorgediazp.kpopcalendar.main.common.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jorgediazp.kpopcalendar.main.about.AboutScreen
import com.jorgediazp.kpopcalendar.main.calendar.presentation.CalendarScreen
import com.jorgediazp.kpopcalendar.main.search.SearchScreen

@Composable
fun MainNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        startDestination = MainNavItemModel.Calendar.route,
        modifier = modifier
    ) {
        composable(MainNavItemModel.Calendar.route) {
            CalendarScreen()
        }
        composable(MainNavItemModel.Search.route) {
            SearchScreen()
        }
        composable(MainNavItemModel.About.route) {
            AboutScreen()
        }
    }
}