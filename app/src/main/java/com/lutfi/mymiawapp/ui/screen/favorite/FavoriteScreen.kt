package com.lutfi.mymiawapp.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lutfi.mymiawapp.common.UiState
import com.lutfi.mymiawapp.di.Injection
import com.lutfi.mymiawapp.ui.ViewModelFactory
import com.lutfi.mymiawapp.ui.screen.home.HomeContent

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFavoriteCats()
            }

            is UiState.Success -> {
                if (uiState.data.isNotEmpty()) {
                    HomeContent(
                        cat = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            text = "No favorite cats available.",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            is UiState.Error -> { }
        }
    }
}