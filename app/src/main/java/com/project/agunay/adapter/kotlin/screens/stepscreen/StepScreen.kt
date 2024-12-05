package com.project.agunay.adapter.kotlin.screens.stepscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.agunay.DarkPurple
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizTopBar

@Composable
fun StepScreen() {
    Scaffold(
        topBar = {
            WalkQuizTopBar(
                text = R.string.steps_button,
                icon = R.drawable.step
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
                StepsCard(0)
                PointsCard(0)
            }
            BottomButtons()
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
@Preview
fun StepScreenPreview() {
    StepScreen()
}