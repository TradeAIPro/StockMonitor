package com.artinsoft.StockMonitor.presentation.controller

import com.artinsoft.StockMonitor.domain.repository.BingxOrder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

//@Controller
class TestController(
    var bingxOrder: BingxOrder
) {
    @GetMapping(value = ["/getBalance"])
    fun getBalance(): String {
        bingxOrder.getBalance("USDT")
        return "OK"
    }
}