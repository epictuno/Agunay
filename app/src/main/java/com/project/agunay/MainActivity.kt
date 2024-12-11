package com.project.agunay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.project.agunay.ui.theme.AgunayTheme
import com.project.agunay.adapter.kotlin.navigation.AppNavigation

val DarkPurple = Color(0xff160129)
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgunayTheme {
                Background()
            }
        }
    }
}
@ExperimentalMaterial3Api
@Composable
fun Background(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(DarkPurple),
        color = Color.Transparent
    ) {
        AppNavigation()
    }
}

@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun GreetingPreview() {
    AgunayTheme {
        Background()
    }
}