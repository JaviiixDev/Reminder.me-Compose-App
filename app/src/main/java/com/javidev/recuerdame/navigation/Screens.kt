package com.javidev.recuerdame.navigation

/**
 * define las pantallas de la aplicacion con un identificador para poderlas manejar con el navController
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object SelectNoteType : Screen("select_note_type")
    object CreateNote : Screen("create_note")
    object SearchNote : Screen("search_note")
    object NoteDetail : Screen("note_detail")
}
