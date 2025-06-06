package com.javidev.recuerdame.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Crear nota") }) }
    ) { innerPadding ->
        val scrollState = rememberScrollState()//recuerda el scroll de la pantalla
        Column(
            modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(innerPadding)
            .padding(bottom = 90.dp, top = 8.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            val fecha = LocalDateTime.now()//obtiene la fecha actual
            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy - h:mm a", Locale.getDefault()) //formatea la fecha a un formato legible
            val formattedDate = fecha.format(formatter)
            //variables para guardar los datos ingresados por el usuario
            var title by remember { mutableStateOf("") }
            var content by remember { mutableStateOf("") }
            var selectedCategory by remember { mutableStateOf("cat_1") }


            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ){
                //muestra la fecha actual
                Text(
                    text = formattedDate.replaceFirstChar { it.uppercase() },
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                //boton de guardar nueva nota
                Button(
                    onClick = {
                        val nuevoId = (notes.maxOfOrNull { it.id } ?: 0) + 1
                        val nuevaNota = Note(
                            id = nuevoId,
                            noteTitle = title,
                            noteContent = content,
                            noteDate = LocalDateTime.now(),
                            noteCategory = selectedCategory
                        )

                        notes.add(nuevaNota) //agregar nueva nota
                        navController.popBackStack() //salir de la pantalla
                    },
                    enabled = title.isNotBlank() && content.isNotBlank(),//solo se activa el boton si se ingreso texto a los campos title y content
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White // color del ícono o texto
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.guardar),
                        tint = Color.White
                    )
                }
            }
            //campo de entrada para el titulo de la nota
            OutlinedTextField(
                value = title,
                onValueChange = {title = it},
                label = { Text(stringResource(R.string.titulo_nota)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White ,
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray
                )
            )
            //campo de entrada para el contenido de la nota
            OutlinedTextField(
                value = content,
                onValueChange = {content = it},
                label = { Text(stringResource(R.string.contenido_nota)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)//altura inicial
                    .padding(horizontal = 16.dp, vertical = 8.dp), // Ocupa el espacio restante
                maxLines = Int.MAX_VALUE, // Permite múltiples líneas
                singleLine = false,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray
                )
            )
            //permite seleccionar un elemento categoria de una lista
            SelectCategoryDropdown(
                selectedOptionText = selectedCategory,
                onOptionSelected = { selectedCategory = it }
            )
        }
    }
}

/**
 * muestra un select con todas las categorias
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCategoryDropdown(
    selectedOptionText: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf(
        "cat_1" to stringResource(R.string.cat_1),
        "cat_2" to stringResource(R.string.cat_2),
        "cat_3" to stringResource(R.string.cat_3),
        "cat_4" to stringResource(R.string.cat_4)
    )

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        val selectedLabel = options.firstOrNull { it.first == selectedOptionText }?.second ?: ""
        OutlinedTextField(
            value = selectedLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.categoria_nota)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White,
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (key, label) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onOptionSelected(key)
                        expanded = false
                    }
                )
            }
        }
    }
}

