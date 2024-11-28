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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cuidadopet1.Agendamento
import com.example.cuidadopet1.R
import com.example.cuidadopet1.fetchAgendamentos
import com.example.cuidadopet1.ui.theme.Joti
import com.example.cuidadopet1.ui.theme.Verde
import com.example.cuidadopet1.ui.theme.VerdeEscuro
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController){
    val auth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Verifica se o id do usuário já está na sessão
    val sharedPreferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE)
    val id = sharedPreferences.getString("id", null) // Retorna o userId ou null se não encontrado

    // Se houver um id de usuário na sessão, redireciona diretamente
    if (id != null) {
        // Aguarda a navegação antes de sair da tela de login
        navController.navigate("agendar") { popUpTo("login") { inclusive = true } } // Ou qualquer rota de agendamento
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Verde),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Logo",
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "Cuidado Pet",
                    fontSize = 30.sp,
                    fontFamily = Joti,
                    color = VerdeEscuro,
                )
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Login",
            fontSize = 30.sp,
            fontFamily = Joti,
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontFamily = FontFamily.Default) },
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = Color.White,
                unfocusedPlaceholderColor = Color.Transparent,
            ),
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha", fontFamily = FontFamily.Default) },
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = Color.White,
                unfocusedPlaceholderColor = Color.Transparent,
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )


        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if(email == "" || password == ""){
                    Toast.makeText(
                        context,
                        "Informe seu email e senha",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button;
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = task.result?.user?.uid ?: ""
                            Toast.makeText(context, "Login bem-sucedido", Toast.LENGTH_SHORT).show()

                            // Salva o ID do usuário na sessão
                            val sharedPreferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            // Salva o userId no SharedPreferences
                            editor.putString("id", userId)
                            editor.apply()

                            navController.navigate("agendar") // Redireciona para ListScreen
                        } else {
                            Toast.makeText(context, "Falha no login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = VerdeEscuro,// Define a cor de fundo do botão
                contentColor = Color.White    // Define a cor do texto dentro do botão
            ),
            modifier = Modifier.width(150.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}