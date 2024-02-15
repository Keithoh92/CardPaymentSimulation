package com.example.salestestapp.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salestestapp.ui.theme.spacing10
import com.example.salestestapp.R
import com.example.salestestapp.ui.theme.double
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.qtrSpacing

@Composable
fun TopAppBar(
    crewMember: String,
) {

    var menuCrew by remember {
        mutableStateOf(false)
    }

    var menuSalesFilter by remember {
        mutableStateOf(false)
    }

    var title by remember {
        mutableStateOf("(All)")
    }

    TopAppBar(
        backgroundColor = colorResource(id = R.color.black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing10)
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = colorResource(id = R.color.white),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Text(
                text = "Card Payment",
                fontSize = 16.sp,
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .weight(double)
                    .padding(start = qtrSpacing),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.weight(full)
            ) {
                IconButton(
                    onClick = { menuCrew = !menuCrew } ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bell),
                        contentDescription = "crew",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

                IconButton(
                    onClick = { menuSalesFilter = !menuSalesFilter } ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dotmenu),
                        contentDescription = "sales filter",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

                DropdownMenu(
                    expanded = menuCrew,
                    onDismissRequest = { menuCrew = false }
                ) {
                    DropdownMenuItem(onClick = { /*TODO*/ }) {
                        Text(text = crewMember)
                    }
                }

                DropdownMenu(
                    expanded = menuSalesFilter,
                    onDismissRequest = { menuSalesFilter = false }
                ) {
                    val saleTypeList = listOf(
                        FilterType.FILTER_BY_SALE
                    )

                    saleTypeList.forEach {
                        DropdownMenuItem(onClick = { title = "(${it})" }) {
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
}

object FilterType {
    const val FILTER_BY_SALE = "Sales"
}

@Preview(showBackground = true)
@Composable
fun SalesControlTopAppBarPreview() {
    TopAppBar("")
}