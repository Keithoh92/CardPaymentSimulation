package com.example.salestestapp.ui.salescontrol.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.halfSpacing
import com.example.salestestapp.ui.theme.spacing10
import com.example.salestestapp.ui.theme.width2
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SalesControlListView(
//    swipeRefreshState: SwipeRefreshState,
//    state: SalesListingState
//    itemListView: List<Saleslist>,
//    viewModel: SalesControlViewModel
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    val state by mutableStateOf(SalesListingUIState())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing10)
            .border(
                width = width2,
                color = Color.LightGray,
                shape = RectangleShape,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
        ) {
            Text(
                text = "Time",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier.weight(full)
            )
            Text(
                text = "Amount",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier.weight(full)
            )
            Text(
                text = "F/A Member",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier.weight(full)
            )
        }
        
        Column(modifier = Modifier.weight(full)) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    /*TODO*/
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(mockSalesInfo().size) { i ->
                        val sales = state.salesLD[i]
                        SalesControlItem(salesListing = mockSalesInfo()[i])
//                        SalesControlItem(
//                            salesListing = sales,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {
//                                    // TODO: Navigate to details screen
//                                }
//                        )
                        if (i < state.salesLD.size) {
                            Divider(modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .padding(halfSpacing)
        ) {
            val totalSalesValue = BigDecimal(state.totalSalesValue).setScale(2, RoundingMode.HALF_EVEN)
            Text(
                text = "Total: $totalSalesValue",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = halfSpacing)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SalesControlListViewPreview() {
    SalesControlListView()
}