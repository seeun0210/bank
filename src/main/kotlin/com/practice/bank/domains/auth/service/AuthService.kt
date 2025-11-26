package com.practice.bank.domains.auth.service

import com.practice.bank.common.exception.CustomException
import com.practice.bank.common.exception.ErrorCode
import com.practice.bank.common.jwt.JwtProvider
import com.practice.bank.interfaces.OauthServiceInterface
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val oAuth2Service: Map<String, OauthServiceInterface>
    private val jwtProvider: JwtProvider
){
    fun handleAuth(state:String, code:String):String{
        val provider = state.lowercase()

        val callService = oAuth2Service[provider] ?: throw CustomException(ErrorCode.PROVIDER_NOT_FOUND)

        val accessToken = callService.getToken(code)
        val userInfo = callService.getUserInfo(accessToken.accessToken)
        val token = jwtProvider.createToken(provider, userInfo.email, userInfo.name, userInfo.id)


        //userInfo

    }
}