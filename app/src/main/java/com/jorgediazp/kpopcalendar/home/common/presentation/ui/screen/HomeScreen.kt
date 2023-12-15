package com.jorgediazp.kpopcalendar.home.common.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jorgediazp.kpopcalendar.common.presentation.ui.ComposeUtils.Companion.isScrollingUp
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen
import com.jorgediazp.kpopcalendar.home.about.AboutScreen
import com.jorgediazp.kpopcalendar.home.calendar.presentation.CalendarScreen
import com.jorgediazp.kpopcalendar.home.common.presentation.model.MainNavItemModel
import com.jorgediazp.kpopcalendar.home.common.presentation.ui.component.HomeNavigationBar
import com.jorgediazp.kpopcalendar.home.liked.presentation.LikedScreen
import com.jorgediazp.kpopcalendar.home.search.presentation.SearchScreen

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val listState = rememberLazyListState()

    Screen {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(visible = listState.isScrollingUp()) {
                    HomeNavigationBar(navController = navController)
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
                composable(MainNavItemModel.Liked.route) {
                    LikedScreen()
                }
                composable(MainNavItemModel.About.route) {
                    AboutScreen()
                }
            }
        }
    }
}