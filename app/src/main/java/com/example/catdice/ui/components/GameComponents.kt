package com.example.catdice.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catdice.R

@Composable
fun DiceHeader(
    diceCount: Int,
    isRolling: Boolean,
    onDiceCountChanged: (Int) -> Unit
) {
    val darkLilac = Color(0xFF5E5F8A)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bigcat),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterStart)
        )

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.3f))
                .padding(4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (diceCount == 1) darkLilac else Color.Transparent)
                    .clickable { if (!isRolling) onDiceCountChanged(1) }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(text = "1 Dice", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (diceCount == 2) darkLilac else Color.Transparent)
                    .clickable { if (!isRolling) onDiceCountChanged(2) }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(text = "2 Dice", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DiceBoard(
    dice1Result: Int,
    dice2Result: Int,
    diceCount: Int
) {
    val diceImages = listOf(
        R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
        R.drawable.dice4, R.drawable.dice5, R.drawable.dice6
    )

    val currentTotal = dice1Result + (if (diceCount == 2) dice2Result else 0)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TOTAL: $currentTotal",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 35.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = diceImages[dice1Result - 1]),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .padding(4.dp)
            )

            AnimatedVisibility(visible = diceCount == 2) {
                Box(contentAlignment = Alignment.TopCenter) {
                    Image(
                        painter = painterResource(id = diceImages[dice2Result - 1]),
                        contentDescription = null,
                        modifier = Modifier
                            .size(160.dp)
                            .padding(4.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.littlecat),
                        contentDescription = null,
                        modifier = Modifier
                            .size(55.dp)
                            .offset(y = (-20).dp, x = 15.dp)
                    )
                }
            }
        }
    }
}