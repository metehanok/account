package com.folksdev.account.dto

import com.folksdev.account.model.Account

data class CustomerDto(
    val id: String?,
    val name: String?,
    val surname: String?,
    val accounts: Set<CustomerAccountDto>?
)
