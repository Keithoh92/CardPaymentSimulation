package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.compose.AppTheme
import com.example.salestestapp.R
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun SalesAssistantCodesSegmentedGroup(
    salesAssistantsCodes: List<String>,
    selectedSACodeIndex: Int,
    onChangeSACodeFilter: (String, Int) -> Unit
) {
    val lazyRowListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = spacing8, top = spacing8)
    ) {
        Text(text = stringResource(R.string.filter_by_sales_assistant))

        LaunchedEffect(key1 = selectedSACodeIndex) {
            if (selectedSACodeIndex == 0) {
                lazyRowListState.animateScrollToItem(selectedSACodeIndex)
            }
        }

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            state = lazyRowListState
        ) {
            items(salesAssistantsCodes.size) { currentSACodeIndex ->
                Column {
                    CrewCodesOutlinedButton(
                        currentCrewCodeIndex = currentSACodeIndex,
                        selectedSalesAssistantCode = selectedSACodeIndex,
                        saCode = salesAssistantsCodes[currentSACodeIndex],
                        onChangeSACodeFilter = { saCode, index -> onChangeSACodeFilter(saCode, index) }
                    )
                }
            }
        }
    }
}

@ThemePreview
@Composable
fun SalesCrewSegmentedGroupPreview() {
    AppTheme {
        SalesAssistantCodesSegmentedGroup(
            salesAssistantsCodes = listOf("ALL", "2252", "SALES"),
            selectedSACodeIndex = 0,
            onChangeSACodeFilter = { _, _ ->}
        )
    }
}