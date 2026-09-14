package com.example.listycity3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onEditCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("")}
    var newProvinceName by remember { mutableStateOf("")}
    var showAddCityFields by remember {mutableStateOf((false))}
    var showEditCityFields by remember {mutableStateOf((false))}
    var selectedCity by remember {mutableStateOf<City?>(null)}
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                }
            ) {
                Text("+")
            }
        }
        if (showAddCityFields || showEditCityFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { OutlinedTextField(
                    value = newCityName,
                    onValueChange = {newCityName = it},
                    label = {
                        if (showAddCityFields) {
                            Text("City")
                        } else if (showEditCityFields) {
                            Text("Edit city")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = {newProvinceName = it},
                    label = {
                        if (showAddCityFields) {
                            Text("Province")
                        } else if (showEditCityFields) {
                            Text("Edit province")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (showAddCityFields) {
                            if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                                onAddCity(
                                    City(
                                        name = newCityName,
                                        province = newProvinceName
                                    )
                                )
                                newCityName = ""
                                newProvinceName = ""
                                showAddCityFields = false
                            }
                        } else if (showEditCityFields) {
                            val updatedCity = City(
                                name = newCityName,
                                province = newProvinceName
                            )
                            if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                                selectedCity?.let {
                                    onEditCity(
                                        it,
                                        updatedCity
                                    )
                                }
                                newCityName = ""
                                newProvinceName = ""
                                showEditCityFields = false
                            }
                        }
                    }
                ) {
                    if (showAddCityFields) {
                        Text("Add City")
                    } else if (showEditCityFields) {
                        Text("Edit City")
                    }
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize().weight(1f)) {
            itemsIndexed(cities) { index, city ->
                CityRow(city = city, modifier = Modifier.clickable {
                        selectedCity = city
                        showEditCityFields = !showEditCityFields
                    })
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}


@Composable
fun CityRow(city: City, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onEditCity = { _, _ -> },
        )
    }
}