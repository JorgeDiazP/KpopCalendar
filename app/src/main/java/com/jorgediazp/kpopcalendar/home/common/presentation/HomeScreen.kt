package com.jorgediazp.kpopcalendar.home.common.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jorgediazp.kpopcalendar.common.ui.ComposeUtils.Companion.isScrollingUp
import com.jorgediazp.kpopcalendar.common.ui.Screen
import com.jorgediazp.kpopcalendar.home.about.AboutScreen
import com.jorgediazp.kpopcalendar.home.calendar.presentation.CalendarScreen
import com.jorgediazp.kpopcalendar.home.search.SearchScreen

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val listState = rememberLazyListState()

    Screen {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(visible = listState.isScrollingUp()) {
                    MainNavigationBar(navController = navController)
                }
            }
        ) { paddingValues ->
            NavHost(
                navController,
                startDestination = MainNavItemModel.Calendar.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(MainNavItemModel.Calendar.route) {
                    CalendarScreen(listState = listState)
                }
                composable(MainNavItemModel.Search.route) {
                    SearchScreen()
                }
                composable(MainNavItemModel.About.route) {
                    AboutScreen()
                }
            }
        }
    }
}