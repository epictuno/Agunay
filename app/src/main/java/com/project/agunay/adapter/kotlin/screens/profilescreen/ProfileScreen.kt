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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
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
import com.project.agunay.adapter.kotlin.components.AchievementCard
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.asImageBitmap
import com.project.agunay.adapter.kotlin.components.WalkQuizTopBar
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.domain.Achievement
import com.project.agunay.domain.User
import com.project.agunay.ui.theme.PurpleGrey80
import com.project.agunay.ui.theme.bluesteelColor
import com.project.agunay.ui.theme.deepBluesteelColor

@Composable
fun ProfileScreen(
    navController: NavHostController,
    user: CurrentUser,
    backStackEntry: NavBackStackEntry
) {
    val viewModel: ProfileScreenVM = viewModel(backStackEntry)
    viewModel.setCurrentUser(user)

    val currentUser by viewModel.currentUser.observeAsState()

    BodyContent(navController, currentUser)
}

@Composable
fun BodyContent(navController: NavHostController, currentUser: User?) {
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
            ProfileCard(currentUser)
            BottomButtons(navController)
            BottomText()
        }
    }
}

@Composable
fun ProfileCard(user: User?) {
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(350.dp)
                .background(bluesteelColor)
        ) {
            if (user != null) {
                Image(
                    bitmap = if (user.profilePicture != null) {
                        user.profilePicture.asImageBitmap()
                    } else {
                        ImageBitmap.imageResource(R.drawable.default_profile_picture)
                    },
                    contentDescription = stringResource(R.string.profile_picture),
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }
            else{
                Image(
                    painter = painterResource(R.drawable.default_profile_picture),
                    contentDescription = stringResource(R.string.profile_picture),
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }
            if (user != null) {
                Text(
                    text = user.username,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(0.dp, 8.dp),
                    color = Color.White
                )
            }
            else {
                Text(
                    text = "Nombre",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(0.dp, 8.dp),
                    color = Color.Black
                )
            }
            if (user != null) {
                Text(
                    text = stringResource(R.string.points, user.points),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            else {
                Text(
                    text = stringResource(R.string.points, 0),
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            AchievementsCard(user)
        }
    }
}

@Composable
fun AchievementsCard(user: User?) {
    val fontSize = 20.sp
    Card(
        colors = CardDefaults.cardColors(
            containerColor = deepBluesteelColor
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
            if (user != null) {
                Text(
                    text = stringResource(R.string.achievements, user.achievements.size, 5),
                    fontSize = fontSize,
                    color = Color.White
                )

                AchievementList(user.achievements)
            }
            else {
                Text(
                    text = stringResource(R.string.achievements, 1, 10),
                    fontSize = fontSize,
                    color = Color.White
                )
                AchievementList(HashSet<Achievement>())
            }
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
                text = text,
                color = Color.White
            )
        }
    }
}

@Composable
fun AchievementList(
    achievementSet: Set<Achievement>
) {
    val achievementList = achievementSet.toList()

    LazyColumn {
        items(achievementList) { achievement ->
            AchievementCard(achievement)
        }
    }
}

@Composable
@Preview
fun AchievementInfoPreview() {
    AchievementInfo("Cagliostro es magordito", R.drawable.placeholder)
}

@Composable
@Preview
fun ProfileScreenPreview() {
    BodyContent(rememberNavController(), null)
}