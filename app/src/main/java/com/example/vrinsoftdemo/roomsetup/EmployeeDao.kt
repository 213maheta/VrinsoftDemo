package com.example.vrinsoftdemo.roomsetup

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.vrinsoftdemo.model.EmployeeModel

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM EmployeeTable")
    fun getAllEmployees(): List<EmployeeModel>

    @Insert
    fun addEmployee(employee: EmployeeModel)

    @Delete
    fun deleteEmployee(employee: EmployeeModel)

}