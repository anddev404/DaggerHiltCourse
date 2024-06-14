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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.plcoding.daggerhiltcourse.ui.theme.DaggerHiltCourseTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Users")
        val testLogin = "marcin@wawer.pl"
        val testPassowr = "qwerty"
        val myToken = "customToken"
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(
                    applicationContext,
                    "lista userów: = ${snapshot.childrenCount}",
                    Toast.LENGTH_LONG
                ).show();
                var x = 5
            }

            override fun onCancelled(error: DatabaseError) {
                var x = 5
            }

        })

        auth.signInWithEmailAndPassword(testLogin, testPassowr)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "succes", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show();

                }

            }




        setContent {
            DaggerHiltCourseTheme {
                val viewModel = hiltViewModel<MyViewModel>()
                val data = viewModel.data.collectAsState()
                val error = viewModel.error.collectAsState()
                val progress = viewModel.progress.collectAsState()

                LaunchedEffect(data, error) {

                }
                AppView(data.value, error.value, progress.value) {
                    viewModel.fetchData()
                }

            }
        }


    }
}

@Composable
fun AppView(text: String?, error: Int?, progress: String?, onClick: () -> Unit) {
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
        if (progress != null) {
            Log.d("MARCIN_W", "progress");
            Text(text = progress)
        }
        Button(onClick = { onClick() }) {
            Text(text = "Click")
        }
    }

}