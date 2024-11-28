package com.example.cuidadopet1.telas

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cuidadopet1.ui.theme.Joti
import com.example.cuidadopet1.ui.theme.VerdeEscuro
import com.example.cuidadopet1.Agendamento
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

@Composable
fun AgendamentosScreen(navController: NavController) {
    val scrollStateHorizontal = rememberScrollState()
    val agendamentos = remember { mutableStateOf<List<Agendamento>>(emptyList()) }
    val firestore = FirebaseFirestore.getInstance()
    val id = 10
    var showDialog by remember { mutableStateOf(false) }
    var agendamentoRemover by remember { mutableStateOf<Agendamento?>(null) }
    val db = Firebase.firestore
    val context = LocalContext.current

    LaunchedEffect(id) {
        try {
            firestore.collection("agendamentos")
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        Log.e("Livros", "Erro ao buscar livros", exception)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.map { document ->
                        Agendamento(
                            id = document.id,
                            nome = document.getString("nome") ?: "",
                            tipoPet = document.getString("tipo_pet") ?: "",
                            raca = document.getString("raca") ?: "",
                            porte = document.getString("porte") ?: "",
                            idade = document.getString("idade") ?: "",
                            sexo = document.getString("sexo") ?: "",
                            servico = document.getString("servico") ?: "",
                            dataTosa = document.getString("data_tosa") ?: "",
                            horaTosa = document.getString("hora_tosa") ?: ""
                        )
                    }.orEmpty()
                    agendamentos.value = list
                }
        } catch (error: Exception) {
            Log.e("Agendamentos", "Erro ao configurar o listener do Firestore", error)
        }
    }

    fun removerAgendamento(agendamento: Agendamento) {
        // Verifica se o ID do agendamento existe
        val agendamentoId = agendamento.id
        if (agendamentoId != null) {
            // Referência ao documento do Firestore com o ID do agendamento
            db.collection("agendamentos")
                .document(agendamentoId) // Usamos o ID do documento
                .delete() // Remove o documento
                .addOnSuccessListener {
                    Toast.makeText(context, "Agendamento removido com sucesso", Toast.LENGTH_SHORT).show()
                    navController.navigate("agendamentos") // Navegação após remover
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Erro ao remover agendamento", e)
                    Toast.makeText(context, "Erro ao remover agendamento", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "ID do agendamento não encontrado", Toast.LENGTH_SHORT).show()
        }
    }


    Column {
        Menu(navController)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Meus agendamentos",
                    fontSize = 30.sp,
                    fontFamily = Joti,
                    color = VerdeEscuro,
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Consulte aqui todos os agendamentos que foram realizados",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }

        // Tabela com scroll horizontal e vertical
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollStateHorizontal)
                    .padding(4.dp)
            ) {
                Column {
                    // Cabeçalho
                    Row(
                        modifier = Modifier
                            .background(Color(0x8FD8D4D4)) // Verde claro para cabeçalho
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center //remover se nao der certo
                    ) {
                        listOf(
                            "Nome", "Tipo", "Raça", "Porte", "Idade", "Sexo",
                            "Serviço", "Data", "Horário", "Ações"
                        ).forEach { header ->
                            Text(
                                text = header,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .width(100.dp),
                                textAlign = TextAlign.Center, //remover
                                color = Color.Black,
                                fontFamily = FontFamily.Default
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        itemsIndexed(agendamentos.value) { index, agendamento ->
                            Log.d("Livros", "Agendamento: $agendamento")
                            Log.d("Livros", "Agendamento: ${agendamento.nome}")
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = agendamento.nome,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = agendamento.tipoPet,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = agendamento.raca,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = agendamento.porte,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = agendamento.idade,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = agendamento.sexo,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = agendamento.servico,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = agendamento.dataTosa,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = agendamento.horaTosa,
                                    modifier = Modifier.padding(10.dp).width(100.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                IconButton(
                                    onClick = {
                                        agendamentoRemover = agendamento // Armazenar o agendamento a ser removido
                                        showDialog = true // Exibir o modal de confirmação
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Excluir agendamento"
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        navController.navigate("agendar/${agendamento.id}")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar agendamento"
                                    )
                                }
                            }
                        }
                    }

                    // Modal de confirmação
                    if (showDialog && agendamentoRemover != null) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Confirmar Exclusão") },
                            text = { Text("Você tem certeza que deseja excluir este agendamento?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    agendamentoRemover?.let {
                                        removerAgendamento(it)
                                    }
                                    showDialog = false
                                }) {
                                    Text("Confirmar")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                }
            }

            // FloatingActionButton no canto inferior direito
            FloatingActionButton(
                onClick = { navController.navigate("agendar") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = VerdeEscuro // Cor do botão
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar novo agendamento",
                    tint = Color.White
                )
            }
        }
    }
}
