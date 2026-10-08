package br.com.bjjtracker.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Home : Route
}
