package com.practice.bank.domains.bank.service

import com.practice.bank.common.logging.Logging
import com.practice.bank.common.transaction.Transactional
import com.practice.bank.domains.bank.repository.BankAccountRepository
import com.practice.bank.domains.bank.repository.BankUserRepository
import com.practice.bank.types.dto.Response
import org.slf4j.*
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BankService(
    private val transaction: Transactional,
    private val bankUserRepository: BankUserRepository,
    private val bankAccountRepository: BankAccountRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(BankService::class.java)

    fun createAccount(userUlid:String):Response<String> = Logging.logFor(logger){log->
        log["userUlid"]=userUlid

        transaction.run {}
    }

    fun balance(userUlid:String, accountUlid: String):Response<BigDecimal> = Logging.logFor(logger){log->
        log["userUlid"]=userUlid
        log["accountUlid"]=accountUlid

        transaction.run {}
    }

    fun removeAccount(userUlid:String, accountUlid: String):Response<String> = Logging.logFor(logger){ log->
        log["userUlid"]=userUlid
        log["accountUlid"]=accountUlid

        transaction.run {}
    }
}