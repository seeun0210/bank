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
import kotlinx.serialization.builtins.serializer
import okhttp3.FormBody
import org.springframework.stereotype.Service

private const val key = "github"

@Service(key)
class GithubAuthService(
    private val config: OAuth2Config,
    private val httpClient: CallClient
): OauthServiceInterface {

    private val oAuthInfo = config.providers[key] ?: throw CustomException(ErrorCode.AUTH_CONFIG_NOT_FOUND,key)

    private val tokenURL = "https://github.com/login/oauth/access_token"
    private val userInfoURL="https://api.github.com/user"

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

        // jsonString -> json 처리
        val response: GithubTokenResponse = JsonUtil.decodeFromJson(jsonString, GithubTokenResponse.serializer())

        return response
    }

    override fun getUserInfo(accessToken: String): OAuth2UserResponse {
        val headers = mapOf("Accept" to "application/json", "Authorization" to "Bearer $accessToken")

        val jsonString = httpClient.GET(tokenURL,headers)

        val response: GithubUserResponseTemp = JsonUtil.decodeFromJson(jsonString, GithubUserResponseTemp.serializer())

        return response.toOAuth2UserResponse()
    }
}

@Serializable
data class GithubTokenResponse(
    @SerialName("access_token") override val accessToken: String,
    // expires_in
    ):OAuth2TokenResponse

@Serializable
data class GithubUserResponseTemp(
    val id: Int,
    val repos_url: String,
    val name: String,
){
    fun toOAuth2UserResponse() = GithubUserResponse(
        id = id.toString(),
        email = repos_url,
        name = name,
    )
}

@Serializable
data class GithubUserResponse(
    override val id: String,
    override val email: String?,
    override val name: String?,
):  OAuth2UserResponse