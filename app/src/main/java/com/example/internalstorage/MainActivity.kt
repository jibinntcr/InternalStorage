package com.example.internalstorage

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internalstorage.ui.theme.InternalStorageTheme

// BITS Pilani Branding Colors
val BitsBlue = Color(0xFF003366)
val BitsRed = Color(0xFFC41230)
val BackgroundGray = Color(0xFFF2F2F2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternalStorageTheme {
                InternalStorageDemo()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InternalStorageDemo() {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var fileContent by remember { mutableStateOf("No notes found.") }
    val fileName = "student_notes.txt"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("BITS INTERNAL STORAGE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Session CS10: Multi-Data Demo", color = Color.White, fontSize = 10.sp)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BitsBlue)
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = BitsBlue) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("JIBIN N | Course Faculty", color = Color.White, fontWeight = FontWeight.Medium)
                    Text("BITS Pilani - WILP 2026", color = Color.LightGray, fontSize = 11.sp)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Append Mode Implementation", color = BitsBlue, fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Add a new student note...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // SAVE BUTTON: Uses MODE_APPEND to add data without overwriting
                Button(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            try {
                                // MODE_APPEND ensures new data is added to the end of the file
                                context.openFileOutput(fileName, Context.MODE_APPEND).use { fos ->
                                    val dataToSave = "$inputText\n"
                                    fos.write(dataToSave.toByteArray())
                                }
                                inputText = ""
                                fileContent = "Note added to file!"
                            } catch (e: Exception) {
                                fileContent = "Error saving data."
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = BitsBlue)
                ) {
                    Text("Append Note")
                }

                // READ BUTTON: Fetches all stored notes from private storage
                Button(
                    onClick = {
                        try {
                            fileContent = context.openFileInput(fileName).bufferedReader().use { it.readText() }
                        } catch (e: Exception) {
                            fileContent = "Empty file. Add some notes first!"
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = BitsRed)
                ) {
                    Text("Read All")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Full Student Record (Internal Storage):", modifier = Modifier.align(Alignment.Start), fontWeight = FontWeight.Bold, color = BitsBlue)
            Surface(
                modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp),
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 2.dp
            ) {
                Text(
                    text = fileContent,
                    modifier = Modifier.padding(16.dp),
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    color = Color.DarkGray
                )
            }
        }
    }
}