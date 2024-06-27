package com.example.vrinsoftdemo.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vrinsoftdemo.model.EmployeeModel
import com.example.vrinsoftdemo.repository.EmployeeRepository
import com.example.vrinsoftdemo.utils.SortMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommonViewModel(val employeeRepository: EmployeeRepository):ViewModel() {


    lateinit var selectedEmployee: EmployeeModel
    val showDialog = mutableStateOf(false)
    val message = mutableStateOf("")

    val designations = listOf("Intern", "Jr. Executive", "Sr. Executive", "Team Lead", "Project Manager")

    val employeeList = mutableStateListOf<EmployeeModel>()
    val filteredList = mutableStateListOf<EmployeeModel>()


    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList = employeeRepository.getAllEmployees()
            employeeList.clear()
            employeeList.addAll(tempList)

            filteredList.clear()
            filteredList.addAll(employeeList)
        }
    }

    fun addEmployee(employee: EmployeeModel){
        val result = validateEmployeeData(employee)
        if(result.first)
        {
            viewModelScope.launch(Dispatchers.IO) {
                employeeRepository.addEmployee(employee)
                message.value = "Employee added successfully"
            }
        }
        else{
            message.value = result.second
        }
    }

    fun deleteEmployee(employee: EmployeeModel){
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.deleteEmployee(employee)
            filteredList.remove(employee)
        }
    }

    fun sortByName(searchText: String) {

        if(searchText.isEmpty())
        {
            filteredList.clear()
            filteredList.addAll(employeeList)
            return
        }

        val sortedList = employeeList.filter {
            it.name.contains(searchText, ignoreCase = true)
        }
        filteredList.clear()
        filteredList.addAll(sortedList)
    }

    fun sort(sortMode:SortMode){
        when(sortMode)
        {
            SortMode.AtoZ -> {
                filteredList.sortBy {
                    it.name
                }
            }
            SortMode.ZtoA -> {
                filteredList.sortByDescending {
                    it.name
                }
            }
            SortMode.Recent -> {
                filteredList.sortByDescending {
                    it.id
                }
            }
        }
    }

    fun sortByDesignation(){
        employeeList.sortedBy {
            it.designation
        }
    }

    fun validateEmployeeData(employeeModel: EmployeeModel):Pair<Boolean, String>
    {
        if(employeeModel.name.isEmpty()){
            return Pair(false, "Name cannot be empty")
        }

        if(employeeModel.email.isEmpty() /*|| !android.util.Patterns.EMAIL_ADDRESS.matcher(employeeModel.email).matches()*/){
            return Pair(false, "Enter valid email id")
        }

        if(employeeModel.phoneNum.isEmpty()){
            return Pair(false, "Enter valid phone number")
        }

        if(employeeModel.dob.isEmpty()){
            return Pair(false, "DOB cannot be empty")
        }

        if(employeeModel.designation.isEmpty()){
            return Pair(false, "Designation cannot be empty")
        }
        return Pair(true, "")
    }
}