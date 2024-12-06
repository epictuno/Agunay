package com.project.agunay.adapter.kotlin.screens.profilescreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.DarkPurple
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.WalkQuizTopBar
import com.project.agunay.domain.Achievement

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            WalkQuizTopBar(
                text = R.string.profile,
                icon = R.drawable.account,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .background(DarkPurple)
                .fillMaxSize()
        ) {
            ProfileCard()
            BottomButtons(navController)
            BottomText()
        }
    }
}

@Composable
fun ProfileCard() {
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(350.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.default_profile_picture),
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Nombre",
                fontSize = 24.sp,
                modifier = Modifier.padding(0.dp, 8.dp)
            )
            Text(
                text = stringResource(R.string.points, 0),
                fontSize = 16.sp
            )
            AchievementsCard()
        }
    }
}

@Composable
fun AchievementsCard() {
    val fontSize = 20.sp
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF9B9B9B)
        ),
        modifier = Modifier
            .width(250.dp)
            .height(300.dp)
            .padding(5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.achievements, 1, 10),
                fontSize = fontSize
            )
            AchievementList(arrayListOf())
        }
    }
}

@Composable
fun AchievementInfo(
    text: String,
    @DrawableRes icon: Int
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(230.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = stringResource(R.string.ach_icon)
            )
            Text(
                text = text
            )
        }
    }
}

@Composable
fun AchievementList(
    achievementList: List<Achievement>
) {
    LazyColumn {
        items(8) {
            AchievementInfo("Cagliostro es magordito", R.drawable.placeholder)
        }
    }
    //Implementar que los elementos AchievementInfo se creen a partir de una lista de logros
}

@Composable
@Preview
fun AchievementInfoPreview() {
    AchievementInfo("Cagliostro es magordito", R.drawable.placeholder)
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(rememberNavController())
}