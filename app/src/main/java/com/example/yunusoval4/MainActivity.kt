package com.example.yunusoval4

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("announcements") { AnnouncementsScreen(navController) }
                    composable("favourites") { FavouritesScreen(navController) }
                    composable("details") { DetailsScreen(navController) }
                    composable("chat") { ChatScreen(navController) }
                }
            }
        }
    }
}


@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA6E0DE))
    ) {

        // Левый верхний угол
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.lapka),
                contentDescription = "Первое изображение",
                modifier = Modifier.size(50.dp, 50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.lapkaside),
                contentDescription = "Второе изображение",
                modifier = Modifier
                    .size(50.dp, 50.dp)
                    .offset(x = (20).dp)
            )
            Text(
                text = "Привет!",
                fontSize = 20.sp, // Больший шрифт
                color = Color.White
            )
            Text(
                text = "Добро пожаловать в Найденыш",
                fontSize = 14.sp, // Меньший шрифт
                color = Color.White
            )
        }

        // Кошка
        Image(
            painter = painterResource(id = R.drawable.koshka),
            contentDescription = "Третье изображение",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(y = (-50).dp)
                .padding(end = 16.dp)
                .size(150.dp, 150.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color(0xFFF5F5DC),
                    shape = RoundedCornerShape(
                        topStart = 32.dp,    // Верхний левый угол
                        topEnd = 0.dp,       // Верхний правый угол
                        bottomStart = 0.dp,  // Нижний левый угол
                        bottomEnd = 0.dp     // Нижний правый угол
                    )
                )
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Вход в систему",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFC1CC),
                modifier = Modifier
                    .align(Alignment.Start) // Выравнивание по левой границе
                    .padding(bottom = 8.dp)
            )
            // Поле ввода email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Электронная почта") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(50.dp) // Овальная форма
            )

            // Поле ввода пароля
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(50.dp), // Овальная форма
                visualTransformation = PasswordVisualTransformation()
            )

            // Ряд с Checkbox и "Забыли пароль"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                    Text(
                        text = "Запомнить меня",
                        color = Color(0xFFFFC1CC))
                }
                Text(
                    text = "Забыли пароль?",
                    color = Color(0xFFFFC1CC),
                    modifier = Modifier.clickable { /* Обработка клика */ }
                )
            }

            // Кнопка "Вход"
            Button(
                onClick = { navController.navigate("announcements") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC1CC))
            ) {
                Text("Вход")
            }

            // Ссылка "Нет аккаунта"
            Text(
                text = "Нет аккаунта? Зарегистрироваться",
                color = Color(0xFFFFC1CC),
                modifier = Modifier
                    .clickable { /* Обработка клика */ }
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun AnnouncementsScreen( navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Экран объявлений")
    }
}

@Composable
fun FavouritesScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Экран c избранными")
    }
}

@Composable
fun DetailsScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Экран с детальной информацией")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Экран с чатом")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun AnnouncementsScreenPreview() {
    MaterialTheme {
        AnnouncementsScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritesScreenPreview() {
    MaterialTheme {
        FavouritesScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    MaterialTheme {
        DetailsScreen(navController = rememberNavController())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    MaterialTheme {
        ChatScreen(navController = rememberNavController())
    }
}