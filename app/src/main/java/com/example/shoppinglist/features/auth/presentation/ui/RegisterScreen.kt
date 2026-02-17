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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SLTextButton
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlIcon
import com.example.shoppinglist.core.presentation.ui.components.SlTextFields
import com.example.shoppinglist.features.auth.presentation.model.RegisterAction
import com.example.shoppinglist.features.auth.presentation.model.RegisterEvent
import com.example.shoppinglist.features.auth.presentation.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onBackArrowClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.action.collect {
            when (it) {
                is RegisterAction.NavigateToLogin -> {
                    onRegisterClick()
                }

                else -> {}
            }
        }
    }
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
                text = stringResource(R.string.registration_label),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(Modifier.size(12.dp))
            SlTextFields.SlInputTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                labelText = stringResource(R.string.email_label),

                )
            Spacer(Modifier.size(12.dp))
            SlTextFields.SlInputTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                labelText = stringResource(R.string.password_label)
            )
            Spacer(Modifier.size(12.dp))
            SlTextFields.SlInputTextField(
                value = state.passwordCheck,
                onValueChange = viewModel::onPasswordCheckChange,
                labelText = stringResource(R.string.check_password_label)
            )
            Spacer(Modifier.size(12.dp))
            SlButtons.SLTextButton(
                text = stringResource(R.string.create_account_label),
                onClick = {
                    viewModel.obtainEvent(RegisterEvent.RegisterClick)
                },
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