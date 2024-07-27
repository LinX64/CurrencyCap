package ui.screens.main.news.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Source
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.expandable
import currencycap.composeapp.generated.resources.source
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import ui.components.base.GlassCard
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun ExpandableSourceButtonRow(
    sources: ImmutableSet<String>,
    selectedSourcesList: (ImmutableList<String>) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSources by remember { mutableStateOf(persistentListOf<String>()) }

    Column {
        GlassCard(
            isClickable = true,
            onCardClick = { isExpanded = !isExpanded }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SPACER_PADDING_8),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Source,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(Res.string.source)
                    )

                    Spacer(modifier = Modifier.width(SPACER_PADDING_8))

                    Text(
                        text = stringResource(Res.string.source),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(Res.string.expandable)
                )
            }
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SPACER_PADDING_8)
                ) {
                    sources.forEach { source ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = source in selectedSources,
                                onCheckedChange = { isChecked ->
                                    selectedSources = if (isChecked) selectedSources.add(source)
                                    else selectedSources.remove(source)

                                    selectedSourcesList(selectedSources)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = source,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = SPACER_PADDING_8)
                            )
                        }
                    }
                }
            }
        }
    }
}