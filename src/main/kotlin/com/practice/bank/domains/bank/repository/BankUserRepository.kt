package com.practice.bank.domains.bank.repository


import com.practice.bank.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BankUserRepository : JpaRepository<User,String> {
    fun findByUlid(ulid:String): User?
}