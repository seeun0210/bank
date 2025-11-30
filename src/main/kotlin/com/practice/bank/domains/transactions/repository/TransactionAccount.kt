package com.practice.bank.domains.transactions.repository

import com.practice.bank.types.entity.Account
import com.practice.bank.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionAccount :JpaRepository<Account, String> {
    fun findByUlidAndUser(ulid: String, user: User): Account?
    fun findByUlid(accountUlid: String): Account?
}