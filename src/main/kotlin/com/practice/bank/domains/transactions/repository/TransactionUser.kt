package com.practice.bank.domains.transactions.repository

import com.practice.bank.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionUser:JpaRepository<User,String> {
    fun findByUlid(ulid: String): User
}