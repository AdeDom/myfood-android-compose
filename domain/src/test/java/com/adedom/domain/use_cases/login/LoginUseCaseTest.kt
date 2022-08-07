package com.adedom.domain.use_cases.login

import com.adedom.data.models.error.AppErrorCode
import com.adedom.data.providers.data_store.AppDataStore
import com.adedom.data.providers.local.FakeUserProfileLocalDataSource
import com.adedom.data.providers.local.UserProfileLocalDataSource
import com.adedom.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.data.providers.remote.profile.ProfileRemoteDataSource
import com.adedom.data.repositories.auth.AuthLoginRepository
import com.adedom.data.repositories.auth.AuthLoginRepositoryImpl
import com.adedom.data.repositories.profile.UserProfileRepository
import com.adedom.data.repositories.profile.UserProfileRepositoryImpl
import com.adedom.data.utils.ApiServiceException
import com.adedom.data.utils.AuthRole
import com.adedom.data.utils.Resource
import com.adedom.domain.providers.data_store.FakeAppDataStore
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.TokenResponse
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import myfood.database.UserProfileEntity
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private val authRemoteDataSource: AuthRemoteDataSource = mockk()
    private val profileRemoteDataSource: ProfileRemoteDataSource = mockk()
    private lateinit var appDataStore: AppDataStore
    private lateinit var userProfileLocalDataSource: UserProfileLocalDataSource
    private lateinit var authLoginRepository: AuthLoginRepository
    private lateinit var userProfileRepository: UserProfileRepository
    private lateinit var useCase: LoginUseCase

    @Before
    fun setUp() {
        appDataStore = FakeAppDataStore()
        userProfileLocalDataSource = FakeUserProfileLocalDataSource()
        authLoginRepository = AuthLoginRepositoryImpl(
            appDataStore,
            authRemoteDataSource,
        )
        userProfileRepository = UserProfileRepositoryImpl(
            userProfileLocalDataSource,
            profileRemoteDataSource,
        )
        useCase = LoginUseCase(
            authLoginRepository,
            userProfileRepository,
        )
    }

    @Test
    fun `call login incorrect should be return error`() = runTest {
        val email = "email"
        val password = "password"
        val loginError = BaseError(message = "login error")
        val loginException = ApiServiceException(loginError)
        coEvery { authRemoteDataSource.callLogin(any()) } throws loginException

        val result = useCase(email, password)

        val loginResourceError = Resource.Error(loginError)
        assertThat(result).isEqualTo(loginResourceError)
        assertThat(appDataStore.getAccessToken()).isNull()
        assertThat(appDataStore.getRefreshToken()).isNull()
        assertThat(authLoginRepository.getAuthRole()).isEqualTo(AuthRole.Unknown)
        assertThat(userProfileLocalDataSource.getUserProfile()).isNull()
        coVerify { authRemoteDataSource.callLogin(any()) }
    }

    @Test
    fun `call login correct but token is null should be error`() = runTest {
        val email = "email"
        val password = "password"
        val tokenResponse = BaseResponse<TokenResponse>()
        coEvery { authRemoteDataSource.callLogin(any()) } returns tokenResponse

        val result = useCase(email, password)

        val code = AppErrorCode.TokenIsNull.code
        val tokenError = BaseError(code = code)
        val tokenResourceError = Resource.Error(tokenError)
        assertThat(result).isEqualTo(tokenResourceError)
        assertThat(appDataStore.getAccessToken()).isNull()
        assertThat(appDataStore.getRefreshToken()).isNull()
        assertThat(authLoginRepository.getAuthRole()).isEqualTo(AuthRole.Unknown)
        assertThat(userProfileLocalDataSource.getUserProfile()).isNull()
        coVerify { authRemoteDataSource.callLogin(any()) }
    }

    @Test
    fun `call login correct has token but call user info is error`() = runTest {
        val email = "email"
        val password = "password"
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        val tokenResponse = BaseResponse(
            result = TokenResponse(accessToken, refreshToken),
        )
        val userProfileError = BaseError(message = "user profile error")
        val userProfileException = ApiServiceException(userProfileError)
        coEvery { authRemoteDataSource.callLogin(any()) } returns tokenResponse
        coEvery { profileRemoteDataSource.callUserProfile() } throws userProfileException

        val result = useCase(email, password)

        val userProfileResourceError = Resource.Error(userProfileError)
        assertThat(result).isEqualTo(userProfileResourceError)
        assertThat(appDataStore.getAccessToken()).isEqualTo(accessToken)
        assertThat(appDataStore.getRefreshToken()).isEqualTo(refreshToken)
        assertThat(authLoginRepository.getAuthRole()).isEqualTo(AuthRole.Auth)
        assertThat(userProfileLocalDataSource.getUserProfile()).isNull()
        coVerify { authRemoteDataSource.callLogin(any()) }
        coVerify { profileRemoteDataSource.callUserProfile() }
    }

    @Test
    fun `call login correct then call user profile success`() = runTest {
        val email = "email"
        val password = "password"
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        val tokenResponse = BaseResponse(
            result = TokenResponse(accessToken, refreshToken),
        )
        val userProfileResponse = BaseResponse(
            result = UserProfileResponse(
                address = "address",
                created = "created",
                email = "email",
                image = "image",
                mobileNo = "mobileNo",
                name = "name",
                status = "status",
                updated = "updated",
                userId = "userId",
            )
        )
        coEvery { authRemoteDataSource.callLogin(any()) } returns tokenResponse
        coEvery { profileRemoteDataSource.callUserProfile() } returns userProfileResponse

        val result = useCase(email, password)

        val useCaseResourceSuccess = Resource.Success(Unit)
        assertThat(result).isEqualTo(useCaseResourceSuccess)
        assertThat(appDataStore.getAccessToken()).isEqualTo(accessToken)
        assertThat(appDataStore.getRefreshToken()).isEqualTo(refreshToken)
        assertThat(authLoginRepository.getAuthRole()).isEqualTo(AuthRole.Auth)
        val userProfileEntity = mapUserProfileToUserProfileEntity(
            userProfileResponse.result
        )
        assertThat(userProfileLocalDataSource.getUserProfile()).isEqualTo(userProfileEntity)
        coVerify { authRemoteDataSource.callLogin(any()) }
        coVerify { profileRemoteDataSource.callUserProfile() }
    }

    private fun mapUserProfileToUserProfileEntity(userProfile: UserProfileResponse?): UserProfileEntity {
        return UserProfileEntity(
            userProfile?.userId.orEmpty(),
            userProfile?.email.orEmpty(),
            userProfile?.name.orEmpty(),
            userProfile?.mobileNo,
            userProfile?.address,
            userProfile?.image,
            userProfile?.status.orEmpty(),
            userProfile?.created.orEmpty(),
            userProfile?.updated,
        )
    }
}