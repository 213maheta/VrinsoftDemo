package com.example.vrinsoftdemo.koinsetup

import androidx.room.Room
import com.example.vrinsoftdemo.repository.EmployeeRepository
import com.example.vrinsoftdemo.roomsetup.MyRoomDatabase
import com.example.vrinsoftdemo.viewmodels.CommonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            MyRoomDatabase::class.java,
            "EmployeeDatabase"
        ).build()
    }

    single {
        val roomDatabase = get<MyRoomDatabase>()
        roomDatabase.employeeDao()
    }

    single {
        EmployeeRepository(get())
    }

    viewModel{ CommonViewModel(get()) }
}