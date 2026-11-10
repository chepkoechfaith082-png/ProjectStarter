package com.example.projectstarter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun TipCalc() {
    var billText by rememberSaveable { mutableStateOf("") }
    var tipPercent by rememberSaveable { mutableFloatStateOf(15f) }

    val bill = billText.toDoubleOrNull()
    val tip = bill?.let { it * tipPercent / 100 }
    val total = bill?.let { it + (tip ?: 0.0) }

    Column(
        Modifier.statusBarsPadding().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = billText,
            onValueChange = { billText = it },
            label = { Text("Bill amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = billText.isNotEmpty() && bill == null,
            supportingText = {
                if (billText.isNotEmpty() && bill == null) Text("Enter a valid number")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Tip: ${tipPercent.roundToInt()}%")
        Slider(value = tipPercent, onValueChange = { tipPercent = it }, valueRange = 0f..30f)

        if (tip != null && total != null) {
            Text("Tip: %.2f".format(tip))
            Text("Total: %.2f".format(total), style = MaterialTheme.typography.titleLarge)
        } else {
            Text("Enter a bill to see the tip")
        }
    }
}