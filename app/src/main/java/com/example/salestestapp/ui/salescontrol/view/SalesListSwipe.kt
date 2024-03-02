package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import com.example.salestestapp.ui.salescontrol.model.SaleInfoUIState
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo
import com.example.salestestapp.ui.theme.spacing8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesListSwipe(
    salesInfoDisplayList: List<SaleInfoUIState>,
    swipeEnabled: Boolean
) {

    val pullRefreshState = rememberPullToRefreshState(
        positionalThreshold =PullToRefreshDefaults.PositionalThreshold,
        enabled = { swipeEnabled },
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = spacing8)
    ) {
        items(salesInfoDisplayList.size) {
            val sale = salesInfoDisplayList[it]
            SalesListItem(saleInfoUIState = sale)
        }
    }
    PullToRefreshContainer(state = pullRefreshState)
}

@Preview
@Composable
fun SalesListSwipePreview() {
    AppTheme {
        SalesListSwipe(mockSalesInfo(), true)
    }
}