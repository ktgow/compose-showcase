package com.joco.composeshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joco.composeshowcase.ui.theme.ComposeShowcaseTheme
import com.joco.showcase.sequence.SequenceShowcase
import com.joco.showcase.sequence.SequenceShowcaseScope
import com.joco.showcase.sequence.SequenceShowcaseState
import com.joco.showcase.sequence.rememberSequenceShowcaseState
import com.joco.showcaseview.AnimationDuration
import com.joco.showcaseview.BackgroundAlpha
import com.joco.showcaseview.ShowcaseAlignment
import com.joco.showcaseview.ShowcasePosition
import com.joco.showcaseview.highlight.ShowcaseHighlight

class HighlightOffsetExampleActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeShowcaseTheme {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Highlight Offset Test") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White
                            ),
                        )
                    }
                ) { innerPadding ->
                    HighlightOffsetTestContent(innerPadding)
                }
            }
        }
    }
}

@Composable
fun HighlightOffsetTestContent(
    innerPadding: PaddingValues
) {
    val sequenceShowcaseState = rememberSequenceShowcaseState()

    // Auto-start the showcase
    LaunchedEffect(Unit) {
        sequenceShowcaseState.start()
    }

    // Simulate AppNavHost structure with additional padding
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding) // First padding layer (from Scaffold)
    ) {
        // Simulate screen-level padding (like PainGraphScreen)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Second padding layer (screen padding)
        ) {
            SequenceShowcase(state = sequenceShowcaseState) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp), // Third padding layer (component padding)
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // Fourth padding layer (content padding)
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Add some content at the top to make padding more visible
                        Text(
                            text = "Testing Multiple Padding Layers",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "This simulates the complex app structure with multiple padding layers that may cause coordinate system mismatches.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Target item 1 - A simple card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .sequenceShowcaseTarget(
                                    index = 0,
                                    //position = ShowcasePosition.Bottom,
                                    alignment = ShowcaseAlignment.CenterHorizontal,
                                    highlight = ShowcaseHighlight.Rectangular(8.dp),
                                    animationDuration = AnimationDuration.Default,
                                    backgroundAlpha = BackgroundAlpha.Normal,
                                    content = { _ ->
                                        HighlightOffsetTestDialog(
                                            text = "Testing highlight offset bug. Check if the highlight is positioned correctly around this card.",
                                            onClick = { sequenceShowcaseState.next() }
                                        )
                                    }
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Highlight Test Card",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "This card tests highlight positioning within a Scaffold. The highlight should align perfectly with the card boundaries.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        // Target item 2 - A simple button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { /* Button action */ },
                                modifier = Modifier
                                    .sequenceShowcaseTarget(
                                        index = 1,
                                        //position = ShowcasePosition.Top,
                                        alignment = ShowcaseAlignment.CenterHorizontal,
                                        highlight = ShowcaseHighlight.Circular(targetMargin = 8.dp),
                                        animationDuration = AnimationDuration.Default,
                                        backgroundAlpha = BackgroundAlpha.Normal,
                                        content = { _ ->
                                            HighlightOffsetTestDialog(
                                                text = "Testing circular highlight offset. Verify the circular highlight centers correctly on this button.",
                                                onClick = { sequenceShowcaseState.dismiss() }
                                            )
                                        }
                                    )
                            ) {
                                Text("Test Button")
                            }
                        }

                        // Some additional content to make the screen more interesting
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Reference Content",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "This card is not a showcase target. It provides visual context to help identify any highlight positioning issues.",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HighlightOffsetTestDialog(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Got it!")
            }
        }
    }
}
