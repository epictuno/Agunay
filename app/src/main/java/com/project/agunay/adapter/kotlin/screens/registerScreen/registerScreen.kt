package com.project.agunay.adapter.kotlin.screens.registerScreen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agunay.ui.theme.AgunayTheme
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIcon
import com.project.agunay.adapter.kotlin.components.WalkQuizTextField

@Composable
fun MainScreen(navController: NavController) {
    val activity = LocalContext.current as Activity
    val userViewModel: UserViewModel = viewModel()
    Column {
        BodyContent(navController = navController, userViewModel = userViewModel)
    }
    BackHandler {
        activity.finishAffinity()
    }
}
@Composable
fun BodyContent(
    navController: NavController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.iconoapp_principal),
            contentDescription = "Icono de la aplicación"
        )
        RegistrationScreen(viewModel = userViewModel)
        BottomButtons()
        BottomText()
    }
}

@Composable
fun RegistrationScreen(viewModel: UserViewModel) {
    var username by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false)}
    var termsError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        WalkQuizTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = if(!username.isValidUsername()) context.getString(R.string.username_invalid_error) else null
            },
            label = stringResource(R.string.type_user),
            errorMessage = usernameError,
            isUser = true
        )
        WalkQuizSquareButtonWithIcon(
            onClick = {
                viewModel.createUser(
                    email = "user@example.com",
                    password = "password123",
                    username = "newusername"
                )
            },
            icon = R.drawable.step,
            text = stringResource(R.string.steps_button)
        )
    }
}

private fun String.isValidUsername(): Boolean {
    TODO("Not yet implemented")
}

@Composable
@Preview
fun MainScreenPreview() {
    AgunayTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val userViewModel: UserViewModel = viewModel()
            BodyContent(
                navController = rememberNavController(),
                userViewModel = userViewModel
            )
        }
    }
}