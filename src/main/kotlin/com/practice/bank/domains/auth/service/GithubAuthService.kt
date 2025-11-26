package com.practice.bank.domains.auth.service

import com.practice.bank.common.exception.CustomException
import com.practice.bank.common.exception.ErrorCode
import com.practice.bank.config.OAuth2Config
import com.practice.bank.interfaces.OAuth2TokenResponse
import com.practice.bank.interfaces.OAuth2UserResponse
import com.practice.bank.interfaces.OauthServiceInterface
import org.springframework.stereotype.Service

private const val key = "github"

@Service(key)
class GithubAuthService(
    private val config: OAuth2Config,
): OauthServiceInterface {

    private val oAuthInfo = config.providers[key] ?: throw CustomException(ErrorCode.AUTH_CONFIG_NOT_FOUND,key)

    override val providerName: String = key

    override fun getToken(code:String): OAuth2TokenResponse{
        TODO("NOT yet implemented")
    }

    override fun getUserInfo(accessToken: String): OAuth2UserResponse {
        TODO("Not yet implemented")
    }
}