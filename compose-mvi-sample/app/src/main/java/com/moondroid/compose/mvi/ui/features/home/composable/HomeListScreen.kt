package com.moondroid.compose.mvi.ui.features.home.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.compose.mvi.ui.features.home.HomeContract
import com.moondroid.compose.mvi.ui.features.home.HomeViewModel
import com.moondroid.compose.mvi.ui.navigation.MyNavigationAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeListScreen(navigationAction: MyNavigationAction, viewModel: HomeViewModel) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.intent.send(HomeContract.Intent.FetchNotes)
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is HomeContract.Effect.ToNoteDetail -> Toast.makeText(context, "aaaa", Toast.LENGTH_SHORT).show()
                is HomeContract.Effect.Toast -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    when (val state = uiState) {
        is HomeContract.State.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = state.message)
        }

        HomeContract.State.Empty -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "새로운 메모를 추가해보세요.")
                Box(modifier = Modifier.height(20.dp))
                Button(onClick = { navigationAction.toNote }) {
                    Text(text = "메모 추가")
                }
            }
        }

        HomeContract.State.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is HomeContract.State.Notes -> LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.data.size) { idx ->
                NoteItem(state.data[idx], {}, {})
            }
        }
    }
}