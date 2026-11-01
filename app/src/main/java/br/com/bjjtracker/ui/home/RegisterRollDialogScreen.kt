package br.com.bjjtracker.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import br.com.bjjtracker.R
import br.com.bjjtracker.ui.home.model.TechnicsEnum
import br.com.bjjtracker.ui.home.model.TrainingItem
import br.com.bjjtracker.ui.home.utils.formatDuration
import br.com.bjjtracker.ui.home.utils.formatDate
import br.com.bjjtracker.ui.home.utils.decreaseDuration
import br.com.bjjtracker.ui.home.utils.increaseDuration
import br.com.bjjtracker.ui.theme.BJJTrackerTheme
import br.com.bjjtracker.ui.theme.Border
import br.com.bjjtracker.ui.theme.Gray
import br.com.bjjtracker.ui.theme.ChipBackground
import br.com.bjjtracker.ui.theme.Navy
import br.com.bjjtracker.ui.theme.GreenBackground
import br.com.bjjtracker.ui.theme.GreenText

@Composable
fun RegisterRollDialogRoute(
    onDismiss: () -> Unit,
    onSave: (TrainingItem) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        RegisterRollDialogScreen(onDismiss = onDismiss, onSave = onSave)
    }
}

@Composable
private fun RegisterRollDialogScreen(
    onDismiss: () -> Unit,
    onSave: (TrainingItem) -> Unit,
) {
    var localDateInstance by rememberSaveable { mutableLongStateOf(LocalDate.now().toEpochDay()) }
    val date = LocalDate.ofEpochDay(localDateInstance)
    var durationMinutes by rememberSaveable { mutableIntStateOf(90) }
    var partners by rememberSaveable { mutableStateOf("") }
    var technic by rememberSaveable { mutableStateOf<TechnicsEnum?>(TechnicsEnum.GUARDA) }

    var showData by remember { mutableStateOf(false) }
    var showTrainingTime by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = stringResource(R.string.register_roll_overline),
                    color = Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.register_roll_title),
                    color = Navy,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .size(36.dp)
                    .background(ChipBackground, CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(R.string.register_roll_close),
                    tint = Navy,
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        TitleTextItem(stringResource(R.string.register_roll_date))
        TextInputItem(Icons.Outlined.CalendarToday, onClick = { showData = true }) {
            Text(formatDate(date), color = Navy, fontSize = 13.sp)
        }

        Spacer(Modifier.height(16.dp))

        TitleTextItem(stringResource(R.string.register_roll_duration))
        TextInputItem(Icons.Outlined.Schedule, onClick = { showTrainingTime = true }) {
            Text(formatDuration(durationMinutes), color = Navy, fontSize = 13.sp)
        }

        Spacer(Modifier.height(16.dp))

        TitleTextItem(stringResource(R.string.register_roll_partners))
        TextInputItem(Icons.Outlined.Group) {
            BasicTextField(
                value = partners,
                onValueChange = { partners = it },
                singleLine = true,
                textStyle = TextStyle(color = Navy, fontSize = 13.sp),
                cursorBrush = SolidColor(Navy),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { textLayout ->
                    if (partners.isEmpty()) {
                        Text(
                            stringResource(R.string.register_roll_partners_hint),
                            color = Gray,
                            fontSize = 13.sp
                        )
                    }
                    textLayout()
                },
            )
        }

        Spacer(Modifier.height(16.dp))

        TitleTextItem(stringResource(R.string.register_roll_focus))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TechnicsEnum.entries.forEach { item ->
                TechnicItem(
                    name = item.label,
                    selected = item == technic,
                    onClick = { technic = if (technic == item) null else item },
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        HorizontalDivider(color = Border)
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
        ) {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ChipBackground,
                    contentColor = Navy,
                ),
                modifier = Modifier.height(44.dp),
            ) {
                Text(
                    stringResource(R.string.calendar_action_cancel),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Button(
                onClick = { onSave(TrainingItem(date, durationMinutes, partners.trim(), technic)) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy,
                    contentColor = Color.White,
                ),
                modifier = Modifier.height(44.dp),
            ) {
                Text(
                    stringResource(R.string.register_roll_save),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    if (showData) {
        SelectorDate(
            initialDate = date,
            onConfirm = { localDateInstance = it.toEpochDay(); showData = false },
            onDismiss = { showData = false },
        )
    }

    if (showTrainingTime) {
        TimeSelector(
            initialMinutes = durationMinutes,
            onConfirm = { durationMinutes = it; showTrainingTime = false },
            onDismiss = { showTrainingTime = false },
        )
    }
}

@Composable
private fun TitleTextItem(text: String) {
    Text(
        text = text,
        color = Gray,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 8.dp),
    )
}

@Composable
private fun TextInputItem(
    icon: ImageVector,
    onClick: (() -> Unit)? = null,
    item: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .border(BorderStroke(1.dp, Border), shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, contentDescription = null, tint = Gray, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            item()
        }
    }
}

@Composable
private fun TechnicItem(name: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) GreenBackground else ChipBackground)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Text(
            text = name,
            color = if (selected) GreenText else Gray,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun SelectorDate(
    initialDate: LocalDate,
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.atStartOfDay(ZoneOffset.UTC).toInstant()
            .toEpochMilli(),
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                state.selectedDateMillis?.let {
                    onConfirm(Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate())
                } ?: onDismiss()
            }) { Text(stringResource(R.string.calendar_action_ok)) }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.calendar_action_cancel)) } },
    ) {
        DatePicker(
            state = state
        )
    }
}

@Composable
private fun TimeSelector(
    initialMinutes: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    var minutes by remember { mutableIntStateOf(initialMinutes) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.register_roll_duration_title)) },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(onClick = { minutes = minutes.decreaseDuration() }) {
                    Text(stringResource(R.string.register_roll_duration_decrease), fontSize = 18.sp)
                }
                Text(formatDuration(minutes), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = { minutes = minutes.increaseDuration() }) {
                    Text(stringResource(R.string.register_roll_duration_increase), fontSize = 18.sp)
                }
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(minutes) }) { Text(stringResource(R.string.calendar_action_ok)) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.calendar_action_cancel)) } },
    )
}

@Preview
@Composable
private fun RegisterRollDialogPreview() {
    BJJTrackerTheme {
        RegisterRollDialogScreen(
            onDismiss = {},
            onSave = {},
        )
    }
}