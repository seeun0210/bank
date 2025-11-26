package com.practice.bank.domains.auth.service

import com.practice.bank.common.exception.CustomException
import com.practice.bank.common.exception.ErrorCode
import com.practice.bank.common.httpClient.CallClient
import com.practice.bank.common.json.JsonUtil
import com.practice.bank.config.OAuth2Config
import com.practice.bank.interfaces.OAuth2TokenResponse
import com.practice.bank.interfaces.OAuth2UserResponse
import com.practice.bank.interfaces.OauthServiceInterface
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.FormBody
import org.springframework.stereotype.Service

private const val key = "google"

@Service(key)
class GoogleAuthService(
    private val config: OAuth2Config,
    private val httpClient: CallClient,
): OauthServiceInterface {

    private val oAuthInfo = config.providers[key] ?: throw CustomException(ErrorCode.AUTH_CONFIG_NOT_FOUND,key)

    private val tokenURL = "https://oauth2.googleapis.com/token"
    private val userInfoURL="https://www.googleapis.com/oauth2/v2/userinfo"

    override val providerName: String = key

    override fun getToken(code:String): OAuth2TokenResponse{

        val body = FormBody.Builder()
            .add("code", code)
            .add("client_id", oAuthInfo.clientId)
            .add("client_secret", oAuthInfo.clientSecret)
            .add("redirect_uri", oAuthInfo.redirectUri)
            .add("grant_type", "authorization_code")
        .build()

        val headers = mapOf("Accept" to "application/json")

        val jsonString = httpClient.POST(tokenURL,headers, body)

        val response: GithubTokenResponse = JsonUtil.decodeFromJson(jsonString, GithubTokenResponse.serializer())

        return response
    }

    override fun getUserInfo(accessToken: String): OAuth2UserResponse {
        val headers = mapOf("Accept" to "application/json","Authorization" to "Bearer $accessToken")

        val jsonString = httpClient.GET(tokenURL,headers)

        val response: GoogleUserResponse = JsonUtil.decodeFromJson(jsonString, GoogleUserResponse.serializer())

        return response
    }
}

@Serializable
data class GoogleTokenResponse(
    @SerialName("access_token") override val accessToken: String,
):OAuth2TokenResponse

@Serializable
data class GoogleUserResponse(
    override val id: String,
    override val email: String?,
    override val name: String?,
):  OAuth2UserResponse