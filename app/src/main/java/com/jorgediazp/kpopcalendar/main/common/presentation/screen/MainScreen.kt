package com.jorgediazp.kpopcalendar.main.common.presentation.screen

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
import com.jorgediazp.kpopcalendar.main.about.AboutScreen
import com.jorgediazp.kpopcalendar.main.calendar.presentation.CalendarScreen
import com.jorgediazp.kpopcalendar.main.common.presentation.navigation.MainNavItemModel
import com.jorgediazp.kpopcalendar.main.common.presentation.navigation.MainNavigationBar
import com.jorgediazp.kpopcalendar.main.search.SearchScreen

@Composable
fun MainScreen() {
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