package com.javidev.recuerdame.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDateTime

/**
 * Representa la informacion de una nota, en una lista de notas de ejemplo
 */
data class Note(
    val id : Int,
    val noteTitle: String,
    val noteContent: String,
    val noteDate: LocalDateTime,
    val noteCategory: String
)

@RequiresApi(Build.VERSION_CODES.O)
val notes =  mutableStateListOf(
    Note(1,"Bienvenida", "Gracias por usar la app. Esperamos que disfrutes la experiencia.", LocalDateTime.of(2025, 5, 19, 10, 0), "cat_1"),
    Note(2,"Primer Viaje", "Prepárate para tu viaje con esta lista de cosas importantes.", LocalDateTime.of(2025, 3, 17, 10, 0), "cat_4"),
    Note(3,"Trabajo Pendiente", "Recuerda enviar el reporte antes del viernes.", LocalDateTime.of(2025, 1, 14, 10, 0), "cat_2"),
    Note(4,"Notas de Escuela", "Revisión del temario para el examen de matemáticas.", LocalDateTime.of(2025, 5, 1, 10, 0), "cat_1"),
    Note(5,"Recordatorio", "Llevar documentos para la inscripción.", LocalDateTime.of(2025, 3, 13, 10, 0), "cat_1"),
    Note(6,"Lista Personal", "Comprar regalo para el cumpleaños de Ana.", LocalDateTime.of(2025, 5, 10, 10, 0), "cat_3"),
    Note(7,"Clase Extra", "Sesión adicional de física el lunes.", LocalDateTime.of(2025, 2, 1, 10, 0), "cat_1"),
    Note(8,"Evento Escolar", "Asistencia obligatoria al taller de robótica.", LocalDateTime.of(2025, 5, 6, 10, 0), "cat_3"),
    Note(9,"Notas Personales", "Reflexiones y metas para el mes de mayo.", LocalDateTime.of(2025, 5, 1, 10, 0), "cat_4")
)