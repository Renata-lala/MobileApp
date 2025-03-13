package com.example.yunusoval4

import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.time.format.DateTimeFormatter
import java.time.LocalTime as LocalTime1


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


// Модель состояния UI для LoginScreen
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false
)

// ViewModel для LoginScreen
class LoginViewModel() : ViewModel(), Parcelable {
    var uiState by mutableStateOf(LoginUiState())
        private set

    constructor(parcel: Parcel) : this() {
    }

    fun updateEmail(newEmail: String) {
        uiState = uiState.copy(email = newEmail, emailError = validateEmail(newEmail))
    }

    fun updatePassword(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordError = validatePassword(newPassword))
    }

    fun updateRememberMe(newValue: Boolean) {
        uiState = uiState.copy(rememberMe = newValue)
    }

    fun login(onLoginSuccess: () -> Unit) {
        val emailValid = validateEmail(uiState.email)
        val passwordValid = validatePassword(uiState.password)

        uiState = uiState.copy(
            emailError = emailValid,
            passwordError = passwordValid,
            isLoading = true
        )

        if (emailValid == null && passwordValid == null) {
            uiState = uiState.copy(isLoading = false, loginSuccess = true)
            onLoginSuccess()
        } else {
            uiState = uiState.copy(isLoading = false)
        }
    }

    private fun validateEmail(email: String): String? {
        return if (email.isBlank()) "Поле не может быть пустым"
        else if (!email.contains("@") || !email.contains(".")) "Неверный формат email"
        else null
    }

    private fun validatePassword(password: String): String? {
        return if (password.isBlank()) "Поле не может быть пустым"
        else if (password.length < 6) "Пароль должен быть не менее 6 символов"
        else null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginViewModel> {
        override fun createFromParcel(parcel: Parcel): LoginViewModel {
            return LoginViewModel(parcel)
        }

        override fun newArray(size: Int): Array<LoginViewModel?> {
            return arrayOfNulls(size)
        }
    }
}


@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val uiState = viewModel.uiState
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
                value = uiState.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Электронная почта") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(50.dp), // Овальная форма
                supportingText = { if (uiState.emailError != null) Text(uiState.emailError!!) }
            )

            // Поле ввода пароля
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Пароль") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(50.dp), // Овальная форма
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.passwordError != null,
                supportingText = { if (uiState.passwordError != null) Text(uiState.passwordError!!)}
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
                        checked = uiState.rememberMe,
                        onCheckedChange = { viewModel.updateRememberMe(it) }
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
                onClick = { viewModel.login { navController.navigate("announcements") } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC1CC)),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Вход")
                }
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() }, // Кнопка возврата
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black
                )
            }

            Row {
                IconButton(


                    onClick = { /* Обработка избранного */ },
                    modifier = Modifier.size(48.dp) // Увеличенный размер
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Избранное",
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = { /* Обработка поделиться */ },
                    modifier = Modifier.size(48.dp) // Увеличенный размер
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Поделиться",
                        tint = Color.Black
                    )
                }
            }

        }
        Image(
            painter = painterResource(id = R.drawable.sobaka),
            contentDescription = "Собака",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Шарик",
                fontSize = 24.sp,
                color = Color.Black
            )
            Text(
                text = "Дворняга",
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "6 месяцев",
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "Кабель",
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = """"Приют "Добрые руки".""",
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        // Панель с заголовком и текстом
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0XFFF6DCE7),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Описание",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat..",
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        // Кнопка "Написать в приют"
        Button(
            onClick = { navController.navigate("chat") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA6E0DE)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(50.dp),

            ) {
            Text(
                text = "Написать в приют",
                color = Color.Black
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(navController: NavHostController) {
// Состояние для сообщений и ввода
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(
        Triple("Вы", "Добрый день! Объявление ещё актульно?", "10:00"),
        Triple("Приют", "Добрый день, да.", "10:02")
    ) }
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC))
    ) {
        // Верхняя панель с фото и именем
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFFA6E0DE)) // Мятный цвет
                .clickable { navController.popBackStack() } // Возврат на DetailsScreen
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.sobaka),
                contentDescription = "Собака",
                modifier = Modifier.size(48.dp) // Квадратное изображение
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Шарик",
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(messages) { message ->
                val (sender, text, time) = message
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = if (sender == "Вы") Arrangement.End else Arrangement.Start
                ) {
                    Card(
                        modifier = Modifier
                            .widthIn(max = 300.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0XFFF6DCE7) // Розовый цвет
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = text,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                            Text(
                                text = time,
                                fontSize = 10.sp,
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }

        // Поле ввода сообщения с кнопкой прикрепления
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {  },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AttachFile,
                    contentDescription = "Прикрепить файл",
                    tint = Color.Black
                )
            }
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                placeholder = { Text("Введите сообщение") },
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, // Поддержка текста на любом языке
                    imeAction = ImeAction.Send // Кнопка "Отправить" на клавиатуре

                ),

                )
            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        val currentTime = LocalTime1.now().format(timeFormatter)
                        messages.add(Triple("Вы", messageText, currentTime))
                        messageText = "" // Очистка поля после отправки
                    }
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Отправить",
                    tint = Color.Black
                )
            }
        }
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