package com.practice.bank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class BankApplication

fun main(args: Array<String>) {
    runApplication<BankApplication>(*args)
}
