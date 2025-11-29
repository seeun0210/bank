package com.practice.bank.domains.bank.service

import com.github.f4b6a3.ulid.UlidCreator
import com.practice.bank.common.exception.CustomException
import com.practice.bank.common.exception.ErrorCode
import com.practice.bank.common.logging.Logging
import com.practice.bank.common.transaction.Transactional
import com.practice.bank.domains.bank.repository.BankAccountRepository
import com.practice.bank.domains.bank.repository.BankUserRepository
import com.practice.bank.types.dto.Response
import com.practice.bank.types.dto.ResponseProvider
import com.practice.bank.types.entity.Account
import org.slf4j.*
import org.springframework.stereotype.Service
import java.lang.Math.random
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class BankService(
    private val transaction: Transactional,
    private val bankUserRepository: BankUserRepository,
    private val bankAccountRepository: BankAccountRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(BankService::class.java)

    fun createAccount(userUlid:String):Response<String> = Logging.logFor(logger){log->
        log["userUlid"]=userUlid

        transaction.run {
            val user = bankUserRepository.findById(userUlid)

            val ulid = UlidCreator.getUlid().toString()
            val accountNumber = generateRandomAccountNumber()

            val account = Account(
                ulid = ulid,
                user = user,
                accountNumber = accountNumber   
            )

            try{
                bankAccountRepository.save(account)
            }catch (e: Exception){
                throw CustomException(ErrorCode.FAILED_TO_SAVE_DATA, e.message)
            }

        }

        return@logFor ResponseProvider.success("SUCCESS")
    }

    fun balance(userUlid:String, accountUlid: String):Response<BigDecimal> = Logging.logFor(logger){log->
        log["userUlid"]=userUlid
        log["accountUlid"]=accountUlid

        //TODO -> 동시성 처리 고려

        return@logFor transaction.run {
            val account = bankAccountRepository.findByUlid(accountUlid) ?: throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)

            if(account.user.ulid!=userUlid) throw CustomException(ErrorCode.MISS_MATCH_ACCOUNT_ULID_AND_USER_ULID)

            ResponseProvider.success(account.balance)
        }
    }

    fun removeAccount(userUlid:String, accountUlid: String):Response<String> = Logging.logFor(logger){ log->
        log["userUlid"]=userUlid
        log["accountUlid"]=accountUlid

        return@logFor transaction.run {
            val account = bankAccountRepository.findByUlid(accountUlid) ?: throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)

            if(account.user.ulid!=userUlid) throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)
            if(account.balance.compareTo(BigDecimal.ZERO) != 0) throw CustomException(ErrorCode.ACCOUNT_BALANCE_IS_NOT_ZERO)

            val updatedAccount = account.copy(isDeleted = true, deletedAt = LocalDateTime.now(), updatedAt = LocalDateTime.now())
            ResponseProvider.success("SUCCESS")

        }

        return@logFor ResponseProvider.success("SUCCESS")
    }

    private fun generateRandomAccountNumber():String{
        val bankCode = "003"
        val section = "12"

        val number= random().toString()
        return "$bankCode-$section-$number"
    }
}