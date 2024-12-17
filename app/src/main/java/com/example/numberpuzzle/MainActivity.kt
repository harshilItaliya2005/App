package com.example.numberpuzzle

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberpuzzle.ui.theme.NumberPuzzleTheme

val data_list = mutableStateListOf("1", "2", "3", "4", "5", "6", "7", "8", "")

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        data_list.shuffle()
        setContent {
            NumberPuzzleTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "Number Puzzle",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFFA1662F),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp
                                )
                            },
                            actions = {
                                IconButton(onClick = {
                                    resetGame()
                                }) {
                                    Box(
                                        modifier = Modifier
                                            .weight(.4f)
                                            .fillMaxSize()
                                            .background(Color(0xFFA1662F)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.reset),
                                            contentDescription = "Reset"
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = Color(
                                    0xFFC19A6B
                                )
                            )
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .background(Color(0xFFA1662F))
                            .fillMaxSize()
                    ) {
                        Design()
                    }
                }
            }
        }
    }

    @Composable
    fun Design() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .border(2.dp, Color(0xffe7cfb4))
                    .background(Color(0xffffc18c)),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data_list.size) { index ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                handleTileClick(index)
                            }
                            .aspectRatio(1f),
                        elevation = CardDefaults.cardElevation(5.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFA1662F))
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = data_list[index],
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineMedium,
                                color = if (data_list[index].isEmpty()) Color.Transparent else Color.Black
                            )
                        }
                    }
                }
            }
            ElevatedButton(
                onClick = {
                    if (checkWon()) {
                        Toast.makeText(
                            applicationContext,
                            "Congratulations! You win!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("ShowLog", "Won the game")
                    }
                    Log.d("ShowLog", "Click the submit button!")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFFB3623A))
            ) {
                Text(text = "Submit")
            }
        }
    }

    fun checkWon(): Boolean {
        for (i in 1..8) {
            if (data_list[i - 1] != "$i") {
                return false
            }
        }
        return true
    }

    fun handleTileClick(index: Int) {
        Log.d("ShowLog", "Tile clicked at index: $index")
        when (index) {
            0 -> {
                swap(index, 1)
                swap(index, 3)
                swap(index, 6)
                swap(index, 2)
            }

            1 -> {
                swap(index, 0)
                swap(index, 2)
                swap(index, 4)
                swap(index, 7)
            }

            2 -> {
                swap(index, 1)
                swap(index, 5)
                swap(index, 0)
                swap(index, 8)
            }

            3 -> {
                swap(index, 0)
                swap(index, 4)
                swap(index, 6)
                swap(index, 5)
            }

            4 -> {
                swap(index, 1)
                swap(index, 3)
                swap(index, 5)
                swap(index, 7)

            }

            5 -> {
                swap(index, 2)
                swap(index, 4)
                swap(index, 8)
                swap(index, 3)
            }

            6 -> {
                swap(index, 3)
                swap(index, 7)
                swap(index, 0)
                swap(index, 8)
            }

            7 -> {
                swap(index, 4)
                swap(index, 6)
                swap(index, 8)
                swap(index, 1)
            }

            8 -> {
                swap(index, 5)
                swap(index, 7)
                swap(index, 6)
                swap(index, 2)
            }
        }
    }

    fun swap(p: Int, p1: Int) {
        if (data_list[p1].isEmpty()) {
            data_list[p1] = data_list[p]
            data_list[p] = ""
        }
    }

    fun resetGame() {
        data_list.shuffle()
        Log.d("ShowLog", "Game reset and shuffled: $data_list")
    }
}