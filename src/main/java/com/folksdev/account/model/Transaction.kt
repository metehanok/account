package com.folksdev.account.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Transaction(
    @Id
    //her yeni kayıtta otomatik ıd oluşutrulur UUID sayesinde
    @GeneratedValue(generator = "UUID")
    //bu uuıd'nin çalışma stratejisini belirlemek gerekir.
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String?,//kotlinde değişken tanımlama
    val transactionType: TransactionType?=TransactionType.INITIAL,
    val amount :BigDecimal?,
    val transactionDate:LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //transaction tablosunda "account id" diye bir column açar
    @JoinColumn(name="account_id", nullable = false)
    val account: Account
){
    constructor(amount: BigDecimal,account: Account) : this(
        id=null,
        amount=amount,
        transactionType =TransactionType.INITIAL,
        transactionDate= LocalDateTime.now(),
        account = account
    )
    constructor() : this(
        id = null,
        transactionType = TransactionType.INITIAL,
        amount = BigDecimal.ZERO,
        transactionDate = LocalDateTime.now(),
        account = Account() // Varsayılan bir Account nesnesi oluşturuluyor
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        if (id != other.id) return false
        if (transactionType != other.transactionType) return false
        if (amount != other.amount) return false
        if (transactionDate != other.transactionDate) return false
        if (account != other.account) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (transactionType?.hashCode() ?: 0)
        result = 31 * result + (amount?.hashCode() ?: 0)
        result = 31 * result + transactionDate.hashCode()
        return result
    }
}

enum class TransactionType{
    INITIAL,TRANSFER
}