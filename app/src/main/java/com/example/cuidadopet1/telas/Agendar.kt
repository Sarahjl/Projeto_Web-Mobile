package com.example.cuidadopet1.telas


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cuidadopet1.ui.theme.Joti
import com.example.cuidadopet1.ui.theme.VerdeEscuro
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarScreen(navController: NavController){
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
                .add(agendamento)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(context, "Cadastro salvo com sucesso", Toast.LENGTH_SHORT).show()
                    navController.navigate("agendamentos") // Substitua com a navegação correta
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Erro ao salvar", e)
                    Toast.makeText(context, "Erro ao salvar", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
        }
    }

    // Opções para os campos de seleção
    val tiposPet = listOf("Ave", "Cachorro", "Coelho", "Gato")
    val portes = listOf("Pequeno", "Medio", "Grande")
    val sexos = listOf("Macho", "Femea")
    val servicos = listOf("Banho", "Banho + Tosa Higienica", "Banho + Tosa Completa", "Hidratação de Pelo")


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

//                OutlinedTextField(
//                    value = tipoPet,
//                    onValueChange = { tipoPet = it },
//                    label = { Text("Tipo de Pet", fontFamily = FontFamily.Default) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = outlinedTextFieldColors(
//                        focusedBorderColor = Color(0x4D000000),
//                        unfocusedBorderColor = Color(0x4D000000),
//                        containerColor = Color(0XFFEFEFEF),
//                        unfocusedPlaceholderColor = Color.Transparent,
//                    ),
//                )
                DropdownSelector(
                    label = "Tipo de Pet",
                    options = listOf("Ave", "Cachorro", "Coelho", "Gato"),
                    selectedOption = tipoPet,
                    onOptionSelected = { tipoPet = it }
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
//                OutlinedTextField(
//                    value = porte,
//                    onValueChange = { porte = it },
//                    label = { Text("Porte", fontFamily = FontFamily.Default) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = outlinedTextFieldColors(
//                        focusedBorderColor = Color(0x4D000000),
//                        unfocusedBorderColor = Color(0x4D000000),
//                        containerColor = Color(0XFFEFEFEF),
//                        unfocusedPlaceholderColor = Color.Transparent,
//                    ),
//                )
                DropdownSelector(
                    label = "Porte",
                    options = listOf("Pequeno", "Médio", "Grande"),
                    selectedOption = porte,
                    onOptionSelected = { porte = it }
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

//                OutlinedTextField(
//                    value = sexo,
//                    onValueChange = { sexo = it },
//                    label = { Text("Sexo", fontFamily = FontFamily.Default) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = outlinedTextFieldColors(
//                        focusedBorderColor = Color(0x4D000000),
//                        unfocusedBorderColor = Color(0x4D000000),
//                        containerColor = Color(0XFFEFEFEF),
//                        unfocusedPlaceholderColor = Color.Transparent,
//                    ),
//                )

                DropdownSelector(
                    label = "Sexo",
                    options = listOf("Macho", "Fêmea"),
                    selectedOption = sexo,
                    onOptionSelected = { sexo = it }
                )

//                OutlinedTextField(
//                    value = servico,
//                    onValueChange = { servico = it },
//                    label = { Text("Serviço", fontFamily = FontFamily.Default) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = outlinedTextFieldColors(
//                        focusedBorderColor = Color(0x4D000000),
//                        unfocusedBorderColor = Color(0x4D000000),
//                        containerColor = Color(0XFFEFEFEF),
//                        unfocusedPlaceholderColor = Color.Transparent,
//                    ),
//                )

                DropdownSelector(
                    label = "Serviço",
                    options = listOf(
                        "Banho",
                        "Banho + Tosa Higienica",
                        "Banho + Tosa Completa",
                        "Hidratação de Pelo"
                    ),
                    selectedOption = servico,
                    onOptionSelected = { servico = it }
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
                    Text("Agendar", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(selectedOption) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = currentSelection.ifEmpty { "Selecione" },
            onValueChange = {},
            label = { Text(label, fontFamily = FontFamily.Default) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = outlinedTextFieldColors(
                focusedBorderColor = Color(0x4D000000),
                unfocusedBorderColor = Color(0x4D000000),
                containerColor = Color(0XFFEFEFEF),
                unfocusedPlaceholderColor = Color.Transparent,
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}