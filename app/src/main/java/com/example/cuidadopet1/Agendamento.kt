package com.example.cuidadopet1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore

data class Agendamento(
    val id: String = "",
    val nome: String = "",
    val tipoPet: String = "",
    val raca: String = "",
    val porte: String = "",
    val idade: String = "",
    val sexo: String = "",
    val servico: String = "",
    val dataTosa: String = "",
    val horaTosa: String = ""
)

@Composable
fun fetchAgendamentos(): SnapshotStateList<Agendamento> {
    val agendamentos = remember { mutableStateListOf<Agendamento>() }
    val firestore = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        firestore.collection("agendamentos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val agendamento = Agendamento(
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
                    agendamentos.add(agendamento)
                }
            }
            .addOnFailureListener { exception ->
                println("Erro ao buscar dados: ${exception.message}")
            }
    }
    return agendamentos
}