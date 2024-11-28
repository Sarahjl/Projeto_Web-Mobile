package com.example.cuidadopet1.telas

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cuidadopet1.R
import com.example.cuidadopet1.ui.theme.VerdeEscuro

@Composable
fun Menu(navController: NavController){
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth().background(Color(0XFFA9CB6C)).padding(end = 15.dp).height(80.dp),
        ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.size(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0XFFA9CB6C),
                    contentColor = Color.White
                ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Logo",
                    modifier = Modifier.size(200.dp)
                )
            }

            // Verifica se o id do usuário já está na sessão
            val sharedPreferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE)
            val id = sharedPreferences.getString("id", null) // Retorna o userId ou null se não encontrado

            Row (verticalAlignment = androidx.compose.ui.Alignment.CenterVertically){
                Button(
                    onClick = { navController.navigate("agendamentos") },
                    modifier = Modifier.padding(end = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0XFFA9CB6C),
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = "Agendamentos",
                        fontFamily = FontFamily.Default,
                        color = Color.White
                    )
                }
                // Se houver um id de usuário na sessão, redireciona diretamente
                if (id != null) {
                    Button(
                        onClick = {
                            val sharedPreferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()

                            // Remover a chave "id" específica
                            editor.remove("id")

                            // Confirmar a aplicação das alterações
                            editor.apply()
                            navController.navigate("login")
                        },
                        modifier = Modifier.size(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFA9CB6C),
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Sair",
                            modifier = Modifier.padding(start = 10.dp),
                            fontFamily = FontFamily.Default,

                            )
                    }
                }else{
                    Text(
                        text = "Agendamentos",
                        modifier = Modifier.padding(start = 10.dp),
                        fontFamily = FontFamily.Default,
                    )
                }
            }
        }
    }
}