package com.artinsoft.StockMonitor.domain.entity

data class Balance(
    val code: Int,
    val msg: String,
    val ttl: Int,
    val data: Data,
)

