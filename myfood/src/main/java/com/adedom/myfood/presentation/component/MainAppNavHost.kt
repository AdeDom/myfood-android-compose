package com.adedom.myfood.presentation.component

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.adedom.authentication.presentation.component.LoginScreen
import com.adedom.authentication.presentation.component.RegisterScreen
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.authentication.presentation.view_model.RegisterViewModel
import com.adedom.connectivity.presentation.component.ConnectivityScreen
import com.adedom.food_detail.presentation.component.FoodDetailScreen
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import com.adedom.main.presentation.component.MainScreen
import com.adedom.main.presentation.view_model.HomeUiEvent
import com.adedom.main.presentation.view_model.HomeViewModel
import com.adedom.search_food.presentation.component.SearchFoodScreen
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import com.adedom.splash_screen.presentation.component.SplashScreen
import com.adedom.splash_screen.presentation.view_model.SplashScreenViewModel
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.user_profile.presentation.component.UserProfileScreen
import com.adedom.user_profile.presentation.view_model.UserProfileUiEvent
import com.adedom.user_profile.presentation.view_model.UserProfileViewModel
import com.adedom.welcome.presentation.component.WelcomeScreen
import com.adedom.welcome.presentation.view_model.WelcomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MyFood() {
    MyFoodTheme {
        ConnectivityScreen(getViewModel())
        MainAppNavHost()
    }
}

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.SplashScreen.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.SplashScreen.route) {
            val viewModel: SplashScreenViewModel = getViewModel()
            SplashScreen(
                viewModel = viewModel,
                openMainPage = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
                openWelcomePage = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        authGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = Screen.Welcome.Init.route,
        route = Screen.Welcome.route,
    ) {
        composable(Screen.Welcome.Init.route) {
            val viewModel: WelcomeViewModel = getViewModel()
            WelcomeScreen(
                viewModel = viewModel,
                openLoginPage = {
                    navController.navigate(Screen.Welcome.Login.route)
                },
                openRegisterPage = {
                    navController.navigate(Screen.Welcome.Register.route)
                },
                openMainPage = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(Screen.Welcome.Login.route) {
            val viewModel: LoginViewModel = getViewModel()
            LoginScreen(
                viewModel = viewModel,
                openMainPage = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                },
                openRegisterPage = {
                    navController.popBackStack()
                    navController.navigate(Screen.Welcome.Register.route)
                },
            )
        }
        composable(Screen.Welcome.Register.route) {
            val viewModel: RegisterViewModel = getViewModel()
            RegisterScreen(
                viewModel = viewModel,
                openLoginPage = {
                    navController.popBackStack()
                    navController.navigate(Screen.Welcome.Login.route)
                },
                openMainPage = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = Screen.Main.Init.route,
        route = Screen.Main.route,
    ) {
        composable(Screen.Main.Init.route) {
            val context = LocalContext.current
            val viewModel: HomeViewModel = getViewModel()
            MainScreen(viewModel) { uiEvent ->
                when (uiEvent) {
                    HomeUiEvent.NavLogout -> {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                    is HomeUiEvent.NavFoodDetail -> {
                        val route = Screen.Main.FoodDetail.arguments(uiEvent.foodId)
                        navController.navigate(route)
                    }
                    HomeUiEvent.NavSearchFood -> {
                        navController.navigate(Screen.Main.SearchFood.route)
                    }
                    HomeUiEvent.NavUserProfile -> {
                        navController.navigate(Screen.Main.UserProfile.route)
                    }
                    HomeUiEvent.NavInfo -> {
                        Toast.makeText(context, "NavInfo", Toast.LENGTH_SHORT).show()
                    }
                    HomeUiEvent.OnBackAlert -> {
                        Toast.makeText(context, "Tap again to exit the app", Toast.LENGTH_SHORT).show()
                    }
                    HomeUiEvent.OnBackPressed -> {
                        (context as? Activity)?.finishAffinity()
                    }
                }
            }
        }
        composable(Screen.Main.UserProfile.route) {
            val viewModel: UserProfileViewModel = getViewModel()
            UserProfileScreen(viewModel) { uiEvent ->
                when (uiEvent) {
                    UserProfileUiEvent.BackPressed -> {
                        navController.popBackStack()
                    }
                    UserProfileUiEvent.RefreshTokenExpired -> {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
        composable(Screen.Main.SearchFood.route) {
            val viewModel: SearchFoodViewModel = getViewModel()
            SearchFoodScreen(
                viewModel = viewModel,
                openFoodDetailPage = { foodId ->
                    val route = Screen.Main.FoodDetail.arguments(foodId)
                    navController.navigate(route)
                },
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }
        composable(
            route = Screen.Main.FoodDetail.route,
            arguments = Screen.Main.FoodDetail.arguments,
        ) { backStackEntry ->
            val viewModel: FoodDetailViewModel = getViewModel()
            val foodId = backStackEntry.arguments?.getInt("foodId")
            FoodDetailScreen(
                viewModel = viewModel,
                foodId = foodId,
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }
    }
}