package com.lutfi.mymiawapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lutfi.mymiawapp.common.UiState
import com.lutfi.mymiawapp.di.Injection
import com.lutfi.mymiawapp.model.Cat
import com.lutfi.mymiawapp.ui.ViewModelFactory
import com.lutfi.mymiawapp.ui.component.CatItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCats()
            }

            is UiState.Success -> {
                HomeContent(
                    cat = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    cat: List<Cat>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier,
    ) {
        items(cat, key = { it.id }) { data ->
            CatItem(
                image = data.image,
                name = data.name,
                detail = data.description,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                }
            )
            Divider()
        }
    }
}