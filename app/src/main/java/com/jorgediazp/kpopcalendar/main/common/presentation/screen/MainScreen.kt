package com.jorgediazp.kpopcalendar.main.common.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jorgediazp.kpopcalendar.common.ui.Screen
import com.jorgediazp.kpopcalendar.main.common.presentation.navigation.MainNavGraph
import com.jorgediazp.kpopcalendar.main.common.presentation.navigation.MainNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Screen {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { MainNavigationBar(navController = navController) }
        ) { paddingValues ->
            MainNavGraph(navController = navController, modifier = Modifier.padding(paddingValues))
        }
    }
}