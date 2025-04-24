package com.toluleke.composesolution

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                    LazyColumn (
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
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = stringResource(R.string.line))
        SpaceDifferential(modifier, contentPresent = false, composable = {})
        SpaceDifferential(
            modifier = modifier,
            contentPresent = true,
            composable = {
                Row {
                    Text(
                        text = (country.name ?: "No Country provided") + "," + country.region
                    )

                    Spacer(modifier.weight(0.5f))

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
                Row(horizontalArrangement = Arrangement.Start){
                    Text(
                        text = country.capital ?: "No Capital provided"
                    )
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
    Row(horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = "|", modifier = modifier.padding(end = 16.dp))
        if (contentPresent) {
            composable()
        } else{
            Spacer(modifier.weight(1f))
        }
        Text(text = "|", modifier = modifier.padding(start = 16.dp))
    }
}