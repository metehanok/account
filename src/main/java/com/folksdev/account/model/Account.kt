package com.folksdev.account.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

//veritabanında bir karşılığı olduğu için tablo olarak
@Entity
data class Account (
    //her tablonun bir ıd'si vardır
    @Id
    //her yeni kayıtta otomatik ıd oluşutrulur UUID sayesinde
    @GeneratedValue(generator = "UUID")
    //bu uuıd'nin çalışma stratejisini belirlemek gerekir.
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String?="",
    val balance: BigDecimal?=BigDecimal.ZERO,
    val creationDate : LocalDateTime,
    //account verisi çekildiğinde customer verisinin gelmemesi içni fetch type lazy
    //yani ihtiyaç olduğunda ".getcustomer" gibi bi komut kullanıldığında sadece kullanılması için
    //lazy verildi.gereksiz sorgu çekilmesi olmasın diye

    //cascade ise şu custome eklendiğinde account eklensin, çıkarıldığında da çıkarılsın
    //customer-account arasında delete,update,create işlemlerini aynı anda yapması için var
    @ManyToOne(fetch = FetchType.LAZY, cascade=[CascadeType.ALL])
    @JoinColumn(name = "customer_id", nullable = false)
        val customer : Customer?,


    //mapped by ile transaction içinde ki account nesnesine gittiğini belirtmiş oluruz
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val transaction: Set<Transaction> =HashSet()

){//equals iki farklı isimde aynı parametrelere sahip nesnelerin adreslerini karşılaştırı
    constructor(customer: Customer,balance: BigDecimal,creationDate: LocalDateTime) : this(
        "",
        customer = customer,
        balance=balance,
        creationDate = creationDate
    )
    constructor() : this(
        id = null,
        balance = BigDecimal.ZERO,
        creationDate = LocalDateTime.now(),
        customer = null,
        transaction = HashSet()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (balance != other.balance) return false
        if (creationDate != other.creationDate) return false
        if (customer != other.customer) return false
        if (transaction != other.transaction) return false

        return true
    }
    //hashcode iki farklı isimde ve aynı parametrelere ait nesnelerin değerlerini karşılaştırır
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + (customer?.hashCode() ?: 0)

        return result
    }
}