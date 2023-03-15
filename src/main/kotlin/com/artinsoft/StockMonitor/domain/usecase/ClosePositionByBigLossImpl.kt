package com.artinsoft.StockMonitor.domain.usecase

import com.artinsoft.StockMonitor.domain.repository.BingxOrder
import com.artinsoft.common.UseCase
import org.springframework.beans.factory.annotation.Value

@UseCase
class ClosePositionByBigLossImpl(
    private val bingxOrder: BingxOrder,
    @Value("\${bingx.baseMargin}")
    private val baseMargin: Double,
    @Value("\${bingx.lossPercent}")
    private val lossPercent: Double,
) : ClosePositionByBigLoss {
    override fun closePositions() {
        val balance = bingxOrder.getBalance("USDT")
        require(balance != null) { "the balance is null" }
        val total: Double = balance.data.account.balance + balance.data.account.unrealisedPNL
        val profit = total / baseMargin * 100 - 100
        if (total > baseMargin) {
            print("OK: $profit %")
            return
        }
        if (profit * -1 > lossPercent) {
            print("ERROR: $profit % loss")
            bingxOrder.closePositions()
            return
        }
        print("Warning: $profit % loss")
    }
}