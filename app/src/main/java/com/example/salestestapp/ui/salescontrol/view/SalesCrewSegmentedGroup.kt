package com.example.salestestapp.ui.salescontrol.view

import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salestestapp.R

@Composable
fun SalesCrewSegmentedGroup(
    crewCodes: List<String>,
    selectedCrewCodeIndex: Int,
    onChangeSaleTypeOrCrewCodeFilter: (String) -> Unit
) {

    val cornerRadius = 10

    @ColorRes val colour: Int = R.color.white
    @ColorRes val color = Color(0xFF2091eb)
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(crewCodes.size) {

            OutlinedButton(
                onClick = {
                    onChangeSaleTypeOrCrewCodeFilter.invoke(crewCodes[it])
                },
                shape = when (it) {
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    crewCodes.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedCrewCodeIndex == it) {
                        Color(0xFF2091eb)
                    } else {
                        Color(0xFF2091eb).copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedCrewCodeIndex == it) {
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color(0xFF2091eb)
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.White
                    )
                }
            ) {
                Text(
                    text = crewCodes[it],
                    color = if (selectedCrewCodeIndex == it) {
                        Color.White
                    } else {
                        Color(0xFF2091eb).copy(alpha = 0.75f)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun SalesCrewSegmentedGroupPreview() {
    SalesCrewSegmentedGroup(
        crewCodes = listOf("ALL", "2252", "SALES"),
        selectedCrewCodeIndex = 0,
        onChangeSaleTypeOrCrewCodeFilter = {}
    )

}