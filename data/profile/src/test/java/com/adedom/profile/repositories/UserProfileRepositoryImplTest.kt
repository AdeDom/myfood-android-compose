package com.adedom.profile.repositories

import com.adedom.core.utils.ApiServiceException
import com.adedom.profile.providers.local.FakeUserProfileLocalDataSource
import com.adedom.profile.providers.local.UserProfileLocalDataSource
import com.adedom.profile.providers.remote.ProfileRemoteDataSource
import com.google.common.truth.Truth.assertThat
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.UserProfileResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import myfood.database.UserProfileEntity
import org.junit.Before
import org.junit.Test

class UserProfileRepositoryImplTest {

    private val profileRemoteDataSource: ProfileRemoteDataSource = mockk()
    private lateinit var userProfileLocalDataSource: UserProfileLocalDataSource
    private lateinit var repository: UserProfileRepository

    @Before
    fun setUp() {
        userProfileLocalDataSource = FakeUserProfileLocalDataSource()
        repository = UserProfileRepositoryImpl(
            userProfileLocalDataSource,
            profileRemoteDataSource,
        )
    }

    @Test
    fun `call user profile return success`() = runTest {
        val userProfileResponse = UserProfileResponse(
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
        val response = BaseResponse(result = userProfileResponse)
        coEvery { profileRemoteDataSource.callUserProfile() } returns response

        val result = repository.callUserProfile()

        assertThat(result).isEqualTo(userProfileResponse)
        coVerify { profileRemoteDataSource.callUserProfile() }
    }

    @Test(expected = ApiServiceException::class)
    fun `call user profile return error`() = runTest {
        val baseError = BaseError(message = "User profile is error.")
        val exception = ApiServiceException(baseError)
        coEvery { profileRemoteDataSource.callUserProfile() } throws exception

        repository.callUserProfile()

        coVerify { profileRemoteDataSource.callUserProfile() }
    }

    @Test
    fun `get user profile return data is null`() = runTest {
        val result = repository.getUserProfile()

        assertThat(result).isNull()
    }

    @Test
    fun `save user profile return data is not null`() = runTest {
        val userProfile = UserProfileEntity(
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

        repository.saveUserProfile(userProfile)

        val result = repository.getUserProfile()
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(userProfile)
    }

    @Test
    fun `save user profile return image profile is not null`() = runTest {
        val image = "image"
        val userProfile = UserProfileEntity(
            address = "address",
            created = "created",
            email = "email",
            image = image,
            mobileNo = "mobileNo",
            name = "name",
            status = "status",
            updated = "updated",
            userId = "userId",
        )

        repository.saveUserProfile(userProfile)

        val result = repository.getImageProfile()
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(image)
    }

    @Test
    fun `delete user profile return data is null`() = runTest {
        val userProfile = UserProfileEntity(
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
        repository.saveUserProfile(userProfile)

        repository.deleteUserProfile()

        val result = repository.getUserProfile()
        assertThat(result).isNull()
    }
}