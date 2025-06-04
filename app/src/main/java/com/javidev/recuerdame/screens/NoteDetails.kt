package com.javidev.recuerdame.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.javidev.recuerdame.R
import com.javidev.recuerdame.data.Note
import com.javidev.recuerdame.data.notes
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(navController: NavHostController, note: Note) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Ver nota") }) },
        content = { innerPadding ->
            val scrollState = rememberScrollState()//recuerda el scroll de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Black)
                    .padding(bottom = 90.dp, top = 16.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //indica si se debe mostrar el dialog de eliminacion o no
                var showDialog by remember { mutableStateOf(false) }
                //muestra la fecha y hora
                Text(
                    text = note.noteDate.format(
                        DateTimeFormatter.ofPattern("MMMM d, yyyy - h:mm a", Locale.getDefault())
                    ),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                //muestra el titulo de la nota
                TextField(
                    value = note.noteTitle,
                    onValueChange = {},
                    enabled = false,
                    label = { Text(stringResource(R.string.titulo_nota)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.White,
                        disabledLabelColor = Color.Gray,
                        disabledIndicatorColor = Color.Gray,
                        disabledLeadingIconColor = Color.Gray,
                        disabledTrailingIconColor = Color.Gray,
                        disabledPlaceholderColor = Color.Gray,
                        containerColor = Color.DarkGray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                //muestra el contenido de la nota
                TextField(
                    value = note.noteContent,
                    onValueChange = {},
                    enabled = false,
                    label = { Text(stringResource(R.string.contenido_nota)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.White,
                        disabledLabelColor = Color.Gray,
                        disabledIndicatorColor = Color.Gray,
                        containerColor = Color.DarkGray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                val categoryName = when (note.noteCategory) {
                    "cat_1" -> stringResource(R.string.cat_1)
                    "cat_2" -> stringResource(R.string.cat_2)
                    "cat_3" -> stringResource(R.string.cat_3)
                    "cat_4" -> stringResource(R.string.cat_4)
                    else -> ""
                }
                //muestra la categoria a la que pertenece la nota
                TextField(
                    value = categoryName,
                    onValueChange = {},
                    enabled = false,
                    label = { Text(stringResource(R.string.categoria_nota)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.White,
                        disabledLabelColor = Color.Gray,
                        disabledIndicatorColor = Color.Gray,
                        containerColor = Color.DarkGray
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))
                //muestra el boton de eliminar, al presionarle abre el dialog
                Button(
                    onClick = {showDialog = true},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.eliminar_nota), color = Color.White)
                }

                //dialog de eliminacion
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(stringResource(R.string.eliminar_nota_pregunta)) },
                        text = { Text(stringResource(R.string.eliminar_advertencia) ) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    notes.remove(note) // Eliminar nota
                                    showDialog = false
                                    navController.popBackStack() // Regresar a pantalla anterior
                                }
                            ) {
                                Text(stringResource(R.string.eliminar), color = Color.Red)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDialog = false }
                            ) {
                                Text(stringResource(R.string.eliminar_cancelar))
                            }
                        }
                    )
                }

            }
        }
    )
}
