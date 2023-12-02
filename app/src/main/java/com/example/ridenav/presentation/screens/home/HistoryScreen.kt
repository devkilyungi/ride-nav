package com.example.ridenav.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Sample data class for the history items
data class HistoryItem(val id: Int, val title: String)

@Composable
fun HistoryScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "History",
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Subtitle
            Text(
                text = "Past",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Body with list of items or relevant text
//            if (historyItems.isNotEmpty()) {
//                LazyColumn {
//                    items(historyItems) { item ->
//                        // Display each history item in the list
//                        HistoryItemRow(item = item)
//                    }
//                }
//            } else {
            // Show relevant text when the list is empty
            Text(
                text = "You don't have any recent history available",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
//            }
        }
    }
}

@Composable
fun HistoryItemRow(item: HistoryItem) {
    // Custom row layout for each history item
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // You can customize the content of each item here
        Text(text = item.title, style = MaterialTheme.typography.body1)
    }
}