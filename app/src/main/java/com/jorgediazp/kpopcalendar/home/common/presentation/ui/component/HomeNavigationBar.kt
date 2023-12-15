package com.jorgediazp.kpopcalendar.home.common.presentation.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jorgediazp.kpopcalendar.common.presentation.theme.LocalCustomColorsPalette
import com.jorgediazp.kpopcalendar.home.common.presentation.model.MainNavItemModel

@Composable
fun HomeNavigationBar(navController: NavHostController) {
    val screens = listOf(
        MainNavItemModel.Calendar,
        MainNavItemModel.Search,
        MainNavItemModel.Liked,
        MainNavItemModel.About
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                label = {
                    Text(
                        text = stringResource(screen.title),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = LocalCustomColorsPalette.current.surfaceVariantSecondary
                )
            )
        }
    }
}