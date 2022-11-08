package com.panasetskaia.core.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhoneDbModel (
    @PrimaryKey(autoGenerate = true)
    val id: String? = null,
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
    var quantity: Int = 0
)