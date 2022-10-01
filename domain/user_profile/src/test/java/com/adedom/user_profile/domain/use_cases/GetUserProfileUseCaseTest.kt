package com.adedom.user_profile.domain.use_cases

import com.adedom.profile.repositories.UserProfileRepository
import com.adedom.user_profile.domain.models.UserProfileModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import myfood.database.UserProfileEntity
import org.junit.Before
import org.junit.Test

class GetUserProfileUseCaseTest {

    private val userProfileRepository: UserProfileRepository = mockk()
    private lateinit var useCase: GetUserProfileUseCase

    @Before
    fun setUp() {
        useCase = GetUserProfileUseCase(userProfileRepository)
    }

    @Test
    fun `get user profile has data return user profile model`() = runTest {
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
        every { userProfileRepository.getUserProfileFlow() } returns flow { emit(userProfileEntity) }

        val result = useCase()

        val userProfileModel = UserProfileModel(
            userId = "userId",
            email = "email",
            name = "name",
            mobileNo = "mobileNo",
            address = "address",
            image = "image",
        )
        assertThat(result.first()).isNotNull()
        assertThat(result.first()).isEqualTo(userProfileModel)
        every { userProfileRepository.getUserProfileFlow() }
    }

    @Test
    fun `get user profile data is null return user profile empty data`() = runTest {
        val userProfileEntity = null
        every { userProfileRepository.getUserProfileFlow() } returns flow { emit(userProfileEntity) }

        val result = useCase()

        val userProfileModel = UserProfileModel(
            userId = "",
            email = "",
            name = "",
            mobileNo = "",
            address = "",
            image = "",
        )
        assertThat(result.first()).isNotNull()
        assertThat(result.first()).isEqualTo(userProfileModel)
        every { userProfileRepository.getUserProfileFlow() }
    }
}