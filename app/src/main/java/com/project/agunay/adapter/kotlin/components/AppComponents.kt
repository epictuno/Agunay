package com.project.agunay.adapter.kotlin.components

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.navigation.AppScreens
import com.project.agunay.domain.Achievement
import com.project.agunay.domain.ShopItem
import com.project.agunay.ui.theme.ButtonColor
import com.project.agunay.ui.theme.ButtonTextColor
import com.project.agunay.ui.theme.DarkGreen
import com.project.agunay.ui.theme.DarkGrey
import com.project.agunay.ui.theme.LightGrey
import com.project.agunay.ui.theme.Purple40
import com.project.agunay.ui.theme.PurpleGrey80
import com.project.agunay.ui.theme.SaturatedGreen

@Composable
fun WalkQuizTransparentButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(4.dp)),
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Composable
fun GradientCircularProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    colors: List<Color>,
    trackColor: Color,
    strokeWidth: Float = 8f
) {
    Canvas(modifier = modifier.size(100.dp)) {
        val diameter = size.minDimension
        val radius = diameter / 2
        val top = (size.height - diameter) / 2
        val startAngle = -90f
        val sweepAngle = 360 * progress

        val gradient = Brush.sweepGradient(
            colors = colors,
            center = Offset(radius, radius)
        )

        drawArc(
            color = trackColor,
            startAngle = startAngle,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(top, top),
            size = Size(diameter, diameter),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        drawArc(
            brush = gradient,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(top, top),
            size = Size(diameter, diameter),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

private fun launchAppOrOpenUrl(context: Context, packageName: String, url: String) {
    try {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            context.startActivity(intent)
        } else {
            val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(fallbackIntent)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Composable
fun ErrorDialog(
    showDialog: MutableState<Boolean>,
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                // If you want to disable that functionality, simply use an empty onDismissRequest.
                onDismiss()
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.alertdialog_confirmbutton))
                }
            }
        )
    }
}

@Composable
fun WalkQuizButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    whiteBorder: Boolean = false
){
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        border = if(whiteBorder) BorderStroke(2.dp, Color.White) else null,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
    ) {
        Text(text = text,
            color = Color.White
        )
    }
}

@Composable
fun WalkQuizTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    isUser: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Default
    )
){
    var passwordVisibility by remember { mutableStateOf(!isPassword) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        modifier = Modifier
            .background(Color.Transparent)
            .composed { modifier },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon =
        if (isPassword) {
            @Composable { IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle password visibility"
                )
            } }
        } else if(isEmail){
            @Composable { Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Icono de email"
            ) }
        } else if(isUser){
            @Composable { Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Icono de email"
            ) }
        } else if(errorMessage != null){
            @Composable { Icon(Icons.Filled.Error,"error", tint = MaterialTheme.colorScheme.error) }
        } else null,
        isError = (errorMessage != null),
        supportingText = {
            if(errorMessage != null){
                Text(
                    text = errorMessage ?: "",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red
                )
            }
        },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun WalkQuizClickableText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    fontSize: TextUnit,
    fontWeight: FontWeight? = null
) {
    ClickableText(
        text = AnnotatedString(text),
        onClick = { onClick() },
        modifier = modifier,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    )
}

@Composable
fun WalkQuizCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
){
    WalkQuizLabelledCheckbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(checkmarkColor = Color.White),
        label = label,
        modifier = modifier
    )
}

@Composable
fun WalkQuizLabelledCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    labelColor: Color = Color.White,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    Row(
        modifier = modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = colors
        )
        Text(label, color = labelColor)
    }
}

@Composable
fun WalkQuizProfilePicture(
    userProfileImage: Painter = painterResource(id = R.drawable.default_profile_picture),
    isChangeable: Boolean = false,
    onUploadImageClick: () -> Unit = {},
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val imageModifier = Modifier
        .size(150.dp)
        .clip(CircleShape)
        .border(2.dp, Color.Gray, CircleShape)
        .clickable { onClick() }
    val iconSize = 22.dp
    val offsetInPx = LocalDensity.current.run { (iconSize / 2).roundToPx() }

    Box(modifier = modifier) {
        Image(
            painter = userProfileImage,
            contentDescription = "Imagen de perfil",
            modifier = imageModifier
        )
        if(isChangeable){
            IconButton(
                onClick = onUploadImageClick,
                modifier = Modifier
                    .size(35.dp)
                    .offset {
                        IntOffset(x = +offsetInPx - 20, y = +offsetInPx - 20)
                    }
                    .clip(CircleShape)
                    .background(Color.Black)
                    .size(iconSize)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Subir foto de perfil",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun ExperienceBar(
    xpPercentage: Float,
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        modifier = modifier
            .height(25.dp)
        ,
        color = SaturatedGreen,
        trackColor = Color.Black,
        progress = xpPercentage
    )
}

@Composable
fun ChatBox(onSend: (String) -> Unit, modifier: Modifier) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier = modifier.padding(16.dp)) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            placeholder = {
                Text(text = "Type something")
            }
        )
        IconButton(onClick = { onSend(chatBoxValue.text) },
            modifier = Modifier
                .clip(CircleShape)
                .background(color = DarkGreen)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Send",
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ShopItemDisplay(
    name: String,
    price: Int,
    image: Painter,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .width(120.dp)
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        color = LightGrey,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(DarkGrey)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = image,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(70.dp)
                )
            }
            Text(
                text = "$price",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(LightGrey)
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}

@Composable
fun AchievementCard(
    achievement: Achievement,
    modifier: Modifier = Modifier
){
    Log.d("achiCard", "logro: " + achievement.id+ " "+ achievement.picture)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(bitmap = if (achievement.picture != null) {
            achievement.picture.asImageBitmap()
        } else {
            ImageBitmap.imageResource(R.drawable.placeholder)
        },
            contentDescription = "Achievement image",
            modifier = Modifier.size(60.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalAlignment = Alignment.Start){
            Text(text = achievement.title, color = Color.White, fontSize = 20.sp)
            Text(text = achievement.description, color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun UserItemList(profilePicture: Painter, username: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Gray)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        //horizontalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = profilePicture,
            contentDescription = "Profile picture"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = username, fontSize = 30.sp,  color= Color.White)
    }
}

@Composable
fun AchievementDialog(achievementImage: Painter, title: String, description: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier
                    .padding(32.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = Color(0xFF000000)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = achievementImage,
                        contentDescription = "Achievement Icon",
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = description,
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )
                WalkQuizButton(
                        onClick = onDismiss,
                        text = "OK",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun WalkQuizSquareButtonWithIcon(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    text: String,
    contentDescription: String = "",
    width: Dp = 256.dp
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor ),
        modifier = Modifier
            .width(width)
            .padding(0.dp, 8.dp)
    ) {
        Icon(
            painterResource(icon),
            contentDescription = contentDescription,
            tint = Color.Black,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = text,
            modifier = Modifier.padding(4.dp),
            fontSize = 24.sp,
            color = ButtonTextColor
        )
    }
}

@Composable
fun WalkQuizSquareButtonWithImage(
    onClick: () -> Unit,
    @DrawableRes image: Int,
    text: String,
    contentDescription: String = "",
    width: Dp = 256.dp,
    isAnswer: Boolean = false,
    showAnswer: Boolean? = false,
    isMarked: Boolean = false
) {
    val buttonColor: Color
    if (showAnswer == true) {
        if (isAnswer) {
            buttonColor = Color.Green
        }
        else {
            buttonColor = Color.Red
        }
    }
    else {
        if (isMarked) {
            buttonColor = Color.Yellow
        }
        else {
            buttonColor = LightGrey
        }
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        modifier = Modifier
            .width(width)
            .padding(0.dp, 8.dp)
    ) {
        Image(
            painterResource(image),
            contentDescription = contentDescription,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = text,
            modifier = Modifier.padding(4.dp),
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
fun WalkQuizRoundButton(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(containerColor = ButtonColor),
        modifier = Modifier.size(64.dp)
    ) {
        Icon(
            painterResource(icon),
            contentDescription = contentDescription,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun BottomButtons(navController: NavController) {
    val openInfoDialog = remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        WalkQuizRoundButton(
            onClick = { navController.navigate(AppScreens.ProfileScreen.route) },
            icon = R.drawable.account,
            contentDescription = stringResource(R.string.profile_button),
        )
        WalkQuizRoundButton(
            onClick = { openInfoDialog.value = true },
            icon = R.drawable.info,
            contentDescription = stringResource(R.string.info_button)
        )
    }
    when {
        openInfoDialog.value -> {
            InfoDialog(
                onDismiss = { openInfoDialog.value = false }
            )
        }
    }
}

@Composable
fun BottomText() {
    Text(
        text = stringResource(R.string.bottom_text),
        color = Color.White
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkQuizTopBar(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    navController: NavHostController,
    onBackButtonClick: () -> Unit = { navController.navigateUp() }
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(

            containerColor = Color(0xFF4682B4),
            titleContentColor = Color.Black
        ),
        title = {
            Row(

                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = stringResource(text),
                    fontSize = 32.sp
                )
            }
        },
        navigationIcon = {
            WalkQuizRoundButton(
                onClick = onBackButtonClick,
                icon = R.drawable.left_arrow,
                contentDescription = stringResource(R.string.back_button)
            )
        }
    )
}

@Composable
fun ShopElement(
    shopItem: ShopItem,
    onRightButtonClick: () -> Unit,
) {
    val fontSize = 22.sp
    val fontSizeButton = 16.sp
    var showDialog by remember { mutableStateOf(false) }


    Card {
        Column(
            modifier = Modifier
                .width(320.dp)
                .background(PurpleGrey80),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (shopItem.image != null) {
                Image(
                    bitmap = shopItem.image.asImageBitmap(),
                    contentDescription = shopItem.name,
                    modifier = Modifier.size(64.dp)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.placeholder),
                    contentDescription = shopItem.name,
                    modifier = Modifier.size(64.dp)
                )
            }
            Text(
                text = shopItem.name,
                fontSize = fontSize,
                color = ButtonTextColor
            )
            Row {
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .padding(1.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
                        contentDescription = "details icon"
                    )

                    Text(stringResource(R.string.shop_element_details), fontSize = fontSize)
                }
                Button(
                    onClick = onRightButtonClick,
                ) {
                    Icon(
                        painterResource(R.drawable.cash_register),
                        contentDescription = "buy icon"
                    )
                    Text(" Comprar", fontSize = fontSize)
                }
            }
        }

    }
    if (showDialog) {
        detailsDialog(
            shopItem = shopItem,
            onDismiss = { showDialog = false }
        )
    }
}

fun ByteArray.asImageBitmap(): ImageBitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size).asImageBitmap()
}

@Composable
fun InfoDialog(
    onDismiss: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                painterResource(R.drawable.info),
                contentDescription = "Example Icon"
            )
        },
        title = {
            Text("Información de WalkQuiz")
        },
        text = {
            Column {
                Text(stringResource(R.string.info_text_1))
                Text(stringResource(R.string.info_text_2))
                Text(stringResource(R.string.info_text_3))
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.close_dialog))
            }
        }
    )

}

@Composable
fun detailsDialog(
    shopItem: ShopItem,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = shopItem.name)
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (shopItem.image != null) {
                    Image(
                        bitmap = shopItem.image.asImageBitmap(),
                        contentDescription = shopItem.name,
                        modifier = Modifier.size(128.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.placeholder),
                        contentDescription = shopItem.name,
                        modifier = Modifier.size(128.dp)
                    )
                }
                Text(text = shopItem.description)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.close_dialog))
            }
        }
    )
}