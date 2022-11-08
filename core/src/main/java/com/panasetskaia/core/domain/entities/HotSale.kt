package com.panasetskaia.core.domain.entities

data class HotSale(
    val id: String,
    val isNew: Boolean? = false,
    val title: String? = null,
    val subtitle: String? = null,
    val picUrl: String? = null,
    val isBuy: Boolean? = false
)
