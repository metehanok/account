package com.folksdev.account.dto

import com.folksdev.account.model.Customer
import com.folksdev.account.model.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime

//veritabanı ile istemci arasında bir filtre görevi görür,veritabanında özel alanların
//istemciye ulaşmaması istendiği durumda ulaşacak yerleri istemciye ulaştırır
data class AccountDto(
    //dto,esneklik sağlaması ve ileriye dönük değişiklik yapıldığında model yerine dto
    //üzerinde değişiklik yapılması için kullanılır.sadece veri taşırlar,iş mantığı içermez
    val id: String?,
    val balance: BigDecimal?,
    val creationDate : LocalDateTime,
    val customer : AccountCustomerDto?,
    var transactions: Set<TransactionDto>

    )
