package com.practice.bank.domains.bank.controller

import com.practice.bank.domains.bank.service.BankService
import com.practice.bank.types.dto.Response
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/bank")
class BankController (
    private val bankService: BankService
){
    @PostMapping("/account/{ulid}")
    fun createAccount(
        @PathVariable("ulid", required = true) ulid: String,
    ):Response<String>{
        return bankService.createAccount(ulid)
    }

    @GetMapping("balance/{userUlid}/account/{accountUlid}")
    fun balance(@PathVariable("accountUlid", required = true) accountUlid: String,
                @PathVariable("userUlid", required = true) userUlid: String): Response<BigDecimal>{
        return bankService.balance(accountUlid, userUlid)
    }

    @DeleteMapping("user/{userUlid}/account/{accountUlid}")
    fun removeAccount(@PathVariable("userUlid", required = true) userUlid: String,
                      @PathVariable("accountUlid", required = true) accountUlid: String): Response<String>{
        return bankService.removeAccount(userUlid,accountUlid)
    }
}