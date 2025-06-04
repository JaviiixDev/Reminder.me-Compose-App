package com.javidev.recuerdame

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.javidev.recuerdame.navigation.AppNavGraph
import com.javidev.recuerdame.navigation.Screen

/**
 * define la topbar la bottom bar y decide en que paginas se mostrara la fecha de back
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // mostrar top bar y nav bar solo si no se esta en Login
    val showTopBar = currentRoute != Screen.Login.route
    val showBottomBar = currentRoute != Screen.Login.route

    //define el texto a mostrar en la topbar
    val customTitle = when (currentRoute) {
        "home" -> stringResource(R.string.hola_usuario)
        "select_note_type" -> ""
        "create_note" -> stringResource(R.string.mi_nueva_nota)
        "search_note" -> stringResource(R.string.buscar_nota)
        else -> stringResource(R.string.notas)
    }

    Scaffold(
        topBar = {
            if (showTopBar) {
                //paginas en las que si aparecera la flechita de retroseso
                val showBackButton = currentRoute?.startsWith("note_detail") == true || currentRoute == "create_note"
                TopBar(
                    title = customTitle,
                    showBackButton = showBackButton,
                    onBackClick = { navController.popBackStack() }//te regresa a la pantalla anterior
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)

    NavigationBar(
        containerColor = Color(0xFF2C2C2C), // Fondo negro
        contentColor = Color.White    // Íconos y texto blancos
    ){
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.nav_home)) },
            label = { Text(stringResource(R.string.nav_home)) },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route)
                    launchSingleTop = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray,
            indicatorColor = Color.DarkGray
        )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Add, contentDescription = stringResource(R.string.nav_add)) },
            label = { Text(stringResource(R.string.nav_add)) },
            selected = currentRoute == Screen.SelectNoteType.route,
            onClick = {
                navController.navigate(Screen.SelectNoteType.route) {
                    popUpTo(Screen.Home.route)
                    launchSingleTop = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.DarkGray
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.nav_search)) },
            label = { Text(stringResource(R.string.nav_search)) },
            selected = currentRoute == Screen.SearchNote.route,
            onClick = {
                navController.navigate(Screen.SearchNote.route) {
                    popUpTo(Screen.Home.route)
                    launchSingleTop = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.DarkGray
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.regresar),
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2C2C2C),
            titleContentColor = Color.White
        )
    )
}


@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
