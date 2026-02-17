package com.example.shoppinglist.features.auth.presentation.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SLTextButton
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlTextFields
import com.example.shoppinglist.core.utils.asString
import com.example.shoppinglist.features.auth.presentation.model.LoginScreenAction
import com.example.shoppinglist.features.auth.presentation.model.LoginScreenEvent
import com.example.shoppinglist.features.auth.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onSignInClick: () -> Unit,
    onCreateNewAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.action.collect {
            when (it) {
                is LoginScreenAction.NavigateToProductList -> {
                    onSignInClick()
                }

                is LoginScreenAction.ShowFormError -> {
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



    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.login_label),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.size(12.dp))
                SlTextFields.SlInputTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    labelText = stringResource(R.string.email_label),
                )
                Spacer(Modifier.size(12.dp))
                SlTextFields.SlInputTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    labelText = stringResource(R.string.password_label),
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
                    modifier = Modifier.width(150.dp),
                    text = stringResource(R.string.login_label),
                    onClick = {
                        viewModel.obtainEvent(LoginScreenEvent.LoginClick)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.42f),
                    disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.38f),
                    shape = RoundedCornerShape(4.0.dp),
                    isLoading = state.isLoading,
                    enabled = state.email != "" && state.password != ""
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(R.string.forgot_password_label),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onForgotPasswordClick
                        ),
                )
            }

            Text(
                text = stringResource(R.string.create_new_account_label),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onCreateNewAccountClick
                    ),
                textAlign = TextAlign.Center
            )

        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(onSignInClick = {}, onCreateNewAccountClick = {}, onForgotPasswordClick = {})
}