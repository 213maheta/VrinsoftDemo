package com.example.vrinsoftdemo.repository

import com.example.vrinsoftdemo.model.EmployeeModel
import com.example.vrinsoftdemo.roomsetup.EmployeeDao

class EmployeeRepository(val employeeDao: EmployeeDao) {

    suspend fun getAllEmployees(): List<EmployeeModel> = employeeDao.getAllEmployees()

    suspend fun addEmployee(employee: EmployeeModel) {
        employeeDao.addEmployee(employee)
    }

    suspend fun deleteEmployee(employee: EmployeeModel) {
        employeeDao.deleteEmployee(employee)
    }

}