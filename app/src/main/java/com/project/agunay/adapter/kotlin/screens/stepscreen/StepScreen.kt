package com.project.agunay.adapter.kotlin.screens.stepscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.DarkPurple
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizTopBar
import com.project.agunay.adapter.kotlin.configuration.CurrentUser

@Composable
fun StepScreen(
    navController: NavHostController,
    user: CurrentUser,
    backStackEntry: NavBackStackEntry
) {
    val viewModel: StepScreenVM = viewModel(backStackEntry)

    val context = LocalContext.current

    viewModel.setCurrentUser(user)
    viewModel.initSensor(context)

    val stepState = viewModel.steps.observeAsState()
    val pointState = viewModel.points.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState()
    
    viewModel.setCurrentUser(user)

    BodyContent(navController, stepState, pointState, viewModel)

    if (isLoading.value!!) {
        LoadingDialog()
    }

}

@Composable
fun BodyContent(
    navController: NavHostController,
    stepState: State<Int?>?,
    pointState: State<Int?>?,
    viewModel: StepScreenVM?,
) {
    Scaffold(
        topBar = {
            WalkQuizTopBar(
                text = R.string.steps_button,
                icon = R.drawable.step,
                navController = navController,
                onBackButtonClick = {
                    viewModel?.updateUserPoints()
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkPurple),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                StepsCard(stepState?.value ?: 0)
                PointsCard(pointState?.value ?: 0)
            }
            BottomButtons(navController)
            BottomText()
        }
    }
}

@Composable
fun PointsCard(
    points: Int
) {
    Card(modifier = Modifier.padding(0.dp, 8.dp)) {
        Text(
            text = stringResource(R.string.points, points),
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp, 0.dp)
        )
    }
}

@Composable
fun StepsCard(
    steps: Int
) {
    Card(
        shape = CircleShape,
        modifier = Modifier.size(350.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(R.drawable.step_symbol),
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.size(200.dp)
            )
            Text(
                stringResource(R.string.steps_today, steps),
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun LoadingDialog() {
    AlertDialog(
        icon = {},
        title = {},
        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator()
                Text(stringResource(R.string.saving_info))
            }
        },
        onDismissRequest = {},
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
@Preview
fun StepScreenPreview() {
    BodyContent(rememberNavController(), null, null, null)
}