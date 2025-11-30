package com.practice.bank.domains.transactions.model

import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal


data class DepositRequest(
    @field:NotBlank(message = "enter to account id")
    val toAccountId: String,

    @field:NotBlank(message = "enter to Ulid")
    val toUlid: String,

    @field:NotBlank(message = "enter value")
    val value: BigDecimal,
)

data class TransferRequest(
    @field:NotBlank(message = "enter from account id")
    val fromAccountId: String,

    @field:NotBlank(message = "enter to account id")
    val toAccountId: String,

    @field:NotBlank(message = "enter from Ulid")
    val fromUlid: String,

    @field:NotBlank(message = "enter value")
    val value: BigDecimal,
)