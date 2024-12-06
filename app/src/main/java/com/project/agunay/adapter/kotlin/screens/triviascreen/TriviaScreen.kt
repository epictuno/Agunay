package com.project.agunay.adapter.kotlin.screens.triviascreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.DarkPurple
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIcon
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithImage
import com.project.agunay.adapter.kotlin.components.WalkQuizTopBar

@Composable
fun TriviaScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            WalkQuizTopBar(
                text = R.string.trivia_button,
                icon = R.drawable.trivia,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkPurple)
        ) {
            TriviaInfoCard(Modifier.padding(8.dp))
            QuestionCard(Modifier.padding(8.dp))
            WalkQuizSquareButtonWithIcon(
                onClick = {},
                icon = R.drawable.shop_object,
                text = stringResource(R.string.use_object_button),
            )
            AnswerButtons()
            BottomText()
        }
    }
}

@Composable
fun AnswerButtons(modifier: Modifier = Modifier) {
    val buttonWidth = 350.dp
    Column {
        WalkQuizSquareButtonWithImage(
            onClick = {},
            image = R.drawable.option_a,
            text = "Opción A",
            width = buttonWidth
        )
        WalkQuizSquareButtonWithImage(
            onClick = {},
            image = R.drawable.option_b,
            text = "Opción B",
            width = buttonWidth
        )
        WalkQuizSquareButtonWithImage(
            onClick = {},
            image = R.drawable.option_c,
            text = "Opción C",
            width = buttonWidth
        )
        WalkQuizSquareButtonWithImage(
            onClick = {},
            image = R.drawable.option_d,
            text = "Opción D",
            width = buttonWidth
        )
    }
}

@Composable
fun QuestionCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = "Texto de la pregunta",
            fontSize = 48.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TriviaInfoCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.
            fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Pregunta x de x",
                fontSize = 24.sp
            )
            Text(
                text = "Correctas: x",
                fontSize = 24.sp
            )
        }
    }
}

@Composable
@Preview
fun TriviaScreenPreview() {
    TriviaScreen(rememberNavController())
}