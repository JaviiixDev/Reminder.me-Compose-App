package com.javidev.recuerdame.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.javidev.recuerdame.R
import com.javidev.recuerdame.data.notes
import com.javidev.recuerdame.screens.*

/**
 * define la estructura de navegacion de la app, controla a que pantalla se dirigue, define las rutas y tambien pasa parametos por las rutas
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.SelectNoteType.route) {
            SelectNoteTypeScreen(navController)
        }
        composable(Screen.CreateNote.route) {
            CreateNoteScreen(navController)
        }
        composable(Screen.SearchNote.route) {
            SearchNoteScreen(navController)
        }
        composable(
            route = Screen.NoteDetail.route + "/{noteId}"//pasa por la ruta el id de la nota a ver
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: -1
            val note = notes.find { it.id == noteId }
            //si la nota existe manda los datos a la pagina y la abre
            if (note != null) {
                NoteDetailScreen(
                    navController = navController,
                    note = note
                )
            } else {
                Text(stringResource(R.string.nota_no_encontrada))
            }
        }
    }
}

