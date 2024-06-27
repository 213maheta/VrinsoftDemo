package com.example.vrinsoftdemo.roomsetup

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vrinsoftdemo.model.EmployeeModel

@Database(entities = [EmployeeModel::class], version = 1)
abstract class MyRoomDatabase:RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}