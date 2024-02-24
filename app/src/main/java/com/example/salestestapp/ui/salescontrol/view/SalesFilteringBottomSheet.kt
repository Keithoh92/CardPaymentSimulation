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
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            modifier = Modifier
                .border(BorderStroke(0.5.dp, Color.LightGray))
                .padding(start = spacing16, end = spacing16, bottom = spacing16)
        ) {
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
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(height = height24))
            Text(text = "Sale Type:")
            Row {
                Column {
                    SwitchAndSalesType(
                        SalesFilterType.FILTER_BY_MISSED,
                        salesFilteringBottomSheetUIState.isMissedChecked,
                        onCheckChanged = { type, isChecked ->
                            onEvent(SalesControlScreenEvent.OnSalesTypeChecked(type, isChecked))
                        }
                    )
                    SwitchAndSalesType(
                        SalesFilterType.FILTER_BY_VOID,
                        salesFilteringBottomSheetUIState.isVoidChecked,
                        onCheckChanged = { type, isChecked ->
                            onEvent(SalesControlScreenEvent.OnSalesTypeChecked(type, isChecked))
                        }
                    )
                }
                Column {
                    SwitchAndSalesType(
                        SalesFilterType.FILTER_BY_VALID,
                        salesFilteringBottomSheetUIState.isValidChecked,
                        onCheckChanged = { type, isChecked ->
                            onEvent(SalesControlScreenEvent.OnSalesTypeChecked(type, isChecked))
                        }
                    )
                    SwitchAndSalesType(
                        SalesFilterType.FILTER_BY_COMP,
                        salesFilteringBottomSheetUIState.isCompChecked,
                        onCheckChanged = { type, isChecked ->
                            onEvent(SalesControlScreenEvent.OnSalesTypeChecked(type, isChecked))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = height24))
            Text(text = "Seat Number:")
            Spacer(modifier = Modifier.height(height = height16))
            SeatNumberSelector(
                seatNumber = salesFilteringBottomSheetUIState.seatNumberSelected,
                seatList = mockSeatListState(),
                onSeatNumberSelected = { selected ->
                    onEvent(SalesControlScreenEvent.OnSeatNumberSelected(selected))
                }
            )
        }
    }
}

@Composable
fun SeatNumberSelector(
    seatNumber: String?,
    seatList: List<String>,
    onSeatNumberSelected: (String) -> Unit,
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
                        }
                    ) { Text(text = it) }
                }
            }
        }
    }
}

object SalesFilterType {
    const val FILTER_BY_VOID = "Void"
    const val FILTER_BY_MISSED = "Missed"
    const val FILTER_BY_VALID = "Valid"
    const val FILTER_BY_COMP = "Complimentary"
}

@Composable
fun SwitchAndSalesType(
    salesType: String,
    isChecked: Boolean,
    onCheckChanged: (String, Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = spacing16)
    ) {
        Switch(
            checked = isChecked,
            onCheckedChange = { onCheckChanged(salesType, isChecked) },
            colors = SwitchDefaults.colors(
                checkedTrackColor = Color.Gray,
                uncheckedTrackColor = Color.Gray,
                checkedThumbColor = Color.Black,
                uncheckedThumbColor = Color.Black,
            )
        )
        Text(text = salesType, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true)
@Composable
fun SalesFilteringBottomSheetPreview() {
    SalesFilteringBottomSheet(
        salesFilteringBottomSheetUIState =
        SalesFilteringBottomSheetUIState(
            isVoidChecked = true,
            isValidChecked = true,
            isMissedChecked = true,
            isCompChecked = true,
            seatNumberSelected = "1A"
        ),
        onEvent = {},
    )
}