package com.adedom.myfood.presentation

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
import com.adedom.food_detail.presentation.component.FoodDetailScreen
import com.adedom.main.presentation.component.MainScreen
import com.adedom.search_food.presentation.component.SearchFoodScreen
import com.adedom.splash_screen.presentation.component.SplashScreen
import com.adedom.user_profile.presentation.component.UserProfileScreen
import com.adedom.welcome.presentation.component.WelcomeScreen
import org.koin.androidx.compose.koinViewModel

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
            SplashScreen(
                viewModel = koinViewModel(),
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
            WelcomeScreen(
                viewModel = koinViewModel(),
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
            LoginScreen(
                viewModel = koinViewModel(),
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
            RegisterScreen(
                viewModel = koinViewModel(),
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
            MainScreen(
                viewModel = koinViewModel(),
                onLogoutClick = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Main.route) {
                            inclusive = true
                        }
                    }
                },
                openFoodDetailPage = { foodId ->
                    val route = Screen.Main.FoodDetail.arguments(foodId)
                    navController.navigate(route)
                },
                openSearchFoodPage = {
                    navController.navigate(Screen.Main.SearchFood.route)
                },
                openUserProfilePage = {
                    navController.navigate(Screen.Main.UserProfile.route)
                },
                openInfoPage = {
                    Toast.makeText(context, "Info", Toast.LENGTH_SHORT).show()
                },
                onBackPressed = {
                    (context as? Activity)?.finishAffinity()
                },
            )
        }
        composable(Screen.Main.UserProfile.route) {
            UserProfileScreen(
                viewModel = koinViewModel(),
                onBackPressed = {
                    navController.popBackStack()
                },
                refreshTokenExpired = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Main.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(Screen.Main.SearchFood.route) {
            SearchFoodScreen(
                viewModel = koinViewModel(),
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
            val foodId = backStackEntry.arguments?.getInt(Screen.Main.FOOD_ID)
            FoodDetailScreen(
                viewModel = koinViewModel(),
                foodId = foodId,
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }
    }
}