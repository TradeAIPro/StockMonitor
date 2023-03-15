package com.artinsoft.StockMonitor.domain.entity

data class Account(
    val userId: String,
    val currency: String,
    val balance: Double,
    val equity: Double,
    val unrealisedPNL: Double,
    val realisedPNL: Double,
    val availableMargin: Double,
    val usedMargin: Double,
    val freezedMargin: Double,
    val longLeverage: Double,
    val shortLeverage: Double,
)
