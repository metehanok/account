package com.folksdev.account.model

import jakarta.persistence.*
//import lombok.NoArgsConstructor
import org.hibernate.annotations.GenericGenerator

@Entity
//@NoArgsConstructor//boş constructor ekler
data class Customer(
    @Id
    //her yeni kayıtta otomatik ıd oluşutrulur UUID sayesinde
    @GeneratedValue(generator = "UUID")
    //bu uuıd'nin çalışma stratejisini belirlemek gerekir.
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String?,

    val name: String?,
    val surname: String?,

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    val accounts: Set<Account>?


){//equals ve hashcode metodları

    constructor(name:String,surname: String?):this("",name,surname,HashSet())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (accounts != other.accounts) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (surname?.hashCode() ?: 0)
        return result
    }
}
