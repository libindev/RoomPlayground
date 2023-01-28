package com.example.roomplayground

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomplayground.components.AppUserListView
import com.example.roomplayground.components.addUserDialog
import com.example.roomplayground.ui.theme.RoomPlaygroundTheme
import com.example.roomplayground.viewModel.MainActivityViewModel
import com.example.roomplayground.viewModel.UserDataFetchState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomPlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainActivityViewModel = viewModel()) {
    val uiState = viewModel.viewModelState.collectAsStateWithLifecycle()
    var showAddUserPopup by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                {
                    showAddUserPopup = true
                },
                contentColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(50),
                content = {
                    Icon(Icons.Filled.Add, "")
                }

            )
        },
        content = {
            AppUserListView(uiState.value.userData)
            if (uiState.value.userDataFetchState == UserDataFetchState.LOADING) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                }
            }
            if (showAddUserPopup) {
                addUserDialog(onDismissRequest = {
                    showAddUserPopup = false
                }, onSave = { name, email, phoneNo ->
                        showAddUserPopup = false
                        viewModel.saveUserData(name, email, phoneNo)
                    })
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoomPlaygroundTheme {
        MainScreen()
    }
}
