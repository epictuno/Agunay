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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButton

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
        WalkQuizSquareButton(
            onClick = {},
            icon = R.drawable.step,
            text = R.string.steps_button
        )
        WalkQuizSquareButton(
            onClick = {},
            icon = R.drawable.trivia,
            text = R.string.trivia_button
        )
        WalkQuizSquareButton(
            onClick = {},
            icon = R.drawable.shop,
            text = R.string.shop_button
        )
        WalkQuizSquareButton(
            onClick = {},
            icon = R.drawable.settings,
            text = R.string.settings_button
        )
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen(Modifier)
}