package com.adedom.profile.repositories

import com.adedom.core.data.Resource2
import com.adedom.core.data.models.error.AppErrorCode
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.RefreshTokenExpiredException
import com.adedom.profile.providers.local.UserProfileLocalDataSource
import com.adedom.profile.providers.remote.ProfileRemoteDataSource
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.data.models.response.UserProfileResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import myfood.database.UserProfileEntity

class UserProfileRepositoryImpl(
    private val userProfileLocalDataSource: UserProfileLocalDataSource,
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserProfileRepository {

    override suspend fun callUserProfile(): Resource2<UserProfileResponse> {
        return withContext(ioDispatcher) {
            try {
                val userProfileResponse = profileRemoteDataSource.callUserProfile()
                val result = userProfileResponse.result
                if (result != null) {
                    Resource2.Success(result)
                } else {
                    val baseError = BaseError(code = AppErrorCode.DataIsNull.code)
                    Resource2.Error(baseError)
                }
            } catch (exception: ApiServiceException) {
                val baseError = exception.toBaseError()
                Resource2.Error(baseError)
            } catch (exception: RefreshTokenExpiredException) {
                val baseError = exception.toBaseError()
                Resource2.RefreshTokenExpired(baseError)
            }
        }
    }

    override fun getUserProfileFlow(): Flow<UserProfileEntity?> {
        return userProfileLocalDataSource.getUserProfileFlow()
    }

    override suspend fun getUserProfile(): UserProfileEntity? {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.getUserProfile()
        }
    }

    override suspend fun getImageProfile(): String? {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.getImageProfile()
        }
    }

    override suspend fun saveUserProfile(userProfile: UserProfileEntity) {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.saveUserProfile(userProfile)
        }
    }

    override suspend fun deleteUserProfile() {
        return withContext(ioDispatcher) {
            userProfileLocalDataSource.deleteUserProfile()
        }
    }
}