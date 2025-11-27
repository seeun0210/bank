package com.practice.bank.domains.auth.repository

import com.practice.bank.types.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface AuthUserRepository : JpaRepository<User,String>{
    fun existsByUsername(username: String): Boolean

    fun updateAccessTokenByUsername(
        @Param("username") username: String,
        @Param("accessToken") token: String
    )
}