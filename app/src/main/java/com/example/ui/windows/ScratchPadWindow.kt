package com.example.ui.windows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScratchNoteEntity
import com.example.ui.components.BevelStyle
import com.example.ui.components.RetroBevelBox
import com.example.ui.components.RetroButton
import com.example.ui.theme.AmberCrt
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.PhosphorGreen
import com.example.ui.theme.TerminalFontFamily
import com.example.ui.theme.TerminalSurface
import com.example.ui.theme.TerminalTextDim
import com.example.ui.theme.TerminalTextPrimary
import com.example.ui.theme.TerminalTextSecondary

@Composable
fun ScratchPadWindow(
    notes: List<ScratchNoteEntity>,
    onAddNote: (String, String, String) -> Unit,
    onDeleteNote: (ScratchNoteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }
    var newTags by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        // Quick Capture Input Box
        RetroBevelBox(
            modifier = Modifier.fillMaxWidth(),
            style = BevelStyle.SUNKEN,
            backgroundColor = Color(0xFF06090D)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "AUTO-LINKING SCRATCHPAD // PERSISTENT COGNITIVE BUFFER:",
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = AmberCrt
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = newTitle,
                    onValueChange = { newTitle = it },
                    placeholder = { Text("Note Title / Objective...", fontFamily = TerminalFontFamily, fontSize = 10.sp, color = TerminalTextDim) },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = PhosphorGreen,
                        unfocusedTextColor = TerminalTextPrimary,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = newContent,
                    onValueChange = { newContent = it },
                    placeholder = { Text("Ideas, syntax, automated task instructions...", fontFamily = TerminalFontFamily, fontSize = 10.sp, color = TerminalTextDim) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = PhosphorGreen,
                        unfocusedTextColor = TerminalTextPrimary,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newTags,
                        onValueChange = { newTags = it },
                        placeholder = { Text("Tags (e.g. TASKS, INTEL)", fontFamily = TerminalFontFamily, fontSize = 9.sp, color = TerminalTextDim) },
                        modifier = Modifier.weight(1f).height(46.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    RetroButton(
                        text = "+ SAVE NOTE",
                        onClick = {
                            if (newTitle.isNotBlank() || newContent.isNotBlank()) {
                                onAddNote(
                                    if (newTitle.isBlank()) "Untitled Note" else newTitle,
                                    newContent,
                                    newTags
                                )
                                newTitle = ""
                                newContent = ""
                                newTags = ""
                            }
                        },
                        textColor = PhosphorGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Existing Notes List
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(notes, key = { it.id }) { note ->
                RetroBevelBox(
                    modifier = Modifier.fillMaxWidth(),
                    style = BevelStyle.RAISED,
                    backgroundColor = TerminalSurface
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = note.title,
                                fontFamily = TerminalFontFamily,
                                fontSize = 11.sp,
                                color = CyberCyan
                            )
                            RetroButton(
                                text = "PURGE",
                                onClick = { onDeleteNote(note) },
                                textColor = Color(0xFFFF5252)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = note.content,
                            fontFamily = TerminalFontFamily,
                            fontSize = 10.sp,
                            color = TerminalTextPrimary
                        )
                        if (note.tags.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "TAGS: ${note.tags}",
                                fontFamily = TerminalFontFamily,
                                fontSize = 8.sp,
                                color = AmberCrt
                            )
                        }
                    }
                }
            }
        }
    }
}
