package com.folksdev.account.dto

import com.folksdev.account.model.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime
//account bilgilerini customerda gösterebilmek için dto katmanı oluşturuldu
    data class CustomerAccountDto(
        //accountdto ile aynı değişkene sahip
        val id:String,
        val balance:BigDecimal?=BigDecimal.ZERO,
        val transactions:Set<TransactionDto>?,
        val creationDate:LocalDateTime



    )
