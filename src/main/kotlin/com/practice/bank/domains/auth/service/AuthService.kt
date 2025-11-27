package com.practice.bank.domains.auth.service

import com.practice.bank.common.exception.CustomException
import com.practice.bank.common.exception.ErrorCode
import com.practice.bank.common.jwt.JwtProvider
import com.practice.bank.common.logging.Logging
import com.practice.bank.interfaces.OauthServiceInterface
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val oAuth2Service: Map<String, OauthServiceInterface>,
    private val jwtProvider: JwtProvider,
){
    private val logger: Logger = Logging.getLogger(AuthService::class.java)
    
    @Transactional
    fun handleAuth(state:String, code:String):String=Logging.logFor(logger){
        log->
        val provider = state.lowercase()
        log["provider"]=provider

        val callService = oAuth2Service[provider] ?: throw CustomException(ErrorCode.PROVIDER_NOT_FOUND)

        val accessToken = callService.getToken(code)
        val userInfo = callService.getUserInfo(accessToken.accessToken)
        val token = jwtProvider.createToken(provider, userInfo.email, userInfo.name, userInfo.id)


        //userInfo

    }
}