package com.javidev.recuerdame.screens

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.javidev.recuerdame.R
import com.javidev.recuerdame.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectNoteTypeScreen(navController: NavHostController) {
    val context = LocalContext.current
    Scaffold(
        topBar = { TopAppBar(title = { Text("Tipo de nota") }) }
    ) { innerPadding ->
        val scrollState = rememberScrollState()//recuerda el scroll de la pantalla
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black)
                .padding(bottom = 90.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            //titulo agregar nota
            Text(
                text = stringResource(R.string.agregar_nota),
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 20.sp),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            //card para ingresar a la pantalla agregar nota
            SelectCard(R.drawable.baseline_text_snippet_24,R.string.select_notasencilla,onClick = { navController.navigate(Screen.CreateNote.route) })
            //card para ingresar a la pantalla de agregar nota de voz
            SelectCard(R.drawable.voicenote,
                R.string.select_notavoz,
                onClick = {
                Toast.makeText(context,R.string.no_disponible, Toast.LENGTH_SHORT).show()
                }
            )
            //card para ingresar a la pantalla agregar documento scan
            SelectCard(R.drawable.baseline_document_scanner_24,
                R.string.select_escanear,
                onClick = {
                Toast.makeText(context,R.string.no_disponible, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

/**
 * Diseño de card de los tipos de seleccion
 */
@Composable
fun SelectCard(
    @DrawableRes imageRes: Int,
    @StringRes selectName:Int,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable { onClick() }, //card clickeable
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424242)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 20.dp, top = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null ,
                modifier = Modifier.weight(1f)
                )
            Text(
                text = stringResource(selectName),
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 16.sp),
                color = Color.White,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {

}
