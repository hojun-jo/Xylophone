package com.example.xylophone

import android.app.Application
import android.content.pm.ActivityInfo
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.xylophone.ui.theme.XylophoneTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // 화면 가로
        super.onCreate(savedInstanceState)

        setContent {
            XylophoneScreen(viewModel = viewModel)
        }
    }
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val soundPool = SoundPool.Builder().setMaxStreams(8).build()
    private val sounds = listOf(
        soundPool.load(application.applicationContext, R.raw.c3, 1),
        soundPool.load(application.applicationContext, R.raw.d, 1),
        soundPool.load(application.applicationContext, R.raw.e, 1),
        soundPool.load(application.applicationContext, R.raw.f, 1),
        soundPool.load(application.applicationContext, R.raw.g, 1),
        soundPool.load(application.applicationContext, R.raw.a, 1),
        soundPool.load(application.applicationContext, R.raw.b, 1),
        soundPool.load(application.applicationContext, R.raw.c4, 1),
    )

    fun playSound(index: Int) {
        soundPool.play(sounds[index], 1f, 1f, 0, 0, 1f)
    }

    override fun onCleared() {
        soundPool.release()
        super.onCleared()
    }
}

@Composable
fun XylophoneScreen(
    viewModel: MainViewModel
) {
    val keys = listOf(
        Pair("도", Color.Red),
        Pair("레", Color(0xFFFF9800)),
        Pair("미", Color(0xFFFFC107)),
        Pair("파", Color(0xFF8BC34A)),
        Pair("솔", Color(0xFF2196F3)),
        Pair("라", Color(0xFF3F51B5)),
        Pair("시", Color(0xFF673AB7)),
        Pair("도", Color.Red),
    )

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        keys.forEachIndexed { index, key ->
            val padding = (index + 2) * 8 // 16, 24, 32,...
            Keyboard(
                modifier = Modifier
                    .padding(top = padding.dp, bottom = padding.dp)
                    .clickable {
                               viewModel.playSound(index = index)
                    },
                text = key.first,
                 color = key.second
            )
        }
    }
}

@Composable
fun Keyboard(
    modifier: Modifier,
    text: String,
    color: Color
) {
    Box(
        modifier = modifier
            .width(50.dp)
            .fillMaxHeight()
            .background(color = color)
    ) {
        Text(
            text = text,
            style = TextStyle(color = Color.White, fontSize = 20.sp),
            modifier = Modifier.align(Alignment.Center),
        )
    }
}