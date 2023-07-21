package com.example.temperatureconversor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temperatureconversor.ui.theme.TemperatureConversorTheme
import com.example.temperatureconversor.ui.TempViewModel

@Composable
fun TempScreen(
    tempViewModel: TempViewModel = viewModel()
) {
    val tempUiState by tempViewModel.uiState.collectAsState()

    TempLayout (
        currentTemperature = tempUiState.currentTemperature,
        tempTypeOpposite = tempUiState.currentTempTypeOpposite,
        tempType = tempUiState.currentTempType,
        convertTempInput = { tempViewModel.convertTempImput(it) },
        switchChange = { tempViewModel.switchChange() },
        switchTemp = tempUiState.switchTemp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempLayout(
    currentTemperature: String,
    tempTypeOpposite: String,
    tempType: String,
    convertTempInput: (String) -> Unit,
    switchChange: () -> Unit,
    switchTemp: Boolean
){
    var fieldValue by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.temperature_converter),
            fontSize = 30.sp,
            modifier = Modifier.padding(20.dp)
        )
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = fieldValue,
                onValueChange = { fieldValue = it },
                label = { Text(stringResource(R.string.enter_the_temperature)) },
                singleLine = true,
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(fontWeight = FontWeight.Bold,
                    fontSize = 30.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()}
                )
            )
            Text(
                text = tempType,
                fontSize = 30.sp
            )
        }
        Text(
            text = currentTemperature + tempTypeOpposite, // "20" + "ºC"
            fontSize = 30.sp
        )
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.celsius),
                modifier = Modifier.padding(10.dp)
            )
            Switch(
                checked = switchTemp,
                onCheckedChange = {
                    switchChange()
                    convertTempInput(fieldValue)
                }
            )
            Text(
                text = stringResource(R.string.fahrenheit),
                modifier = Modifier.padding(10.dp)
            )
        }
        Button(
            onClick = { convertTempInput(fieldValue) }
        )
        {
            Text(stringResource(R.string.convert_temperature))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TempConversorPreview() {
    TemperatureConversorTheme {
        TempScreen()
    }
}