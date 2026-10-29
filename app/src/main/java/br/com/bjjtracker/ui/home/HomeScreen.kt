package br.com.bjjtracker.ui.home

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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.bjjtracker.ui.theme.BlueAccent
import br.com.bjjtracker.ui.theme.Mint
import br.com.bjjtracker.ui.theme.NavyDark
import br.com.bjjtracker.ui.theme.NavyDeep
import br.com.bjjtracker.ui.theme.SurfaceLight
import br.com.bjjtracker.ui.theme.TextMuted

@Composable
fun HomeScreen(
    state: HomeUiState,
    onAddTraining: () -> Unit,
    onRegisterTraining: () -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = SurfaceLight,
        topBar = { TopBar() },
        bottomBar = {
            BottomNav(selected = selectedTab, onSelect = { selectedTab = it })
        },
    ) { padding ->
        val stats = listOf(
            StatItem(
                icon = Icons.Filled.CalendarToday,
                label = "Treinos este mês",
                value = state.trainingsThisMonth.toString(),
                detail = "+${state.trainingsDeltaVsLastMonth} vs. maio",
                iconTint = Mint,
                iconBackground = Mint.copy(alpha = 0.15f),
            ),
            StatItem(
                icon = Icons.Filled.Schedule,
                label = "Tempo no tatame",
                value = state.timeOnMat,
                detail = "Média de ${state.averageSession}",
                iconTint = BlueAccent,
                iconBackground = BlueAccent.copy(alpha = 0.12f),
            ),
            StatItem(
                icon = Icons.Filled.MenuBook,
                label = "Técnicas salvas",
                value = state.savedTechniques.toString(),
                detail = "",
                iconTint = Color(0xFFE08A3C),
                iconBackground = Color(0xFFE08A3C).copy(alpha = 0.15f),
            ),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                HeroCard(state = state, onRegister = onRegisterTraining)
            }
            items(stats) { stat ->
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
                Text("R", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = "RollTracker",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = NavyDark,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun HeroCard(state: HomeUiState, onRegister: () -> Unit) {
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
                text = "Cada treino conta.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
            )
            Text(
                text = buildSubtitle(state),
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
                Text("Registrar treino", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(24.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ProgressRing(progress = state.progress, label = "${state.progressPercent}%")
            }

            Spacer(Modifier.height(20.dp))

            Spacer(Modifier.height(8.dp))

            Text(
                text = buildRemainingText(state.remaining),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

private fun buildSubtitle(state: HomeUiState) = buildString {
    append("Você já completou")
    append("${state.trainingsDone} de ${state.trainingsGoal} treinos")
    append("rumo ao seu próximo grau.")
}

private fun buildRemainingText(remaining: Int) = "Faltam $remaining treinos para a meta"

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
            "da meta",
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
                    .background(stat.iconBackground),
                contentAlignment = Alignment.Center,
            ) {
                Icon(stat.icon, contentDescription = null, tint = stat.iconTint)
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text(
                    text = stat.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextMuted,
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = stat.value,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark,
                    )
                    if (stat.detail.isNotEmpty()) {
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stat.detail,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNav(selected: Int, onSelect: (Int) -> Unit) {
    val items = listOf(
        "Início" to Icons.Filled.Home,
        "Treinos" to Icons.Filled.Event,
        "Técnicas" to Icons.Filled.MenuBook,
        "Perfil" to Icons.Filled.Person,
    )
    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, (label, icon) ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { onSelect(index) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            state = HomeUiState(),
            onAddTraining = {},
            onRegisterTraining = {},
        )
    }
}
