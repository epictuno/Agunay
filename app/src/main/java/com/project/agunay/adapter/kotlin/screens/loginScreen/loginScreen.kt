package com.project.agunay.adapter.kotlin.screens.loginScreen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agunay.ui.theme.AgunayTheme
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIcon
import com.project.agunay.adapter.kotlin.components.WalkQuizTextField
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.adapter.kotlin.navigation.AppScreens
import com.project.agunay.adapter.kotlin.screens.registerScreen.registerScreenVM

@Composable
fun LoginScreen(
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
        navController.navigate(AppScreens.MainScreen.route) // O cualquier otra lógica que quieras
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
        verticalArrangement = Arrangement.Center, // Asegura que los elementos estén centrados
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.iconoapp_principal),
            contentDescription = "Icono de la aplicación",
            //modifier = Modifier.size(120.dp) // Puedes ajustar el tamaño
        )
        Spacer(modifier = Modifier.height(32.dp)) // Espacio entre imagen y formulario

        LoginBody(viewModel = userViewModel, currentUser = currentUser)
    }
}

@Composable
fun LoginBody(viewModel: registerScreenVM, currentUser: CurrentUser) {
    var username by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
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

        Spacer(modifier = Modifier.height(16.dp))

        WalkQuizSquareButtonWithIcon(
            onClick = {

            },
            icon = R.drawable.step,
            text = stringResource(R.string.steps_button)
        )
    }
}

private fun String.isValidUsername(): Boolean {
    return this.length >= 3 // Ejemplo: El nombre de usuario debe tener al menos 3 caracteres
}

@Composable
@Preview
fun LoginScreenPreview() {
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