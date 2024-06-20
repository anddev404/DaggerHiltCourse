package com.plcoding.daggerhiltcourse

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.plcoding.daggerhiltcourse.ui.theme.DaggerHiltCourseTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

//testowy commit 20.06.2024. 13.12


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // private lateinit var auth: FirebaseAuth;
//    suspend fun getUser() {
//        coroutineScope {//coroutinescope czeka na wykonanie wszytkich async i launchy i dopiero sam sie zakończy tzn wyjdzie poza coroutine
//            for (i in 1..10) {
//
//                val x = async() {
//                    delay(i * 1000L)
//                    Log.d("MARCIN_WW", "$i")
//
//
//                }
//
//                Log.d("MARCIN_WW", "end $i = ${x.await()}");
//
//            }
//            // Log.d("MARCIN_WW", "END COROUTINE");
//
//        }
//        //   Log.d("MARCIN_WW", "END");
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            val jobGetUser = launch {
                delay(3000)
                Log.d("MARCIN_WW", "Get user");

            }
            val jobGetUsersFrend = launch {
                delay(500)
                Log.d("MARCIN_WW", "get frends");

            }
            //  joinAll(jobGetUser, jobGetUsersFrend)
            jobGetUser.join()
            Log.d("MARCIN_WW", "END");
            //wykonywanie kodu po pobraniu wszystkich danych
        }

        Log.d("MARCIN_WW", "END COROUTINE");


        var auth = FirebaseAuth.getInstance()
        val uid: String = auth.currentUser?.uid ?: ""
        //4sFujI42wNheHUMFNKvHCLUsDX53
        val database = FirebaseDatabase.getInstance()

        val testLogin = "marcin@wawer.pl"
        val testPassowr = "qwerty"
        auth.signInWithEmailAndPassword(testLogin, testPassowr)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "log in - succes ${uid}", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "log in - fail", Toast.LENGTH_LONG).show();

                }

            }


//        Metoda addVal ueEventListener w Firebase działa w taki sposób, że nasłuchuje na zmiany
//        w czasie rzeczywistym. Oznacza to, że gdy dane w bazie danych zmienią się, czyli ktoś coś doda,
//        usunie lub zmodyfikuje, metoda onDataChange zostanie wywołana ponownie. Twoja aplikacja
//        będzie więc na bieżąco otrzymywać aktualizacje.


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
                    val myRef = database.getReference("Users").child(uid)
                    val myRef2 = database.getReference("Habits").child(uid)

                    myRef.addValueEventListener(object : ValueEventListener {
                        //addListenerForSingleValueEvent-jednokrotnie wywołanie -// wielokrotne - nasłuchiwanie -addValueEventListener
                        //czy to działa cały czałs i łaczy sie z baza i jak ktos doda coś to sie wykona ponownie, czy tylko w momencie wykonania
                        override fun onDataChange(snapshot: DataSnapshot) {

                            Toast.makeText(
                                applicationContext,
                                "jest user",
                                Toast.LENGTH_SHORT
                            ).show();
//                            val users: MutableList<User> = mutableListOf()
//                            for (userSnapshot in snapshot.children) {
//                                val user = userSnapshot.getValue(User::class.java)
//                                if (user != null) {
//                                    users.add(user)
//                                }
//                            }
//                            Toast.makeText(
//                                applicationContext,
//                                "lista userów: = ${users.size}",
//                                Toast.LENGTH_LONG
//                            ).show();
                            var x = 5
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                applicationContext,
                                "lista userów: = 0",
                                Toast.LENGTH_LONG
                            ).show();
                            var x = 5
                        }

                    })

                    myRef2.addValueEventListener(object : ValueEventListener {
                        //addListenerForSingleValueEvent-jednokrotnie wywołanie -// wielokrotne - nasłuchiwanie -addValueEventListener
                        //czy to działa cały czałs i łaczy sie z baza i jak ktos doda coś to sie wykona ponownie, czy tylko w momencie wykonania
                        override fun onDataChange(snapshot: DataSnapshot) {

                            Toast.makeText(
                                applicationContext,
                                "jest user2",
                                Toast.LENGTH_SHORT
                            ).show();
//                            val users: MutableList<User> = mutableListOf()
//                            for (userSnapshot in snapshot.children) {
//                                val user = userSnapshot.getValue(User::class.java)
//                                if (user != null) {
//                                    users.add(user)
//                                }
//                            }
//                            Toast.makeText(
//                                applicationContext,
//                                "lista userów: = ${users.size}",
//                                Toast.LENGTH_LONG
//                            ).show();
                            var x = 5
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                applicationContext,
                                "lista userów: = 0",
                                Toast.LENGTH_LONG
                            ).show();
                            var x = 5
                        }

                    })

                }
                var stateEditText = viewModel.editText.collectAsState()

                showEditText(stateEditText.value) {
                    viewModel.sendEditText(it)
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

@Composable
fun showEditText(state: TextFieldValue, sendState: (TextFieldValue) -> Unit) {

    TextField(value = state, onValueChange = { sendState(it) })
}

