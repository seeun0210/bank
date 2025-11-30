package com.practice.bank.domains.transactions.service

import com.practice.bank.common.cache.RedisClient
import com.practice.bank.common.cache.RedisKeyProvider
import com.practice.bank.common.exception.CustomException
import com.practice.bank.common.exception.ErrorCode
import com.practice.bank.common.logging.Logging
import com.practice.bank.common.transaction.Transactional
import com.practice.bank.domains.transactions.model.DepositResponse
import com.practice.bank.domains.transactions.model.TransferResponse
import com.practice.bank.domains.transactions.repository.TransactionAccount
import com.practice.bank.domains.transactions.repository.TransactionUser
import com.practice.bank.types.dto.Response
import com.practice.bank.types.dto.ResponseProvider
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransactionService (
    private val transactionUser: TransactionUser,
    private val transactionAccount: TransactionAccount,
    private val redisClient: RedisClient,
    private val transactional: Transactional,
) {
    private val logger: Logger = Logging.getLogger(TransactionService::class.java)

    fun deposit(userUlid:String, accountID:String, value: BigDecimal):Response<DepositResponse>
    =Logging.logFor(logger){
        it
        it["userUlid"] = userUlid
        it["accountID"] = accountID
        it["value"] = value

        val key = RedisKeyProvider.bankMutexKey(userUlid,accountID)

        return@logFor redisClient.invokeWithMutex(key){
            //동시성 처리 할 로직
            return@invokeWithMutex transactional.run{
                val user = transactionUser.findByUlid(userUlid)

                val account = transactionAccount.findByUlidAndUser(accountID, user)?:throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)

                account.balance = account.balance.add(value)
                account.updatedAt = LocalDateTime.now()
                transactionAccount.save(account)

                ResponseProvider.success(DepositResponse(afterBalance=account.balance))
            }
        }
    }

    fun transfer(fromUlid:String, fromAccountId:String, toAccountId:String, value: BigDecimal): Response<TransferResponse>
    =Logging.logFor(logger){it
        it["fromUlid"] = fromUlid
        it["fromAccountId"] = fromAccountId
        it["toUlid"] = toAccountId
        it["value"] = value

        val key = RedisKeyProvider.bankMutexKey(fromUlid,fromAccountId)

        return@logFor redisClient.invokeWithMutex(key){
            return@invokeWithMutex transactional.run{
                val fromAccount = transactionAccount.findByUlid(fromAccountId)
                    ?:throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)

                val toAccount = transactionAccount.findByUlid(toAccountId)
                    ?:throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)

                if(fromAccount.user.ulid != fromUlid){

                }
                else if (fromAccount.balance < value) {

                }
                else if (value <= BigDecimal.ZERO){

                }

                fromAccount.balance = fromAccount.balance.subtract(value)
                toAccount.balance = toAccount.balance.add(value)

                transactionAccount.save(fromAccount)
                transactionAccount.save(fromAccount)

                ResponseProvider.success(TransferResponse(afterFromBalance=fromAccount.balance, afterToBalance=toAccount.balance))
            }
        }
    }
}