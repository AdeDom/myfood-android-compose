package com.adedom.user_profile.presentation.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.components.*
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.ui_components.theme.RectangleMediumShape
import com.adedom.user_profile.R
import com.adedom.user_profile.domain.models.UserProfileModel
import com.adedom.user_profile.presentation.view_model.UserProfileUiEvent
import com.adedom.user_profile.presentation.view_model.UserProfileUiState
import com.adedom.user_profile.presentation.view_model.UserProfileViewModel

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel,
    onBackPressed: () -> Unit,
    refreshTokenExpired: () -> Unit,
) {
    UserProfileContent(
        viewModel.uiState,
        viewModel::dispatch,
        onBackPressed,
        refreshTokenExpired,
    )
}

@Composable
fun UserProfileContent(
    state: UserProfileUiState,
    dispatch: (UserProfileUiEvent) -> Unit,
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
                    Box(
                        modifier = Modifier
                            .size(
                                width = 100.dp,
                                height = 100.dp,
                            )
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black,
                                        ),
                                        startY = 200f,
                                    ),
                                ),
                        )
                        AppImageNetwork(
                            image = userProfile.image,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(image = R.drawable.ic_account_gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(userProfile.name)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(image = R.drawable.ic_email_gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(userProfile.email)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(image = R.drawable.ic_location_gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(userProfile.address)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AppIcon(image = R.drawable.ic_phone_gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        AppText(userProfile.mobileNo)
                    }
                }
            }
        }

        AppColorButton(
            text = "Back",
            onClick = onBackPressed,
            modifier = Modifier.align(Alignment.BottomCenter),
        )

        when (state.dialog) {
            is UserProfileUiState.Dialog.Error -> {
                AppErrorAlertDialog(
                    error = state.dialog.error,
                    onDismiss = { dispatch(UserProfileUiEvent.DismissErrorDialog) },
                )
            }
            is UserProfileUiState.Dialog.RefreshTokenExpired -> {
                AppErrorAlertDialog(
                    title = "Token expired!!!",
                    error = state.dialog.error,
                    onDismiss = refreshTokenExpired,
                )
            }
            null -> {}
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
//                dialog = UserProfileUiState.Dialog.Error(BaseError()),
//                dialog = UserProfileUiState.Dialog.RefreshTokenExpired(BaseError()),
            ),
            dispatch = { event ->
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