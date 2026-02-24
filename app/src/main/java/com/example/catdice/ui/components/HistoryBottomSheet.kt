package com.example.catdice.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catdice.domain.RollResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    historyList: List<RollResult>,
    onDismissRequest: () -> Unit,
    sheetState: SheetState
) {
    val newEntryColor = Color(0xFF4CAF50)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Last 5 Rolls",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4245D3),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (historyList.isEmpty()) {
                Text(
                    text = "No rolls yet",
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    itemsIndexed(historyList) { index, roll ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (index == 0) {
                                Text(
                                    text = "NEW",
                                    fontWeight = FontWeight.Black,
                                    color = newEntryColor,
                                    fontSize = 16.sp
                                )
                            } else {
                                Text(
                                    text = "#${index + 1}",
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🎲 ${roll.dice1}", fontSize = 18.sp)
                                if (roll.dice2 != null) {
                                    Text(text = " + 🎲 ${roll.dice2}", fontSize = 18.sp)
                                }
                                Text(
                                    text = " = ${roll.total}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                        Divider(color = Color.LightGray.copy(alpha = 0.2f))
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}