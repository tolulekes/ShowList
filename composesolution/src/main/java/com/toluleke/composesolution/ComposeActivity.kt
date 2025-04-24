package com.toluleke.composesolution

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.toluleke.common.response.CountryDetails
import com.toluleke.composesolution.ui.theme.ShowListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    private val viewModel: ComposeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val viewState by viewModel.viewState.collectAsState()
            ShowListTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        itemsIndexed(viewState.countries) { index, country ->
                            Country(modifier = Modifier, country = country)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Country(modifier: Modifier = Modifier, country: CountryDetails) {

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(0.7f),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.line))
        SpaceDifferential(modifier, contentPresent = false, composable = {})
        SpaceDifferential(
            modifier = modifier,
            contentPresent = true,
            composable = {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = (country.name ?: "No Country provided") + ", " + country.region
                    )
                    Spacer(modifier.weight(1f))
                    Text(
                        text = country.code ?: "No Code provided"
                    )
                }
            }
        )
        SpaceDifferential(modifier = modifier, contentPresent = false, composable = {})
        SpaceDifferential(
            modifier = modifier,
            contentPresent = true,
            composable = {
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = country.capital ?: "No Capital provided"
                    )
                    Spacer(modifier.weight(1f))
                }
            }
        )
        SpaceDifferential(modifier = modifier, contentPresent = false, composable = {})
        Text(text = stringResource(R.string.line))
    }

}

@Composable
private fun SpaceDifferential(
    modifier: Modifier,
    composable: @Composable (() -> Unit),
    contentPresent: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "|")
        if (contentPresent) {
            composable()
            Text(text = "|")

        } else {
            Spacer(modifier.weight(1f))
        }
        Text(text = "|")
    }
}