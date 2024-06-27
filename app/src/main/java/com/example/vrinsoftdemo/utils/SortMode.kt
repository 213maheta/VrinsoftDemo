package com.example.vrinsoftdemo.utils

sealed class SortMode {
    data object AtoZ:SortMode()
    data object ZtoA:SortMode()
    data object Recent:SortMode()
}