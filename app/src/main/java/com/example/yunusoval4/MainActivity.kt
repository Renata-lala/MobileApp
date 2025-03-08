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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
    ) {
        // Основной контент
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Поле поиска
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Поиск") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(50.dp)
            )

            // Кнопки с иконками
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = { /* Обработка сортировки */ },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFA6E0DE),
                            shape = RoundedCornerShape(90.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = "Сортировка",
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = { /* Обработка фильтров */ },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFFA6E0DE),
                            shape = RoundedCornerShape(90.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Tune,
                        contentDescription = "Фильтры",
                        tint = Color.Black
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0XFFF6DCE7),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(end = 16.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.sobaka),
                                    contentDescription = "Собака",
                                    modifier = Modifier.size(80.dp)
                                )
                                Text(
                                    text = "22.02.2025",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Шарик",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Дворняга",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Кабель",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = """"Приют "Добрые руки".""",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Подробнее",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(top = 8.dp)
                                        .clickable { navController.navigate("details") } // Переход на экран "details"
                                )
                            }
                        }

                        IconButton(
                            onClick = { /* Обработка добавления в избранное */ },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Добавить в избранное",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFC1CC), // Розовый цвет
                    shape = RoundedCornerShape(16.dp) // Закругление по всем углам
                )
                .padding(16.dp)
                .align(Alignment.BottomCenter), // Исправлено на Modifier.align
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Кнопка текущего экрана - серая
            IconButton(
                onClick = { /* Уже на этом экране */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(12.dp) // Подушка
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Объявления",
                    tint = Color.Black
                )
            }

            // Остальные кнопки - розовые
            IconButton(
                onClick = { navController.navigate("favourites") },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFC1CC), // Розовый базовый цвет
                        shape = RoundedCornerShape(12.dp) // Подушка
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Избранное",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { /* Переход на другой экран */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFC1CC), // Розовый базовый цвет
                        shape = RoundedCornerShape(12.dp) // Подушка
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.ChatBubbleOutline,
                    contentDescription = "Настройки",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { /* Переход на другой экран */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFC1CC), // Розовый базовый цвет
                        shape = RoundedCornerShape(12.dp) // Подушка
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.PersonOutline,
                    contentDescription = "Домой",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun FavouritesScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Избранное",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFC1CC),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(2) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFA6E0DE),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(end = 16.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.sobaka),
                                    contentDescription = "Собака",
                                    modifier = Modifier.size(80.dp)
                                )
                                Text(
                                    text = "22.02.2025",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Шарик",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Дворняга",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Кабель",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = """"Приют "Добрые руки".""",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Подробнее",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(top = 8.dp)
                                        .clickable { navController.navigate("details") }
                                )
                            }
                        }

                        // Кнопка-иконка в правом верхнем углу
                        IconButton(
                            onClick = {  },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Любимое",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFC1CC),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { navController.navigate("announcements") },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFC1CC),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Объявления",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { /* Уже на этом экране */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Избранное",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { /* Переход на другой экран */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFC1CC),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.ChatBubbleOutline,
                    contentDescription = "Чат",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { /* Переход на другой экран */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFC1CC),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.PersonOutline,
                    contentDescription = "Профиль",
                    tint = Color.Black
                )
            }
        }
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