package com.project.agunay.adapter.kotlin.screens.loginScreen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.ui.theme.AgunayTheme
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIcon
import com.project.agunay.adapter.kotlin.components.WalkQuizTextField
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.adapter.kotlin.navigation.AppScreens
import com.project.agunay.ui.theme.LinkColor

@Composable
fun LoginScreen(
    navController: NavController,
    currentUser: CurrentUser,
    backStackEntry: NavBackStackEntry
) {
    val activity = LocalContext.current as Activity
    val userViewModel: LoginScreenVM = viewModel(backStackEntry)

    Column(modifier = Modifier.fillMaxSize()) {
        BodyContent(navController = navController, userViewModel = userViewModel, currentUser = currentUser)
    }

    BackHandler {
        activity.finishAffinity()
    }
}

@Composable
fun BodyContent(
    navController: NavController,
    userViewModel: LoginScreenVM,
    currentUser: CurrentUser,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.iconoapp_principal),
            contentDescription = "Icono de la aplicación"
        )
        Spacer(modifier = Modifier.height(32.dp))

        LoginScreenContent(viewModel = userViewModel, navController = navController, currentAppUser = currentUser)
    }
}

@Composable
fun LoginScreenContent(
    viewModel: LoginScreenVM,
    navController: NavController,
    currentAppUser: CurrentUser
) {
    var userInput by remember { mutableStateOf("") }
    var userInputError by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    val currentUser by viewModel.currentUser.observeAsState()
    val error by viewModel.error.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    Column(modifier = Modifier.padding(16.dp)) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(100.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        } else{
        WalkQuizTextField(
            value = userInput,
            onValueChange = {
                userInput = it
                userInputError = if (!userInput.isValidEmail() && !userInput.isValidUsername()) context.getString(R.string.user_invalid_error) else null
            },
            label = stringResource(R.string.type_user_or_email),
            errorMessage = userInputError,
            isUser = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        WalkQuizTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = if (!password.isValidPassword()) context.getString(R.string.password_invalid_error) else null
            },
            label = stringResource(R.string.type_password),
            errorMessage = passwordError,
            isUser = false,
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
            WalkQuizSquareButtonWithIcon(
                onClick = {
                    if ((userInput.isValidEmail() || userInput.isValidUsername()) && password.isValidPassword()) {
                        if (userInput.isValidEmail()) {
                            viewModel.loginWithEmail(userInput, password)
                        } else {
                            viewModel.loginWithUsername(userInput, password)
                        }
                    } else {
                        if (!userInput.isValidEmail() && !userInput.isValidUsername()) {
                            userInputError = context.getString(R.string.user_invalid_error)
                        }
                        if (!password.isValidPassword()) {
                            passwordError = context.getString(R.string.password_invalid_error)
                        }
                    }
                },
                icon = R.drawable.login_variant,
                text = stringResource(R.string.login_button)
            )

            Spacer(modifier = Modifier.height(16.dp))

        }


        LaunchedEffect(currentUser) {
            currentUser?.let {
                currentAppUser.setUser(currentUser!!)
                navController.navigate(route = AppScreens.MainScreen.route)
            }
        }

        LaunchedEffect(error) {
            error?.let {
                errorMessage = it
                showDialog.value = true
            }
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = stringResource(R.string.error)) },
                text = { Text(text = errorMessage) },
                confirmButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text(text = stringResource(R.string.ok))
                    }
                }
            )
        }
    }
    TextButton(onClick = {
        navController.navigate(AppScreens.RegisterScreen.route)
    }) {
        Text(text = stringResource(R.string.no_account_register),
            color = LinkColor,
            fontSize = 20.sp)
    }
}

fun String.isValidUsername(): Boolean {
    return this.length >= 3
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}

@Composable
@Preview
fun LoginScreenPreview() {
    AgunayTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val userViewModel: LoginScreenVM = viewModel()
            BodyContent(
                navController = rememberNavController(),
                userViewModel = userViewModel,
                currentUser = CurrentUser(),
                modifier = Modifier
            )
        }
    }
}