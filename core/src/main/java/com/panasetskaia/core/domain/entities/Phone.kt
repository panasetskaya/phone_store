package com.panasetskaia.core.domain.entities

data class Phone(
    val id: Int,
    val isFavorite: Boolean? = false,
    val CPU: String? = null,
    val camera: String? = null,
    val capacities: List<String>? = null,
    val colors: List<String>? = null,
    val images: List<String>? = null,
    val price: Int? = null,
    val rating: Float? = null,
    val sd: String? = null,
    val ssd: String? = null,
    val title: String? = null,
    var quantity: Int = 0,
    var chosenColor: String? = colors?.get(0),
    var chosenCapacity: String? = capacities?.get(0)
)