package com.example.shoppinglist.features.auth.presentation.ui

import android.util.Log
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.shoppinglist.core.utils.asString
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
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.action.collect {
            when (it) {
                is RegisterAction.NavigateToLogin -> {
                    onRegisterClick()
                }

                is RegisterAction.ShowFormError -> {
                    Log.d("ListDetailViewModel", state.formError?.asString(context) ?: "")
                    snackBarHostState.showSnackbar(
                        it.message.asString(context),
                        duration = SnackbarDuration.Short
                    )
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
        }, snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
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
                labelText = stringResource(R.string.check_password_label),
                isError = state.passwordError != null || state.emailError != null,
                supportText = {
                    if (state.passwordError != null) {
                        state.passwordError?.let {
                            Text(it.asString(), color = MaterialTheme.colorScheme.error)
                        }
                    } else if (state.emailError != null) {
                        state.emailError?.let {
                            Text(it.asString(), color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
            Spacer(Modifier.size(12.dp))
            SlButtons.SLTextButton(
                text = stringResource(R.string.create_account_label),
                onClick = {
                    viewModel.obtainEvent(RegisterEvent.RegisterClick)
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.42f),
                disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.38f),
                shape = RoundedCornerShape(4.0.dp),
                isLoading = state.isLoading,
                enabled = state.email != "" && state.password != "" && state.passwordCheck != ""
            )
        }
    }

}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(onRegisterClick = {}, onBackArrowClick = {})
}