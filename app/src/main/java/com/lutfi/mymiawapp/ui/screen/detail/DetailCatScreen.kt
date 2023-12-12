package com.lutfi.mymiawapp.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lutfi.mymiawapp.R
import com.lutfi.mymiawapp.common.UiState
import com.lutfi.mymiawapp.di.Injection
import com.lutfi.mymiawapp.ui.ViewModelFactory
import com.lutfi.mymiawapp.ui.theme.MyMiawAppTheme

@Composable
fun DetailScreen(
    catId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCatById(catId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.image,
                    name = data.name,
                    detail = data.detail,
                )
                viewModel.isFavorite.collectAsState(initial = UiState.Loading).value.let { isFavorite ->
                    when (isFavorite) {
                        is UiState.Loading -> {
                            viewModel.getFavoriteById(catId)
                        }
                        is UiState.Success -> {
                            var favorite by rememberSaveable { mutableStateOf(isFavorite.data) }
                            FavoriteButton(
                                isFavorite = favorite,
                                onClick = {
                                    if (favorite) {
                                        favorite = false
                                        viewModel.deleteFromFavorite(data)
                                    } else {
                                        favorite = true
                                        viewModel.addToFavorite(data)
                                    }
                                }
                            )
                        }
                        is UiState.Error -> {}
                    }
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    name: String,
    detail: String,
    modifier: Modifier = Modifier,
) {
   Column(
       modifier = modifier
           .verticalScroll(rememberScrollState())
   ) {
       Image(
           painter = painterResource(id = image),
           contentDescription = null,
           contentScale = ContentScale.Crop,
           modifier = modifier
               .height(400.dp)
               .fillMaxWidth()
               .clip(RoundedCornerShape(8.dp))
       )
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.spacedBy(12.dp),
           modifier = Modifier
               .padding(16.dp)
       ) {
           Text(
               text = name,
               textAlign = TextAlign.Left,
               style = MaterialTheme.typography.titleLarge.copy(
                   fontWeight = FontWeight.ExtraBold
               ),
           )
           Text(
               text = detail,
               textAlign = TextAlign.Justify,
               style = MaterialTheme.typography.bodyMedium,
           )
       }
   }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            if (isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Delete"
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Add"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun DetailContentPreview() {
    MyMiawAppTheme {
        DetailContent(
            R.drawable.abyssinian,
            "Abyssinian",
            "Kucing Abyssinian adalah salah satu ras tertua yang dikenal dan memiliki bulu pendek dengan motif berbintik-bintik yang mencolok. Tubuh mereka ramping dan otot, dengan mata yang besar dan telinga yang sedang.\\nSifatnya sangat aktif, cerdas, dan penuh semangat. Mereka suka bermain dan seringkali memerlukan stimulasi mental untuk tetap bahagia. Abyssinian adalah kucing yang cocok untuk pemilik yang ingin kucing yang aktif dan cerdas.",
        )
    }
}

