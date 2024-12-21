package com.project.agunay.adapter.kotlin.screens.registerScreen

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
import androidx.compose.material.icons.materialIcon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.ui.theme.AgunayTheme
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.WalkQuizCheckbox
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIcon
import com.project.agunay.adapter.kotlin.components.WalkQuizTextField
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.adapter.kotlin.navigation.AppScreens

@Composable
fun RegisterScreen(
    navController: NavController,
    currentUser: CurrentUser,
    backStackEntry: NavBackStackEntry
) {
    val activity = LocalContext.current as Activity
    val userViewModel: registerScreenVM = viewModel(backStackEntry)

    Column(modifier = Modifier.fillMaxSize()) {
        BodyContent(navController = navController, userViewModel = userViewModel, currentUser = currentUser)
    }

    BackHandler {
        navController.navigate(AppScreens.LoginScreen.route) // O cualquier otra lógica que quieras
    }
}

@Composable
fun BodyContent(
    navController: NavController,
    userViewModel: registerScreenVM,
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
            contentDescription = "Icono de la aplicación",
        )
        Spacer(modifier = Modifier.height(32.dp))

        RegistrationScreen(viewModel = userViewModel, navController = navController,currentAppUser = currentUser)
    }
}

@Composable
fun RegistrationScreen(viewModel: registerScreenVM, navController: NavController,currentAppUser: CurrentUser) {
    var username by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var termsError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val currentUser by viewModel.currentUser.observeAsState()
    val error by viewModel.error.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    Column(modifier = Modifier.padding(16.dp)) {
        WalkQuizTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = if (!username.isValidUsername()) context.getString(R.string.username_invalid_error) else null
            },
            label = stringResource(R.string.type_user),
            errorMessage = usernameError,
            isUser = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

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
        }
        else{
        WalkQuizTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = if (!email.isValidEmail()) context.getString(R.string.email_invalid_error) else null
            },
            label = stringResource(R.string.type_email),
            errorMessage = emailError,
            isUser = false,
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

        WalkQuizCheckbox(
            checked = termsAccepted,
            onCheckedChange = {
                termsAccepted = it
                if (termsAccepted) termsError = null
            },
            label = stringResource(R.string.accept_tos)
        )

        if (termsError != null) {
            Text(
                text = termsError ?: "",
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
        }}

        Spacer(modifier = Modifier.height(16.dp))

        WalkQuizSquareButtonWithIcon(
            onClick = {
                if (username.isValidUsername() && email.isValidEmail() && password.isValidPassword() && termsAccepted) {
                    viewModel.createUser(email, password, username)
                } else {
                    if (!username.isValidUsername()) {
                        usernameError = context.getString(R.string.username_invalid_error)
                    }
                    if (!email.isValidEmail()) {
                        emailError = context.getString(R.string.email_invalid_error)
                    }
                    if (!password.isValidPassword()) {
                        passwordError = context.getString(R.string.password_invalid_error)
                    }
                    if (!termsAccepted) {
                        termsError = context.getString(R.string.terms_not_accepted_error)
                    }
                }
            },
            icon = R.drawable.account_plus,
            text = stringResource(R.string.register)
        )
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
fun RegisterScreenPreview() {
    AgunayTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val userViewModel: registerScreenVM = viewModel()
            BodyContent(
                navController = rememberNavController(),
                userViewModel = userViewModel,
                currentUser = CurrentUser(),
                modifier = Modifier
            )
        }
    }
}
