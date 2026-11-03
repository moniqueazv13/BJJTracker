package br.com.bjjtracker.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import br.com.bjjtracker.R
import br.com.bjjtracker.ui.home.model.BottomNavItemEnum
import br.com.bjjtracker.ui.home.model.StatItem
import br.com.bjjtracker.ui.theme.BJJTrackerTheme
import br.com.bjjtracker.ui.theme.Gray
import br.com.bjjtracker.ui.theme.Mint
import br.com.bjjtracker.ui.theme.NavyDark
import br.com.bjjtracker.ui.theme.NavyDeep
import br.com.bjjtracker.ui.theme.SurfaceLight


@Composable
fun HomeRoute(viewModel: HomeViewModel = koinViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.onAction(HomeAction.LoadHomeInfo)
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showRegisterDialog by remember { mutableStateOf(false) }
    var trainingSaved by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                HomeEvent.TrainingSaved -> {
                    trainingSaved = true
                    showRegisterDialog = false
                    trainingSaved = false
                }
            }
        }
    }

    HomeScreen(
        state = state,
        onRegisterTraining = { showRegisterDialog = true },
    )

    if (showRegisterDialog) {
        RegisterRollDialogRoute(
            onDismiss = { showRegisterDialog = false },
            onSave = { training -> viewModel.onAction(HomeAction.SaveTraining(training)) },
            isSaved = trainingSaved,
        )
    }
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onRegisterTraining: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(BottomNavItemEnum.HOME) }

    Scaffold(
        containerColor = SurfaceLight,
        topBar = { TopBar() },
        bottomBar = {
            BottomNav(selected = selectedTab, onSelect = { selectedTab = it })
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                TrainingCard(state = state, onRegister = onRegisterTraining)
            }
            items(state.stats) { stat ->
                StatCard(stat)
            }
        }
    }
}

@Composable
private fun TopBar() {
    Surface(color = Color.White, shadowElevation = 2.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Mint),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    stringResource(R.string.home_logo_initial),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.home_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = NavyDark,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
private fun TrainingCard(state: HomeUiState, onRegister: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(listOf(NavyDark, NavyDeep)),
                RoundedCornerShape(20.dp),
            ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.home_hero_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
            )
            Text(
                text = stringResource(
                    R.string.home_hero_subtitle,
                    state.trainingsDone,
                    state.trainingsGoal,
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onRegister,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = NavyDark,
                ),
                shape = RoundedCornerShape(14.dp),
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(R.string.home_register_training),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ProgressRing(
                    progress = state.progress,
                    label = stringResource(R.string.home_progress_percent, state.progressPercent)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = pluralStringResource(
                    R.plurals.home_remaining_trainings,
                    state.remaining,
                    state.remaining,
                ),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun ProgressRing(progress: Float, label: String) {
    CircularProgressIndicator(
        progress = { progress },
        modifier = Modifier.size(130.dp),
        color = Mint,
        strokeWidth = 12.dp,
        trackColor = Color.White.copy(alpha = 0.1f),
        strokeCap = StrokeCap.Round,
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
        )
        Text(
            stringResource(R.string.home_progress_of_goal),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun StatCard(stat: StatItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(stat.type.iconBackground),
                contentAlignment = Alignment.Center,
            ) {
                Icon(stat.type.icon, contentDescription = null, tint = stat.type.iconTint)
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text(
                    text = stringResource(stat.type.labelRes),
                    style = MaterialTheme.typography.labelMedium,
                    color = Gray,
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = stat.value,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark,
                    )
                    stat.type.detailRes?.let { detailRes ->
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(detailRes, *stat.detailArgs.toTypedArray()),
                            style = MaterialTheme.typography.labelSmall,
                            color = Gray,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNav(selected: BottomNavItemEnum, onSelect: (BottomNavItemEnum) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        BottomNavItemEnum.entries.forEach { item ->
            val label = stringResource(item.labelRes)
            NavigationBarItem(
                selected = selected == item,
                onClick = { onSelect(item) },
                icon = { Icon(item.icon, contentDescription = label) },
                label = { Text(label) },
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    BJJTrackerTheme {
        HomeScreen(
            state = HomeUiState(
                trainingsDone = 18,
                trainingsGoal = 25,
                trainingsThisMonth = 8,
                trainingsDeltaVsLastMonth = 2,
                timeOnMat = "11h 40m",
                averageSession = "1h 27m",
                savedTechniques = 6,
                technicsSaved = 5,
                favoriteTechnics = 2,
                previousMonthName = "outubro",
            ),
            onRegisterTraining = {},
        )
    }
}
