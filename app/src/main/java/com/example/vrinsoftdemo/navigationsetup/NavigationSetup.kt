package com.example.vrinsoftdemo.navigationsetup

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vrinsoftdemo.ComposableScreens.AddEmployeeScreen
import com.example.vrinsoftdemo.ComposableScreens.HomeScreen
import com.example.vrinsoftdemo.utils.Screen
import com.example.vrinsoftdemo.viewmodels.CommonViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationSetup()
{
    val navController = rememberNavController()
    val navigateTo = {route:String -> navController.navigate(route)}
    val commonViewModel: CommonViewModel = koinViewModel()
    NavHost(navController = navController, startDestination = "HomeScreen"){
        composable(route = Screen.HomeScreen.route)
        {
            HomeScreen(navigateTo, commonViewModel)
        }

        composable(route = Screen.AddEmployee.route)
        {
            AddEmployeeScreen(navigateTo, commonViewModel)
        }
    }
}