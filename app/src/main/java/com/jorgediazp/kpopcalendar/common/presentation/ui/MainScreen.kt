package com.jorgediazp.kpopcalendar.common.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jorgediazp.kpopcalendar.common.presentation.ui.ComposeUtils.Companion.isScrollingUp
import com.jorgediazp.kpopcalendar.about.AboutScreen
import com.jorgediazp.kpopcalendar.calendar.presentation.CalendarScreen
import com.jorgediazp.kpopcalendar.common.presentation.model.MainNavItemModel
import com.jorgediazp.kpopcalendar.common.presentation.ui.component.HomeNavigationBar
import com.jorgediazp.kpopcalendar.common.presentation.ui.component.KpopCalendarSnackBar
import com.jorgediazp.kpopcalendar.liked.presentation.LikedScreen
import com.jorgediazp.kpopcalendar.search.presentation.SearchScreen
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val listState = rememberLazyListState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Screen {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(visible = listState.isScrollingUp()) {
                    HomeNavigationBar(navController = navController)
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState) { snackBarData ->
                    KpopCalendarSnackBar(message = snackBarData.visuals.message)
                }
            }
        ) { paddingValues ->
            NavHost(
                navController,
                startDestination = MainNavItemModel.Calendar.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(MainNavItemModel.Calendar.route) {
                    CalendarScreen(listState = listState, showSnackBar = { text ->
                        scope.launch {
                            snackBarHostState.showSnackbar(text)
                        }
                    })
                }
                composable(MainNavItemModel.Search.route) {
                    SearchScreen(showSnackBar = { text ->
                        scope.launch {
                            snackBarHostState.showSnackbar(text)
                        }
                    })
                }
                composable(MainNavItemModel.Liked.route) {
                    LikedScreen(showSnackBar = { text ->
                        scope.launch {
                            snackBarHostState.showSnackbar(text)
                        }
                    })
                }
                composable(MainNavItemModel.About.route) {
                    AboutScreen()
                }
            }
        }
    }
}