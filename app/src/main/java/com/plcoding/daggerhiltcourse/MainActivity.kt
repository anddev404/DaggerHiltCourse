package com.plcoding.daggerhiltcourse

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.ui.theme.DaggerHiltCourseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaggerHiltCourseTheme {
                val viewModel = hiltViewModel<MyViewModel>()
                val data = viewModel.data.collectAsState()
                val error = viewModel.error.collectAsState(initial = 0)

                LaunchedEffect(data, error) {

                }
                AppView(data.value, error.value) {
                    viewModel.fetchData()
                }

            }
        }
    }
}

@Composable
fun AppView(text: String?, error: Int?, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Log.d("MARCIN_W", "data= ${text}");
        error?.let {
            if (it != 0) {
                Toast.makeText(
                    LocalContext.current,
                    "Error $it while downloading!\n Try agan.",
                    Toast.LENGTH_LONG
                )
                    .show();
            }

        }

        if (text != null) {
            Log.d("MARCIN_W", "let");
            Text(text = text)
        } else {
            Log.d("MARCIN_W", "run");
            Text(text = "No Data")
        }
        Button(onClick = { onClick() }) {
            Text(text = "Click")
        }
    }

}