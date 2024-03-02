package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.salestestapp.R
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.salescontrol.SalesSortFilter
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.iconSize20
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun SalesFilteringBar(
    orderByTimeOrSeatNumberSelection: SalesSortFilter,
    filterMenuItemVisible: Boolean,
    isEnableSalesControlFilter: Boolean,
    onClickMenuSalesFilterItem: () -> Unit,
    onClickSortByOption: (Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    val radioOptions = listOf(R.string.salescontrol_lblTime, R.string.salescontrol_lblSeatNumber)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = spacing8, end = spacing8)
    ) {
        Text(text = "Sort By:", fontWeight = FontWeight.SemiBold)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(start = spacing8)
        ) {
            Text(text = stringResource(id = orderByTimeOrSeatNumberSelection.salesSortingTitle))
            Spacer(modifier = Modifier.width(spacing8))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize20)
                    .align(Alignment.CenterVertically)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                radioOptions.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onClickSortByOption(it)
                            expanded = !expanded
                        },
                        text = { Text(text = stringResource(id = it)) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(full))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing8),
            modifier = Modifier
                .padding(spacing8)
                .clickable { onClickMenuSalesFilterItem() }
        ) {
            if (filterMenuItemVisible && isEnableSalesControlFilter) {
                Text(
                    text = "Sales Control",
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Outlined.FilterList,
                    contentDescription = null,
                    modifier = Modifier
                        .size(iconSize20)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@ThemePreview
@Composable
fun SalesFilteringBarPreview() {
    SalesFilteringBar(
        orderByTimeOrSeatNumberSelection = SalesSortFilter.TIME,
        isEnableSalesControlFilter = true,
        filterMenuItemVisible = true,
        onClickMenuSalesFilterItem = {},
        onClickSortByOption = {}
    )
}