package com.adedom.main.domain.use_cases

import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.Resource
import com.adedom.main.domain.repositories.AuthLogoutRepository
import com.adedom.main.domain.repositories.UserProfileRepository

class LogoutUseCase(
    private val authLogoutRepository: AuthLogoutRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke(): Resource<Unit> {
        return try {
            authLogoutRepository.setUnAuthRole()
            userProfileRepository.deleteUserProfile()
            authLogoutRepository.callLogout()
            return Resource.Success(Unit)
        } catch (exception: ApiServiceException) {
            val baseError = exception.toBaseError()
            Resource.Error(baseError)
        }
    }
}