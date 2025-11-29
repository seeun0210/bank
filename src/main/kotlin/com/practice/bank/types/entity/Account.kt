package com.practice.bank.types.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "account")
data class Account (
    @Id
    @Column(name="ulid",length=12,nullable=false)
    //val: 수정 불가능
    //const 역할
    val ulid: String,

    @ManyToOne(fetch = FetchType.LAZY)
    //fetchType에 따라서 데이터를 다 가져올 것 인지?
    //Lazy로 선언하게되면 사용할때만 데이터를 가져오게된다.
    @JoinColumn(name="user_ulid",nullable=false)
    val user: User,

    @Column(name= "balance", nullable = false, precision = 15, scale =2)
    //var: 수정 가능
    var balance: BigDecimal=BigDecimal.ZERO,

    @Column(name="account_number",length=100, nullable = false, unique = true)
    val accountNumber: String,

    @Column(name="is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name="created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name="updated_at", nullable=false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name="deleted_at", nullable = true)
    var deletedAt: LocalDateTime? = null
)