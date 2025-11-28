package com.practice.bank.domains.bank.repository

import com.practice.bank.types.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository : JpaRepository<Account,String> {
    fun findByUlid(ulid:String): Account?
}