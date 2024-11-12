package com.folksdev.account.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class CreateAccountRequest(
    @field:NotBlank
    val customerId:String,
    //stringin boş olmaması için not blank kullanıldı
    @field:Min(0)
    val initialCredit :BigDecimal
)
