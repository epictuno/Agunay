package com.project.agunay.adapter.kotlin.screens.triviascreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.DarkPurple
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIcon
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithIconComposable
import com.project.agunay.adapter.kotlin.components.WalkQuizSquareButtonWithImage
import com.project.agunay.adapter.kotlin.components.WalkQuizTopBar
import com.project.agunay.adapter.kotlin.components.invetoryElement
import com.project.agunay.adapter.kotlin.configuration.CurrentQuizz
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.domain.Question
import com.project.agunay.domain.Quizz
import com.project.agunay.domain.ShopItem
import com.project.agunay.domain.User

@Composable
fun TriviaScreen(
    navController: NavHostController,
    quizz: CurrentQuizz,
    user: CurrentUser,
    backStackEntry: NavBackStackEntry
) {

    val viewModel: TriviaScreenVM = viewModel(backStackEntry)

    viewModel.setCurrentUser(user)
    viewModel.setCurrentQuiz(quizz)
    val currentQuizz = viewModel.currentQuizz.observeAsState()
    val currentUser = viewModel.currentUser.observeAsState()
    val currentQuestion = viewModel.currentQuestion.observeAsState()
    val markedAnswers = viewModel.markedAnswers.observeAsState()

    viewModel.getQuestion()

    BodyContent(navController, currentQuestion.value, currentQuizz.value,currentUser.value, markedAnswers, viewModel)
    Log.d("QuizDebug", "Pantalla cargada")
}

@Composable
fun BodyContent(
    navController: NavHostController,
    currentQuestion: Question?,
    currentQuizz: Quizz?,
    currentUser: User?,
    markedAnswers: State<ArrayList<Boolean>?>?,
    viewModel: TriviaScreenVM?
) {
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
            TriviaInfoCard(currentQuizz, Modifier.padding(8.dp))
            QuestionCard(currentQuestion,Modifier.padding(8.dp))
            ShowInventory(currentQuestion,currentQuizz,currentUser, viewModel!!)
            AnswerButtons(question = currentQuestion, viewModel = viewModel, markedAnswers = markedAnswers?.value)
            BottomText()
        }
    }
}

@Composable
fun ShowInventory(currentQuestion: Question?, currentQuizz: Quizz?, currentUser: User?, viewModel: TriviaScreenVM, modifier: Modifier = Modifier) {
    if (currentUser != null) {
        val inventory = currentUser.inventory.entries.toList()
        var showDialog by remember { mutableStateOf(false) }
        WalkQuizSquareButtonWithIcon(
            onClick = { showDialog = true },
            icon = R.drawable.shop_object,
            text = stringResource(R.string.use_object_button)
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "Inventario")
                },
                text = {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp)
                    ) {
                        items(inventory) { entry ->
                            invetoryElement(
                                shopItem = entry.key,
                                quantity = entry.value,
                                onRightButtonClick = {
                                    showDialog = false
                                    viewModel.usarItem(entry)
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = stringResource(R.string.close_dialog))
                    }
                }
            )
        }
    }
}


@Composable
fun AnswerButtons(
    modifier: Modifier = Modifier,
    question: Question?,
    viewModel: TriviaScreenVM?,
    markedAnswers: ArrayList<Boolean>?
) {
    val buttonWidth = 350.dp
    if (question != null && viewModel != null) {
        val showQuestionAnswers = viewModel.showQuestionAnswers.observeAsState()

        viewModel.shuffleAnswers()

        Column {
            WalkQuizSquareButtonWithImage(
                onClick = { viewModel.checkAnswer(question.answers[0].isAnswer, 0) },
                image = R.drawable.option_a,
                text = question.answers[0].answerText,
                width = buttonWidth,
                isAnswer = question.answers[0].isAnswer,
                showAnswer = showQuestionAnswers.value,
                isMarked = markedAnswers?.get(0) ?: false
            )
            WalkQuizSquareButtonWithImage(
                onClick = { viewModel.checkAnswer(question.answers[1].isAnswer, 1) },
                image = R.drawable.option_b,
                text = question.answers[1].answerText,
                width = buttonWidth,
                isAnswer = question.answers[1].isAnswer,
                showAnswer = showQuestionAnswers.value,
                isMarked = markedAnswers?.get(1) ?: false
            )
            if (question.answers.size >= 3) {
                WalkQuizSquareButtonWithImage(
                    onClick = { viewModel.checkAnswer(question.answers[2].isAnswer, 2) },
                    image = R.drawable.option_c,
                    text = question.answers[2].answerText,
                    width = buttonWidth,
                    isAnswer = question.answers[2].isAnswer,
                    showAnswer = showQuestionAnswers.value,
                    isMarked = markedAnswers?.get(2) ?: false
                )
            }
            if (question.answers.size == 4) {
                WalkQuizSquareButtonWithImage(
                    onClick = { viewModel.checkAnswer(question.answers[3].isAnswer, 3) },
                    image = R.drawable.option_d,
                    text = question.answers[3].answerText,
                    width = buttonWidth,
                    isAnswer = question.answers[3].isAnswer,
                    showAnswer = showQuestionAnswers.value,
                    isMarked = markedAnswers?.get(3) ?: false
                )
            }
        }
    }
    else {
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
}

@Composable
fun QuestionCard(currentQuestion: Question?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(128.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            if (currentQuestion != null) {
                Text(
                    text = currentQuestion.questionTitle,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                )
            }
            else {
                Text(
                    text = "Texto de la pregunta",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TriviaInfoCard(currentQuizz: Quizz?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.
            fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (currentQuizz != null) {
                Text(
                    text = stringResource(
                        R.string.actual_question_number,
                        currentQuizz.answeredQuestions + 1,
                        currentQuizz.totalQuestions),
                    fontSize = 24.sp
                )
            }
            else {
                Text(
                    text = "Pregunta x de x",
                    fontSize = 24.sp
                )
            }
            if (currentQuizz != null) {
                Text(
                    text = stringResource(R.string.correct_answers, currentQuizz.correctAnswers),
                    fontSize = 24.sp
                )
            }
            else {
                Text(
                    text = "Correctas: x",
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Composable
@Preview
fun TriviaScreenPreview() {
    BodyContent(rememberNavController(), null, null, null, null,null)
}