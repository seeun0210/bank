package com.practice.bank.domains.auth.repository

import com.practice.bank.types.entity.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AuthUserRepository : JpaRepository<User,String>{
    fun existsByUsername(username: String): Boolean

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.accessToken = :token WHERE u.username = :username")
    fun updateAccessTokenByUsername(
        @Param("username") username: String,
        @Param("token") token: String
    ): Int
}