package com.example.shoppinglist.features.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.core.presentation.ui.components.SLTextButton
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlTextFields

@Composable
fun LoginScreen(onItemClick: () -> Unit) {
    var rememberEmailText by remember { mutableStateOf("") }
    var rememberPasswordText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Shopping List",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.size(12.dp))
            SlTextFields.SlInputTextField(
                value = rememberEmailText,
                onValueChange = { rememberEmailText = it },
                labelText = "Email",
            )
            Spacer(Modifier.size(12.dp))
            SlTextFields.SlInputTextField(
                value = rememberPasswordText,
                onValueChange = { rememberPasswordText = it },
                labelText = "Password"

            )
            Spacer(Modifier.size(12.dp))
            SlButtons.SLTextButton(
                modifier = Modifier.width(150.dp),
                text = "Sing in",
                onClick = onItemClick,
                textStyle = MaterialTheme.typography.bodyMedium,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(4.0.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Forgot password?",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    },
            )
        }

        Text(
            text = "Create new account",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                },
            textAlign = TextAlign.Center
        )

    }
}