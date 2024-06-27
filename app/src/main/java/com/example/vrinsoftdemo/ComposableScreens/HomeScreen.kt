package com.example.vrinsoftdemo.ComposableScreens

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.vrinsoftdemo.model.EmployeeModel
import com.example.vrinsoftdemo.utils.Screen
import com.example.vrinsoftdemo.utils.SortMode
import com.example.vrinsoftdemo.viewmodels.CommonViewModel

@Composable
fun HomeScreen(navigateTo: (String) -> Unit, viewModel: CommonViewModel) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getAllNotes()
    }

    LaunchedEffect(key1 = viewModel.message.value) {
        if (viewModel.message.value.isNotEmpty()) {
            snackbarHostState.showSnackbar(viewModel.message.value)
            viewModel.message.value = ""
        }
    }

    val onDelete: (EmployeeModel) -> Unit = {
        viewModel.showDialog.value = true
        viewModel.selectedEmployee = it
    }

    DeleteConfirmationDialog(
        showDialog = viewModel.showDialog.value,
        onDismiss = { viewModel.showDialog.value = false },
        onConfirm = {
            viewModel.deleteEmployee(viewModel.selectedEmployee)
            viewModel.showDialog.value = false
            viewModel.message.value = "Employee Deleted"
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (viewModel.employeeList.isNotEmpty())
            {
                SearchBar(
                    {searchTect: String -> viewModel.sortByName(searchTect) },
                    {sortMode: SortMode -> viewModel.sort(sortMode)}
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                )
                {
                    if (viewModel.employeeList.isEmpty()) {
                        NoDataFoundScreen(navigateTo)
                    } else {
                        EmployeeListView(viewModel.filteredList, onDelete)
                    }
                }
            }
        },
        bottomBar = {
            Row(modifier = Modifier.height(60.dp))
            {
                Box(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .clickable {
                        navigateTo(Screen.HomeScreen.route)
                    }
                    .border(2.dp, color = Color.Red)
                )
                {
                    Text(
                        text = "Home",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Center)

                    )
                }
                Box(modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
                    .clickable {
                        navigateTo(Screen.AddEmployee.route)
                    }
                    .border(2.dp, color = Color.Red)
                )
                {
                    Text(
                        text = "Add Employee",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Center)

                    )
                }
            }
        },

        )
}

@Composable
fun EmployeeListView(employeeList: List<EmployeeModel>, onDelete: (EmployeeModel) -> Unit) {

    if (isTablet()) {
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
            items(employeeList) {
                EmployeeItem(it, onDelete)
            }
        }
    }
    else{
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(employeeList) {
                EmployeeItem(it, onDelete)
            }
        }
    }
}

@Composable
fun EmployeeItem(employee: EmployeeModel, onDelete: (EmployeeModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Text(
                    text = "Name: ${employee.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = "Email: ${employee.email}", fontSize = 16.sp)
                Text(text = "Phone: ${employee.phoneNum}", fontSize = 16.sp)
                Text(text = "DOB: ${employee.dob}", fontSize = 16.sp)
                Text(text = "Designation: ${employee.designation}", fontSize = 16.sp)
            }
            IconButton(onClick = {
                onDelete(employee)
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}


@Composable
fun DeleteConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this employee?") },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }
}

@Composable
fun NoDataFoundScreen(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text = "No Data Found", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        Button(onClick = { navigateTo(Screen.AddEmployee.route) }) {
            Text("Add Employee")
        }
    }
}

@Composable
fun isTablet(): Boolean {
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        configuration.screenWidthDp > 840
    } else {
        configuration.screenWidthDp > 600
    }
}


@Composable
fun SearchBar(
    onSearchTextChanged: (String) -> Unit,
    sort: (SortMode) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearchTextChanged(searchText)
            },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = {
                        searchText = ""
                        onSearchTextChanged(searchText)

                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
            ) {
            Button(onClick = { sort(SortMode.AtoZ)}) {
                Text(text = "AtoZ")
            }
            Button(onClick = { sort(SortMode.ZtoA)}) {
                Text(text = "ZtoA")
            }
            Button(onClick = { sort(SortMode.Recent)}) {
                Text(text = "Recent")
            }
        }
    }

}
