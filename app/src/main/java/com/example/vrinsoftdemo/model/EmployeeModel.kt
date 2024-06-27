package com.example.vrinsoftdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmployeeTable")
data class EmployeeModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name:String = "",
    var email:String= "",
    var phoneNum:String ,
    var dob:String= "",
    var designation:String="",
)
