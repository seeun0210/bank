package com.practice.bank.domains.transactions.controller

import com.practice.bank.domains.transactions.model.DepositRequest
import com.practice.bank.domains.transactions.model.DepositResponse
import com.practice.bank.domains.transactions.model.TransferRequest
import com.practice.bank.domains.transactions.model.TransferResponse
import com.practice.bank.domains.transactions.service.TransactionService
import com.practice.bank.types.dto.Response
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/transactional")
class TransactionController (
    private val transactionService: TransactionService
){
    @PostMapping("/deposit")
    fun deposit(@RequestBody(required = true) request: DepositRequest):Response<DepositResponse>{
        return transactionService.deposit(request.toUlid, request.toAccountId, request.value)
    }

    @PostMapping("/transfer")
    fun transfer(@RequestBody(required = true) request: TransferRequest):Response<TransferResponse>{
        return transactionService.transfer(request.fromUlid, request.fromAccountId, request.toAccountId, request.value)
    }
}