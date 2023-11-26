package com.jorgediazp.kpopcomebacks.main.common.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jorgediazp.kpopcomebacks.main.about.AboutScreen
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.ui.screen.CalendarScreen
import com.jorgediazp.kpopcomebacks.main.search.SearchScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = MainNavItemModel.Calendar.route) {
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