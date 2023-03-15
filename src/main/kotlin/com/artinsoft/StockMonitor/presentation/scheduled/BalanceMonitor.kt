package com.artinsoft.StockMonitor.presentation.scheduled

import com.artinsoft.StockMonitor.domain.usecase.ClosePositionByBigLoss
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller

@Controller
class BalanceMonitor(
    private val closePositionByBigLoss: ClosePositionByBigLoss
) {
    @Scheduled(fixedDelay = 6000)
    fun balanceMonitor(){
        closePositionByBigLoss.closePositions()
    }
}