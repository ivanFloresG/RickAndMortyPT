package com.aion.rickandmortypt.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Principal

@Serializable
data class Details(val id: Int)

@Serializable
data class LocationMap(val lat: Double, val long: Double, val characterName: String)
