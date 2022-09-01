package com.adedom.user_profile.presentation.component

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.components.AppButton
import com.adedom.ui_components.components.AppErrorAlertDialog
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppImageNetwork
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.user_profile.R
import com.adedom.user_profile.domain.models.UserProfileModel
import com.adedom.user_profile.presentation.view_model.UserProfileUiAction
import com.adedom.user_profile.presentation.view_model.UserProfileUiEvent
import com.adedom.user_profile.presentation.view_model.UserProfileUiState
import com.adedom.user_profile.presentation.view_model.UserProfileViewModel

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel,
    onNavigate: (UserProfileUiEvent) -> Unit,
) {
    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    UserProfileContent(
        viewModel.uiState,
        viewModel.refreshTokenExpired,
        viewModel::dispatch,
    )
}

@Composable
fun UserProfileContent(
    state: UserProfileUiState,
    refreshTokenExpired: BaseError?,
    dispatch: (UserProfileUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        state.userProfile?.let { userProfile ->
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                ) {
                    AppImageNetwork(
                        image = userProfile.image,
                        modifier = Modifier
                            .size(
                                width = 100.dp,
                                height = 100.dp,
                            )
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(image = R.drawable.ic_account_gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = userProfile.name)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(image = R.drawable.ic_email_gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = userProfile.email)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(image = R.drawable.ic_location_gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = userProfile.address)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(image = R.drawable.ic_phone_gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = userProfile.mobileNo)
                    }
                }
            }
        }

        AppButton(
            text = "Back",
            backgroundColor = Color(0xFFFFD700),
            onClick = { dispatch(UserProfileUiAction.BackPressed) },
            modifier = Modifier.align(Alignment.BottomCenter),
        )

        state.error?.let {
            AppErrorAlertDialog(
                error = state.error,
                onDismiss = { dispatch(UserProfileUiAction.DismissErrorDialog) },
            )
        }

        refreshTokenExpired?.let { error ->
            AppErrorAlertDialog(
                error = error,
                onDismiss = { dispatch(UserProfileUiAction.RefreshTokenExpired) },
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun UserProfileContentPreview() {
    MyFoodTheme {
        val context = LocalContext.current
        UserProfileContent(
            state = UserProfileUiState(
                userProfile = UserProfileModel(
                    userId = "userId",
                    name = "name",
                    email = "email",
                    image = "",
                    address = "address",
                    mobileNo = "mobileNo",
                ),
//                error = BaseError(),
            ),
            refreshTokenExpired = null,
            dispatch = { action ->
                when (action) {
                    UserProfileUiAction.BackPressed -> {
                        Toast.makeText(context, "BackPressed", Toast.LENGTH_SHORT).show()
                    }
                    UserProfileUiAction.DismissErrorDialog -> {
                        Toast.makeText(context, "DismissErrorDialog", Toast.LENGTH_SHORT).show()
                    }
                    is UserProfileUiAction.RefreshTokenExpired -> {
                        Toast.makeText(context, "RefreshTokenExpired", Toast.LENGTH_SHORT).show()
                    }
                }
            },
        )
    }
}