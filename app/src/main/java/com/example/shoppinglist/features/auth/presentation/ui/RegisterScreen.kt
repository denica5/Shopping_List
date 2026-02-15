package com.example.shoppinglist.features.auth.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SLTextButton
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlIcon
import com.example.shoppinglist.core.presentation.ui.components.SlTextFields
import com.example.shoppinglist.features.auth.presentation.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onRegisterClick: () -> Unit, onBackArrowClick: () -> Unit, viewModel: RegisterViewModel = hiltViewModel()) {

    var rememberEmailText by remember { mutableStateOf("") }
    var rememberPasswordText by remember { mutableStateOf("") }
    var rememberPasswordCheckText by remember { mutableStateOf("") }




    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    SlButtons.SlIcon(
                        painter = painterResource(R.drawable.arrow_back),
                        onClick = onBackArrowClick,
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(Modifier.size(12.dp))
            SlTextFields.SlInputTextField(
                value = rememberEmailText,
                onValueChange = { rememberEmailText = it },
                labelText = "Почта",

                )
            Spacer(Modifier.size(12.dp))
            SlTextFields.SlInputTextField(
                value = rememberPasswordText,
                onValueChange = { rememberPasswordText = it },
                labelText = "Пароль"
            )
            Spacer(Modifier.size(12.dp))
            SlTextFields.SlInputTextField(
                value = rememberPasswordCheckText,
                onValueChange = { rememberPasswordCheckText = it },
                labelText = "Повторите пароль"
            )
            Spacer(Modifier.size(12.dp))
            SlButtons.SLTextButton(
                text = "Создать аккаунт",
                onClick = { viewModel.register(rememberEmailText.trim(), password = rememberPasswordText.trim()) },
                textStyle = MaterialTheme.typography.bodyMedium,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(4.0.dp)
            )
        }
    }

}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(onRegisterClick = {}, onBackArrowClick = {})
}