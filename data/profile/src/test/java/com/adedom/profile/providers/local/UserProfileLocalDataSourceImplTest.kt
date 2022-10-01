package com.adedom.profile.providers.local

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import myfood.database.UserProfileEntity
import org.junit.Before
import org.junit.Test

class UserProfileLocalDataSourceImplTest {

    private lateinit var dataSource: UserProfileLocalDataSource

    @Before
    fun setUp() {
        dataSource = FakeUserProfileLocalDataSource()
    }

    @Test
    fun `get user profile flow empty data return null`() = runTest {
        val result = dataSource.getUserProfileFlow()

        assertThat(result.first()).isNull()
    }

    @Test
    fun `get user profile empty data return null`() = runTest {
        val result = dataSource.getUserProfile()

        assertThat(result).isNull()
    }

    @Test
    fun `get image profile empty data return null`() = runTest {
        val result = dataSource.getImageProfile()

        assertThat(result).isNull()
    }

    @Test
    fun `save user profile return user profile flow`() = runTest {
        val userProfile = UserProfileEntity(
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

        dataSource.saveUserProfile(userProfile)

        val result = dataSource.getUserProfileFlow()
        assertThat(result).isNotNull()
        assertThat(result.first()).isEqualTo(userProfile)
    }

    @Test
    fun `save user profile return user profile`() = runTest {
        val userProfile = UserProfileEntity(
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

        dataSource.saveUserProfile(userProfile)

        val result = dataSource.getUserProfile()
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(userProfile)
    }

    @Test
    fun `get image profile has data return image profile`() = runTest {
        val image = "imageProfile.jpg"
        val userProfile = UserProfileEntity(
            userId = "userId",
            email = "email",
            name = "name",
            mobileNo = "mobileNo",
            address = "address",
            image = image,
            status = "status",
            created = "created",
            updated = "updated",
        )

        dataSource.saveUserProfile(userProfile)

        val result = dataSource.getImageProfile()
        assertThat(result).isNotNull()
        assertThat(userProfile.image).isEqualTo(image)
    }

    @Test
    fun `delete user profile return data is null`() = runTest {
        val userProfile = UserProfileEntity(
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
        dataSource.saveUserProfile(userProfile)

        dataSource.deleteUserProfile()

        val result = dataSource.getUserProfile()
        assertThat(result).isNull()
    }
}