package com.adedom.user_profile.domain.use_cases

import com.adedom.core.utils.ApiServiceException
import com.adedom.profile.providers.local.UserProfileLocalDataSource
import com.adedom.profile.providers.remote.ProfileRemoteDataSource
import com.adedom.profile.repositories.UserProfileRepository
import com.adedom.profile.repositories.UserProfileRepositoryImpl
import com.adedom.user_profile.data.providers.local.FakeUserProfileLocalDataSource
import com.google.common.truth.Truth.assertThat
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.UserProfileResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import myfood.database.UserProfileEntity
import org.junit.Before
import org.junit.Test

class FetchUserProfileUseCaseTest {

    private val profileRemoteDataSource: ProfileRemoteDataSource = mockk()
    private lateinit var userProfileLocalDataSource: UserProfileLocalDataSource
    private lateinit var userProfileRepository: UserProfileRepository
    private lateinit var useCase: FetchUserProfileUseCase

    @Before
    fun setUp() {
        userProfileLocalDataSource = FakeUserProfileLocalDataSource()
        userProfileRepository = UserProfileRepositoryImpl(
            userProfileLocalDataSource,
            profileRemoteDataSource,
            Dispatchers.IO
        )
        useCase = FetchUserProfileUseCase(userProfileRepository)
    }

    @Test
    fun `call user profile success return user profile model`() = runTest {
        userProfileRepository.saveUserProfile(
            UserProfileEntity(
                userId = "1",
                email = "2",
                name = "3",
                mobileNo = "4",
                address = "5",
                image = "6",
                status = "7",
                created = "8",
                updated = "9",
            ),
        )
        val userProfileResponse = UserProfileResponse(
            userId = "userId",
            email = "email",
            name = "name",
            mobileNo = "mobileNo",
            address = "address",
            image = "image",
            status = "status",
            created = "created",
            updated = "updated",
        )
        coEvery { profileRemoteDataSource.callUserProfile() } returns BaseResponse(result = userProfileResponse)

        useCase()

        val result = userProfileLocalDataSource.getUserProfile()
        val userProfileEntity = UserProfileEntity(
            userId = "userId",
            email = "email",
            name = "name",
            mobileNo = "mobileNo",
            address = "address",
            image = "image",
            status = "status",
            created = "created",
            updated = "updated",
        )
        assertThat(result).isEqualTo(userProfileEntity)
        coEvery { profileRemoteDataSource.callUserProfile() }
    }

    @Test(expected = ApiServiceException::class)
    fun `call user profile error return user profile is null`() = runTest {
        userProfileRepository.saveUserProfile(
            UserProfileEntity(
                userId = "1",
                email = "2",
                name = "3",
                mobileNo = "4",
                address = "5",
                image = "6",
                status = "7",
                created = "8",
                updated = "9",
            ),
        )
        val messageError = "Api error."
        val baseError = BaseError(message = messageError)
        val exception = ApiServiceException(baseError)
        coEvery { profileRemoteDataSource.callUserProfile() } throws exception

        useCase()

        val result = userProfileLocalDataSource.getUserProfile()
        assertThat(result).isNull()
        coEvery { profileRemoteDataSource.callUserProfile() }
    }
}