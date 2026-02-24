package com.example.catdice.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catdice.R
import com.example.catdice.ui.components.DiceBoard
import com.example.catdice.ui.components.DiceHeader
import com.example.catdice.ui.components.HistoryBottomSheet
import com.example.catdice.ui.components.ShakeDetector
import com.example.catdice.ui.viewmodel.DiceViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceScreen(
    viewModel: DiceViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lilacColor = Color(0xFF8182AE)

    val historyList by viewModel.historyList.collectAsState()
    var showHistorySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var pawOffset by remember { mutableFloatStateOf(500f) }
    val animatedPawOffset by animateFloatAsState(
        targetValue = pawOffset,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = 450f),
        label = "PawAnimation"
    )

    val scope = rememberCoroutineScope()

    fun rollDice() {
        if (!viewModel.isRolling) {
            scope.launch {
                viewModel.isRolling = true
                pawOffset = 110f
                delay(150)

                repeat(10) {
                    viewModel.dice1Result = (1..6).random()
                    if (viewModel.diceCount == 2) viewModel.dice2Result = (1..6).random()
                    delay(60)
                }

                val finalDice1 = (1..6).random()
                val finalDice2 = if (viewModel.diceCount == 2) (1..6).random() else null

                viewModel.dice1Result = finalDice1
                if (finalDice2 != null) viewModel.dice2Result = finalDice2

                val total = finalDice1 + (finalDice2 ?: 0)

                viewModel.addRollResult(finalDice1, finalDice2, total)

                delay(200)
                pawOffset = 500f
                delay(300)
                viewModel.isRolling = false
            }
        }
    }

    ShakeDetector(context = context) {
        rollDice()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lilacColor)
            .statusBarsPadding()
    ) {
        DiceHeader(
            diceCount = viewModel.diceCount,
            isRolling = viewModel.isRolling,
            onDiceCountChanged = { viewModel.diceCount = it }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DiceBoard(
                dice1Result = viewModel.dice1Result,
                dice2Result = viewModel.dice2Result,
                diceCount = viewModel.diceCount
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { rollDice() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rollpaw),
                    contentDescription = null,
                    modifier = Modifier.size(180.dp)
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.paw),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = animatedPawOffset.dp)
                .size(500.dp)
        )

        AnimatedVisibility(
            visible = !viewModel.isRolling,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 15.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { showHistorySheet = true }
            ) {
                Text(
                    text = "HISTORY",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.5f))
                        .width(80.dp)
                        .height(6.dp)
                ) { }
            }
        }

        if (showHistorySheet) {
            HistoryBottomSheet(
                historyList = historyList,
                onDismissRequest = { showHistorySheet = false },
                sheetState = sheetState
            )
        }
    }
}