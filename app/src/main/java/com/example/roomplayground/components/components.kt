package com.example.roomplayground.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.roomplayground.model.UserInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    text: String,
    label: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = text,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = {
            onTextChange.invoke(it)
        }
    )
}

@Composable
fun AppUserListView(userInfoList: List<UserInfo>) {
    LazyColumn {
        itemsIndexed(userInfoList) { _, item ->
            AppUserListItem(item)
        }
    }
}

@Composable
fun AppUserListItem(item: UserInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Icon(
            modifier = Modifier
                .size(32.dp),
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = item.name,
            tint = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 3f, fill = false)
                    .padding(start = 16.dp)
            ) {
                // Title
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )

                AppHorizontalSpacer(height = 10.dp)
                Text(
                    text = item.email,
                    style = TextStyle(
                        fontSize = 14.sp,
                        letterSpacing = (0.8).sp,
                        color = Color.Gray
                    )
                )
                AppHorizontalSpacer(height = 10.dp)
                Text(
                    text = item.phone,
                    style = TextStyle(
                        fontSize = 14.sp,
                        letterSpacing = (0.8).sp,
                        color = Color.Gray
                    )
                )
                AppHorizontalSpacer(height = 10.dp)
                Divider()
            }
        }
    }
}

@Composable
fun AppButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@Composable
fun AppHorizontalSpacer(height: Dp) {
    Spacer(modifier = Modifier.size(height = height, width = 0.dp))
}

@Composable
fun AppVerticalSpacer(width: Dp) {
    Spacer(modifier = Modifier.size(width = width, height = 0.dp))
}

@Composable
fun AddUserDialog(
    onDismissRequest: () -> Unit,
    onSave: (name: String, email: String, phoneNo: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = {
            onDismissRequest.invoke()
        }
    ) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            Box(
                modifier = Modifier.padding(10.dp)
                    .background(Color.White)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AppTextField(
                        text = name,
                        label = "Enter your name",
                        keyboardType = KeyboardType.Text,
                        onTextChange = {
                            name = it
                        }
                    )
                    AppHorizontalSpacer(height = 10.dp)
                    AppTextField(
                        text = email,
                        label = "Enter your email",
                        keyboardType = KeyboardType.Email,
                        onTextChange = {
                            email = it
                        }
                    )
                    AppHorizontalSpacer(height = 10.dp)
                    AppTextField(
                        text = phoneNumber,
                        label = "Enter your Phone",
                        keyboardType = KeyboardType.Phone,
                        onTextChange = {
                            phoneNumber = it
                        }
                    )

                    AppHorizontalSpacer(height = 10.dp)
                    AppButton(text = "Save") {
                        onSave.invoke(name, email, phoneNumber)
                        // viewModel.saveUserData(name = name, email = email, phone = phoneNumber)
                    }
                }
            }
        }
    }
}
