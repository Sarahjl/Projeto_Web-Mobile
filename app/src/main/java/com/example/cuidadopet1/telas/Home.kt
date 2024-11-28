package com.example.cuidadopet1.telas

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cuidadopet1.R
import com.example.cuidadopet1.ui.theme.Cinza
import com.example.cuidadopet1.ui.theme.CinzaEscuro
import com.example.cuidadopet1.ui.theme.Joti
import com.example.cuidadopet1.ui.theme.Verde
import com.example.cuidadopet1.ui.theme.VerdeEscuro
import com.google.firebase.Firebase

@Composable
fun HomeScreen(navController: NavController){
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
    )  {
        Menu(navController)
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "BANHO e TOSA",
                    fontSize = 30.sp,
                    fontFamily = Joti,
                    color = VerdeEscuro,
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "O cuidado que seu pet merece!",
                    fontSize = 20.sp,
                    fontFamily = Joti,
                    color = Color.Black,
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = {
                    navController.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeEscuro,
                    contentColor = Color.White
                ),
                modifier = Modifier.width(150.dp)
            ) {
                Text(text = "Agende já")
            }

            Spacer(modifier = Modifier.height(50.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth().background(Cinza).padding(vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                "A higiene do seu bichinho é indicada pelos veterinários",
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pata),
                    contentDescription = "Logo",
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Evita infecções e alergias causadas por sujeira e parasitas.",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Spacer(Modifier.height(15.dp))

            Row(
                modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pata),
                    contentDescription = "Logo",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "A higiene adequada contribui para o bem-estar e saúde do animal.",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }


            Spacer(Modifier.height(15.dp))

            Row(
                modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pata),
                    contentDescription = "Logo",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Banhos e cuidados ajudam a combater pulgas, carrapatos e outros parasitas.",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }


            Spacer(Modifier.height(15.dp))

            Row(
                modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pata),
                    contentDescription = "Logo",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "A higiene regular facilita a identificação de feridas, nódulos ou sinais de doença.",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }

        Row(
            modifier = Modifier.padding(vertical = 18.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Conheça as etapas do banho",
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp),
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            CardImg("Banho", R.drawable.banho)
            CardImg("Secagem", R.drawable.secagem)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            CardImg("Escovação", R.drawable.escovacao)
            CardImg("Corte de unhas", R.drawable.corte_unha)
        }
    }


    Spacer(modifier = Modifier.height(50.dp))
}

@Composable
fun CardImg(text: String, img: Int){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(CinzaEscuro).padding(5.dp).width(150.dp)
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = text,
            modifier = Modifier.size(30.dp)
        )

        Text(
            text,
            fontSize = 10.sp,
            color = Color.Black,
            fontFamily = FontFamily.Default
        )
    }
}