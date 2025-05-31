package com.javidev.recuerdame.screens

import android.os.Build
import android.service.autofill.OnClickAction
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsActions.OnClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.javidev.recuerdame.R
import com.javidev.recuerdame.data.Note
import com.javidev.recuerdame.data.notes
import com.javidev.recuerdame.navigation.Screen
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    //guarda la categoria seleccionada para el filtro
    val selectedCategory = remember { mutableStateOf<Int?>(null) }

    //almacena una lista de categorias
    val categories = listOf(
        R.string.cat_1 to R.drawable.personal,
        R.string.cat_2 to R.drawable.work,
        R.string.cat_3 to R.drawable.school,
        R.string.cat_4 to R.drawable.travel
    )
    //muestra todas las notas si no se ha seleccionado una categoria
    val filteredNotes = if (selectedCategory.value == null) {
        notes
    } else {
        //como obtenemos de stringRes las categorias, aqui se le asigna el nombre en string para poder comparar
        val categoryName = when (selectedCategory.value) {
            R.string.cat_1 -> "Personal"
            R.string.cat_2 -> "Trabajo"
            R.string.cat_3 -> "Escuela"
            R.string.cat_4 -> "Viajes"
            else -> null
        }
        //si se selecciona una categoria, entonces se filtra la lista solo por las notas que sean parte de esa categoria
        notes.filter { it.noteCategory == categoryName }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("!Hola, Usuario¡") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
        ) {
            //titulo de las categorias
            Text(
                text = stringResource(R.string.categorias),
                style = TextStyle(fontSize = 16.sp),
                fontWeight = FontWeight.Normal,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp, top = 32.dp, bottom = 16.dp)
            )
            //muestra las categorias en una row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                items(categories) { (name, icon) ->
                    val isSelected = selectedCategory.value == name
                    CategoryCard(
                        categoryName = name,
                        imageRes = icon,
                        isSelected = isSelected,
                        onClick = {
                            selectedCategory.value = if (isSelected) null else name
                        }
                    )
                }
            }
            //titulo notas recientes
            Text(
                text = stringResource(R.string.notas_recientes),
                style = TextStyle(fontSize = 16.sp),
                fontWeight = FontWeight.Light,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp, start = 8.dp)
            )
            //Ordena las notas por fecha y hora, pone primero las mas actuales
            val sortedNotes = filteredNotes.sortedByDescending { it.noteDate }
            //muestra las categorias filtradas en la lista
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 80.dp
                )
            ) {
                items(sortedNotes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        onClick = {
                            navController.navigate(Screen.NoteDetail.route+ "/${note.id}")//si le da click a alguna categoria la abre en la pantalla de detalles de nota
                        }
                    )
                }
            }
        }
    }
}

/**
 * diseño de la visualizacion de la nota
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    //limita los caracteres de la previsualizacion de la nota
    val previewText = if (note.noteContent.length > 40) {
        note.noteContent.take(40) + "..."
    } else {
        note.noteContent
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424242)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = note.noteDate.format(
                                DateTimeFormatter.ofPattern("d MMMM 'de' yyyy", Locale("es", "ES"))
                            ),
                            style = TextStyle(fontSize = 12.sp),
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFF6F00)
                            )
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, start = 6.dp, end = 6.dp ),
                                text = note.noteCategory,
                                style = TextStyle(fontSize = 11.sp),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = note.noteTitle,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = previewText,
                        fontWeight = FontWeight.Normal,
                        style = TextStyle(fontSize = 13.sp),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón centrado verticalmente en toda la Card
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Ver nota",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

/**
 * diseño de la card de la categoria
 */
@Composable
fun CategoryCard(
    @StringRes categoryName: Int,
    @DrawableRes imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFFF6F00) else Color(0xFF424242)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Card(
            modifier = Modifier.size(width = 80.dp, height = 60.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            shape = RoundedCornerShape(30.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = stringResource(categoryName),
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 16.sp),
            color = Color.White
        )
    }
}

