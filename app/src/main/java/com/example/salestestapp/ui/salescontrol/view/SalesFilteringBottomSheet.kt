package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.salescontrol.enums.SalesFilterType
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.model.mockSeatListState
import com.example.salestestapp.ui.salescontrol.state.SalesFilteringBottomSheetUIState
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.height16
import com.example.salestestapp.ui.theme.height24
import com.example.salestestapp.ui.theme.iconSize20
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing32
import com.example.salestestapp.ui.theme.spacing8
import com.example.salestestapp.ui.theme.width1

@Composable
fun SalesFilteringBottomSheet(
    salesFilteringBottomSheetUIState: SalesFilteringBottomSheetUIState,
    onEvent: (SalesControlScreenEvent) -> Unit
) {
    Box {
        Column(
            modifier = Modifier.padding(start = spacing16, end = spacing16, bottom = spacing16)
        ) {
            HorizontalDivider(thickness = 2.dp)

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Select Filters",
                    fontSize = fontSize16,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(full))
                IconButton(onClick = { onEvent(SalesControlScreenEvent.OnCloseBottomSheet) }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = height24))
            Text(text = "Sale Type:")
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(start = spacing16)
            ) {
                val salesFilters = SalesFilterType.values().toList()

                items(salesFilters.size) { item ->
                    val checkedValue = when (salesFilters[item]) {
                        SalesFilterType.FILTER_BY_MISSED ->
                            salesFilteringBottomSheetUIState.isMissedChecked
                        SalesFilterType.FILTER_BY_COMP ->
                            salesFilteringBottomSheetUIState.isCompChecked
                        SalesFilterType.FILTER_BY_VALID ->
                            salesFilteringBottomSheetUIState.isValidChecked
                        SalesFilterType.FILTER_BY_VOID ->
                            salesFilteringBottomSheetUIState.isVoidChecked
                    }

                    SwitchAndSalesType(
                        salesType = salesFilters[item],
                        isChecked = checkedValue,
                        onCheckChanged = { type, isChecked ->
                            onEvent(SalesControlScreenEvent.OnSalesTypeChecked(type, isChecked))
                        }
                    )
                }
            }


            Spacer(modifier = Modifier.height(height = height24))
            Text(text = "Seat Number:")
            Spacer(modifier = Modifier.height(height = height16))
            Row {
                SeatNumberSelector(
                    seatNumber = salesFilteringBottomSheetUIState.seatNumberSelected,
                    seatList = mockSeatListState(),
                    onSeatNumberSelected = { selected ->
                        onEvent(SalesControlScreenEvent.OnSeatNumberSelected(selected))
                    }
                )

                Spacer(modifier = Modifier.weight(full))
                Button(
                    modifier = Modifier.padding(end = spacing16),
                    onClick = { onEvent(SalesControlScreenEvent.OnResetClicked) },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.White
                    )
                ) {
                    Text(text = "Reset")
                }
            }
        }
    }
}

@Composable
fun SeatNumberSelector(
    seatNumber: String?,
    seatList: List<String>,
    onSeatNumberSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(start = spacing16)
            .border(BorderStroke(width1, Color.Black))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(spacing8)
        ) {
            if (seatNumber != null) Text(text = seatNumber) else Text(text = "All")
            Spacer(modifier = Modifier.width(spacing32))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.size(iconSize20)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                seatList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onSeatNumberSelected.invoke(it)
                            expanded = !expanded
                        },
                        text = { Text(text = it) }
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchAndSalesType(
    salesType: SalesFilterType,
    isChecked: Boolean,
    onCheckChanged: (SalesFilterType, Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing8),
        modifier = Modifier.padding(start = spacing16, bottom = spacing16)
    ) {
        Switch(
            checked = isChecked,
            onCheckedChange = { onCheckChanged(salesType, isChecked) },
            colors = SwitchDefaults.colors(
                checkedTrackColor = Color.LightGray,
                uncheckedTrackColor = Color.LightGray,
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.primary,
            )
        )
        Text(
            text = salesType.type,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@ThemePreview
@Composable
fun SalesFilteringBottomSheetPreview() {
    SalesFilteringBottomSheet(
        salesFilteringBottomSheetUIState =
        SalesFilteringBottomSheetUIState(
            isVoidChecked = true,
            isValidChecked = true,
            isMissedChecked = false,
            isCompChecked = true,
            seatNumberSelected = "1A"
        ),
        onEvent = {},
    )
}