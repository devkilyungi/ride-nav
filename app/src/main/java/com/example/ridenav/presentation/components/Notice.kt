package com.example.ridenav.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridenav.common.advancedShadow

@Composable
fun Notice(
    visible: Boolean,
    header: String,
    content: String,
    primaryButtonText: String,
    secondaryButtonText: String? = null,
    primaryOnClick: () -> Unit,
    secondaryOnClick: () -> Unit = {}
) {
    AnimatedVisibility(
        visible,
        enter = slideInVertically(
            animationSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = .6f)
        ) { 300 } + fadeIn(),
        exit = slideOutVertically(animationSpec = tween(250, easing = EaseIn)) { 300 } + fadeOut()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                elevation = CardDefaults.cardElevation(0.5.dp),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(300.dp)
                    .align(Alignment.Center)
                    .advancedShadow(cornersRadius = 16.dp, alpha = 0.25F, shadowBlurRadius = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = header,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                    Text(
                        content,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        if (!secondaryButtonText.isNullOrEmpty()) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            TextButton(
                                onClick = secondaryOnClick,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(vertical = 8.dp),
                            ) {
                                Text(
                                    secondaryButtonText,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        TextButton(
                            onClick = primaryOnClick,
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                        ) {
                            Text(primaryButtonText, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
