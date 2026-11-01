package br.com.bjjtracker.ui.home.model

data class StatItem(
    val type: StatType,
    val value: String,
    val detailArgs: List<Any> = emptyList(),
)
