package com.example.cuidadopet1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cuidadopet1.ui.theme.CuidadoPet1Theme
import com.example.cuidadopet1.telas.AgendamentosScreen
import com.example.cuidadopet1.telas.AgendarScreen
import com.example.cuidadopet1.telas.EditarScreen
import com.example.cuidadopet1.telas.HomeScreen
import com.example.cuidadopet1.telas.LoginScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuidadoPet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    Navigation()
                }
            }
        }
    }
}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("agendamentos") { AgendamentosScreen(navController) }
        composable("agendar") { AgendarScreen(navController) }

        composable("agendar/{agendamentoId}") { backStackEntry ->
            var agendamentoId = backStackEntry.arguments?.getString("agendamentoId")
            agendamentoId = requireNotNull(agendamentoId)
            EditarScreen(navController, agendamentoId)
        }
    }
}