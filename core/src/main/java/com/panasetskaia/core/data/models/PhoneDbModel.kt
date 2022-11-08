package com.panasetskaia.core.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.panasetskaia.core.data.database.CartConverters

@Entity
data class PhoneDbModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val isFavorite: Boolean? = false,
    val CPU: String? = null,
    val camera: String? = null,
    @TypeConverters(CartConverters::class)
    val capacities: List<String>? = null,
    @TypeConverters(CartConverters::class)
    val colors: List<String>? = null,
    @TypeConverters(CartConverters::class)
    val images: List<String>? = null,
    val price: Int? = null,
    val rating: Float? = null,
    val sd: String? = null,
    val ssd: String? = null,
    val title: String? = null,
    var quantity: Int = 0
)