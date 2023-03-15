package com.artinsoft.StockMonitor.domain.repository

import com.artinsoft.StockMonitor.domain.entity.Balance

interface BingxOrder {
    fun getBalance(symbol: String): Balance?
    fun getPositions(symbol: String)
    fun closePositions()
}