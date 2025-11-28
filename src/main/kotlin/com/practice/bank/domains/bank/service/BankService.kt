package com.practice.bank.domains.bank.service

import com.practice.bank.common.logging.Logging
import com.practice.bank.common.transaction.Transactional
import org.slf4j.*
import org.springframework.stereotype.Service

@Service
class BankService(
    private val transaction: Transactional,
) {
    private val logger: Logger = LoggerFactory.getLogger(BankService::class.java)

    fun createAccount(userUlid:String) = Logging.logFor(logger){log->
        log["userUlid"]=userUlid

        transaction.run {}
    }

    fun balance(userUlid:String, accountUlid: String) = Logging.logFor(logger){log->
        log["userUlid"]=userUlid
        log["accountUlid"]=accountUlid

        transaction.run {}
    }

    fun removeAccount(userUlid:String, accountUlid: String) = Logging.logFor(logger){ log->
        log["userUlid"]=userUlid
        log["accountUlid"]=accountUlid
        
        transaction.run {}
    }
}