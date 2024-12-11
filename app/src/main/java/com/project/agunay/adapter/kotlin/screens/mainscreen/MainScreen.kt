package com.project.agunay.adapter.kotlin.screens.mainscreen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.ui.theme.AgunayTheme
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIcon
import com.project.agunay.adapter.kotlin.configuration.CurrentQuizz
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.adapter.kotlin.navigation.AppScreens

@Composable
fun MainScreen(
    navController: NavController,
    user: CurrentUser,
    quizz: CurrentQuizz,
    backStackEntry: NavBackStackEntry
) {
    val activity = LocalContext.current as Activity
    val viewModel: MainScreenVM = viewModel(backStackEntry)
    viewModel.setCurrentUser(user)

    val currentUser by viewModel.currentUser.observeAsState()

    Column {
        BodyContent(navController = navController, viewModel = viewModel, quizz = quizz)
    }
    BackHandler {
        activity.finishAffinity()
    }
}
@Composable
fun BodyContent(navController: NavController, viewModel: MainScreenVM,quizz: CurrentQuizz, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.iconoapp_principal),
            contentDescription = "Icono de la aplicación"
        )
        MainScreenButtons(navController, viewModel = viewModel, quizz = quizz)
        BottomButtons(navController)
        BottomText()
    }
}

@Composable
fun MainScreenButtons(navController: NavController, viewModel: MainScreenVM, quizz: CurrentQuizz) {
    val currentQuizz by viewModel.currentQuizz.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    var triviaButtonPressed by remember { mutableStateOf(false) }
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
    Column(
        modifier = Modifier
            .padding(0.dp, 16.dp)
    ) {
        WalkQuizSquareButtonWithIcon(
            onClick = { navController.navigate(route = AppScreens.StepsScreen.route) },
            icon = R.drawable.step,
            text = stringResource(R.string.steps_button)
        )
        WalkQuizSquareButtonWithIcon(
            onClick = {
                if (!isLoading) {
                    triviaButtonPressed = true
                    if(currentQuizz==null){viewModel.setCurrentQuizz("Generic",quizz)}
                    Log.d("dafuq?", (currentQuizz!=null).toString())
                }
            },
            icon = R.drawable.trivia,
            text = stringResource(R.string.trivia_button)
        )
        WalkQuizSquareButtonWithIcon(
            onClick = { navController.navigate(route = AppScreens.ShopScreen.route) },
            icon = R.drawable.shop,
            text = stringResource(R.string.shop_button)
        )
        WalkQuizSquareButtonWithIcon(
            onClick = {},
            icon = R.drawable.settings,
            text = stringResource(R.string.settings_button)
        )
    }
    LaunchedEffect(isLoading, triviaButtonPressed, currentQuizz) {
        if (!isLoading && triviaButtonPressed && currentQuizz != null) {
            triviaButtonPressed = false
            navController.navigate(route = AppScreens.TriviaScreen.route)
        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    AgunayTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BodyContent(
                navController = rememberNavController(), MainScreenVM(), CurrentQuizz()
            )
        }
    }
}