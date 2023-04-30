package com.adedom.user_profile.presentation.component

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.AnimatedBrushBox
import com.adedom.ui_components.components.AppColorButton
import com.adedom.ui_components.components.AppErrorAlertDialog
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppImage
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.ui_components.theme.RectangleMediumShape
import com.adedom.user_profile.R
import com.adedom.user_profile.domain.models.UserProfileModel
import com.adedom.user_profile.presentation.view_model.UserProfileUiEvent
import com.adedom.user_profile.presentation.view_model.UserProfileUiState
import com.adedom.user_profile.presentation.view_model.UserProfileViewModel
import com.adedom.ui_components.R as res

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel,
    onBackPressed: () -> Unit,
    refreshTokenExpired: () -> Unit,
) {
    UserProfileContent(
        viewModel.uiState,
        viewModel::onEvent,
        onBackPressed,
        refreshTokenExpired,
    )
}

@Composable
fun UserProfileContent(
    state: UserProfileUiState,
    onEvent: (UserProfileUiEvent) -> Unit,
    onBackPressed: () -> Unit,
    refreshTokenExpired: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        state.userProfile?.let { userProfile ->
            Card(
                shape = RectangleMediumShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        AppImage(
                            image = userProfile.image,
                            contentDescription = stringResource(id = res.string.cd_image_profile),
                            modifier = Modifier.fillMaxSize(),
                        )
                        AnimatedBrushBox(modifier = Modifier.fillMaxSize())
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(
                            image = painterResource(id = R.drawable.ic_account_gray),
                            contentDescription = stringResource(id = res.string.cd_icon_account),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(userProfile.name)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(
                            image = painterResource(id = R.drawable.ic_email_gray),
                            contentDescription = stringResource(id = res.string.cd_icon_email),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(userProfile.email)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(
                            image = painterResource(id = R.drawable.ic_location_gray),
                            contentDescription = stringResource(id = res.string.cd_icon_location),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(userProfile.address)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(
                            image = painterResource(id = R.drawable.ic_phone_gray),
                            contentDescription = stringResource(id = res.string.cd_icon_phone),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(userProfile.mobileNo)
                    }
                }
            }
        }

        AppColorButton(
            text = stringResource(id = res.string.str_back),
            onClick = onBackPressed,
            modifier = Modifier.align(Alignment.BottomCenter),
        )

        when (state.dialog) {
            is UserProfileUiState.Dialog.Error -> {
                AppErrorAlertDialog(
                    error = state.dialog.error,
                    onDismiss = { onEvent(UserProfileUiEvent.DismissErrorDialog) },
                )
            }
            is UserProfileUiState.Dialog.RefreshTokenExpired -> {
                AppErrorAlertDialog(
                    title = stringResource(id = res.string.str_error_token_expired),
                    error = state.dialog.error,
                    onDismiss = refreshTokenExpired,
                )
            }
            null -> {}
        }
    }
}

@Preview(
    name = "User profile content",
    group = "Feature - User profile",
    showBackground = true,
)
@Composable
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
//                dialog = UserProfileUiState.Dialog.Error(BaseError()),
//                dialog = UserProfileUiState.Dialog.RefreshTokenExpired(BaseError()),
            ),
            onEvent = { event ->
                when (event) {
                    UserProfileUiEvent.DismissErrorDialog -> {
                        Toast.makeText(context, "DismissErrorDialog", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onBackPressed = {},
            refreshTokenExpired = {},
        )
    }
}