package com.example.cuidadopet1.telas


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cuidadopet1.ui.theme.Joti
import com.example.cuidadopet1.ui.theme.VerdeEscuro
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarScreen(navController: NavController, agendamentoId: String){
    var nomePet by remember { mutableStateOf("") }
    var tipoPet by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var porte by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var servico by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    val db = Firebase.firestore
    val context = LocalContext.current

    // Opções para os seletores
    val tiposPet = listOf("Ave", "Cachorro", "Coelho", "Gato")
    val portes = listOf("Pequeno", "Médio", "Grande")
    val sexos = listOf("Macho", "Fêmea")
    val servicos = listOf("Banho", "Banho + Tosa Higiênica", "Banho + Tosa Completa", "Hidratação de Pelo")

    // Use LaunchedEffect to load data only once when the screen is first composed
    LaunchedEffect(agendamentoId) {
        db.collection("agendamentos")
            .document(agendamentoId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val agendamento = document.data
                    nomePet = agendamento?.get("nome") as? String ?: ""
                    tipoPet = agendamento?.get("tipo_pet") as? String ?: ""
                    raca = agendamento?.get("raca") as? String ?: ""
                    porte = agendamento?.get("porte") as? String ?: ""
                    idade = agendamento?.get("idade") as? String ?: ""
                    sexo = agendamento?.get("sexo") as? String ?: ""
                    servico = agendamento?.get("servico") as? String ?: ""
                    data = agendamento?.get("data_tosa") as? String ?: ""
                    horario = agendamento?.get("hora_tosa") as? String ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erro ao carregar agendamento", e)
                Toast.makeText(context, "Erro ao carregar agendamento", Toast.LENGTH_SHORT).show()
            }
    }

    // Função para salvar no Firestore
    fun salvarAgendamento() {
        if (nomePet.isNotEmpty() && tipoPet.isNotEmpty() && raca.isNotEmpty() && porte.isNotEmpty() &&
            idade.isNotEmpty() && sexo.isNotEmpty() && servico.isNotEmpty() && data.isNotEmpty() && horario.isNotEmpty()) {

            // Criar um objeto de agendamento
            val agendamento = hashMapOf(
                "nome" to nomePet,
                "tipo_pet" to tipoPet,
                "raca" to raca,
                "porte" to porte,
                "idade" to idade,
                "sexo" to sexo,
                "servico" to servico,
                "data_tosa" to data,
                "hora_tosa" to horario
            )

            // Salvar no Firestore
            db.collection("agendamentos")
                .document(agendamentoId) // Referência ao documento específico
                .set(agendamento) // Usa set() para substituir o documento
                .addOnSuccessListener {
                    Toast.makeText(context, "Agendamento atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    navController.navigate("agendamentos") // Substitua com a navegação correta
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Erro ao atualizar agendamento", e)
                    Toast.makeText(context, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
    ) {
        Menu(navController)

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 50.dp)
            ) {
                Text(
                    text = "Agende aqui",
                    fontSize = 30.sp,
                    fontFamily = Joti,
                    color = VerdeEscuro,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Preencha o formulário para marcar o banho/tosa do seu pet.",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = nomePet,
                    onValueChange = { nomePet = it },
                    label = { Text("Nome do Pet", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )

                OutlinedTextField(
                    value = tipoPet,
                    onValueChange = { tipoPet = it },
                    label = { Text("Tipo de Pet", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )

                OutlinedTextField(
                    value = raca,
                    onValueChange = { raca = it },
                    label = { Text("Raça", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )
                OutlinedTextField(
                    value = porte,
                    onValueChange = { porte = it },
                    label = { Text("Porte", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )

                OutlinedTextField(
                    value = idade,
                    onValueChange = { idade = it },
                    label = { Text("Idade", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )

                OutlinedTextField(
                    value = sexo,
                    onValueChange = { sexo = it },
                    label = { Text("Sexo", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )

                OutlinedTextField(
                    value = servico,
                    onValueChange = { servico = it },
                    label = { Text("Serviço", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )

                OutlinedTextField(
                    value = data,
                    onValueChange = { data = it },
                    label = { Text("Data de agendamento", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )

                OutlinedTextField(
                    value = horario,
                    onValueChange = { horario = it },
                    label = { Text("Horário", fontFamily = FontFamily.Default) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color(0x4D000000),
                        unfocusedBorderColor = Color(0x4D000000),
                        containerColor = Color(0XFFEFEFEF),
                        unfocusedPlaceholderColor = Color.Transparent,
                    ),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { salvarAgendamento() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFA9CB6C))
                ) {
                    Text("Atualizar", color = Color.White)
                }
            }
        }
    }
}