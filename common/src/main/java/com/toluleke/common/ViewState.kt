package com.toluleke.common

import com.toluleke.common.response.CountryDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ViewState(
    val countries: List<CountryDetails> = listOf(),
    val errorMessage: String = "",
    val error: Boolean = false
)