package com.example.vrinsoftdemo.utils

sealed class Screen(val route: String){
    data object HomeScreen : Screen(route = "HomeScreen")
    data object AddEmployee : Screen(route = "AddEmployee")
}