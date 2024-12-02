package com.project.agunay.screens.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIcon

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
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
        MainScreenButtons()
        BottomButtons()
        BottomText()
    }
}

@Composable
fun MainScreenButtons() {
    Column(
        modifier = Modifier
            .padding(0.dp, 16.dp)
    ) {
        WalkQuizSquareButtonWithIcon(
            onClick = {},
            icon = R.drawable.step,
            text = stringResource(R.string.steps_button)
        )
        WalkQuizSquareButtonWithIcon(
            onClick = {},
            icon = R.drawable.trivia,
            text = stringResource(R.string.trivia_button)
        )
        WalkQuizSquareButtonWithIcon(
            onClick = {},
            icon = R.drawable.shop,
            text = stringResource(R.string.shop_button)
        )
        WalkQuizSquareButtonWithIcon(
            onClick = {},
            icon = R.drawable.settings,
            text = stringResource(R.string.settings_button)
        )
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen(Modifier)
}