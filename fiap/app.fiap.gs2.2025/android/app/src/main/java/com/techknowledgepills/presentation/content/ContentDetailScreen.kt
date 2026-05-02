package com.techknowledgepills.presentation.content

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.gson.Gson
import com.techknowledgepills.domain.model.ContentType
import com.techknowledgepills.domain.model.Quiz
import com.techknowledgepills.domain.model.QuizQuestion

@Composable
fun ContentDetailScreen(
    contentId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ContentViewModel = hiltViewModel()
) {
    val content by viewModel.content.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(contentId) {
        viewModel.loadContentById(contentId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (content != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Content Type Badge
                val typeIcon = when (content!!.type) {
                    ContentType.Article -> Icons.Default.Article
                    ContentType.Video -> Icons.Default.PlayCircle
                    ContentType.Quiz -> Icons.Default.Quiz
                }
                
                val typeColor = when (content!!.type) {
                    ContentType.Article -> Color(0xFF2196F3)
                    ContentType.Video -> Color(0xFFE91E63)
                    ContentType.Quiz -> Color(0xFF9C27B0)
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = typeIcon,
                        contentDescription = null,
                        tint = typeColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = typeColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = content!!.type.name,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = typeColor
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = content!!.title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                val currentContent = content!!
                val contentType: ContentType = currentContent.type
                when (contentType) {
                    ContentType.Article -> {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = currentContent.body,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    lineHeight = androidx.compose.ui.unit.TextUnit(24f, androidx.compose.ui.unit.TextUnitType.Sp)
                                ),
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                    }
                    ContentType.Video -> {
                        if (currentContent.videoUrl != null) {
                            VideoPlayerComponent(videoUrl = currentContent.videoUrl)
                        } else {
                            Text(
                                text = "No video URL available",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    ContentType.Quiz -> {
                        Text(
                            text = currentContent.body,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (currentContent.quizData != null) {
                            Spacer(modifier = Modifier.height(16.dp))
                            QuizComponent(quizData = currentContent.quizData)
                        }
                    }
                }
            }
        } else {
            Text("Content not found")
        }
    }
}

@Composable
fun QuizComponent(quizData: String) {
    val gson = Gson()
    var quiz by remember { mutableStateOf<Quiz?>(null) }
    var selectedAnswers by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }
    var showResults by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }

    LaunchedEffect(quizData) {
        try {
            quiz = gson.fromJson(quizData, Quiz::class.java)
        } catch (e: Exception) {
            android.util.Log.e("QuizComponent", "Failed to parse quiz data", e)
        }
    }

    if (quiz == null) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Failed to load quiz",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.padding(16.dp)
            )
        }
        return
    }

    Column {
        quiz!!.questions.forEachIndexed { questionIndex, question ->
            val isCorrect = selectedAnswers[questionIndex] == question.correct
            val isAnswered = selectedAnswers[questionIndex] != null
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (showResults && isCorrect) 6.dp else 2.dp
                ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (showResults) {
                        when {
                            isCorrect -> MaterialTheme.colorScheme.primaryContainer
                            isAnswered -> MaterialTheme.colorScheme.errorContainer
                            else -> MaterialTheme.colorScheme.surface
                        }
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${questionIndex + 1}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = question.question,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    question.options.forEachIndexed { optionIndex, option ->
                        val isSelected = selectedAnswers[questionIndex] == optionIndex
                        val isCorrectAnswer = optionIndex == question.correct
                        val showAnswer = showResults && isCorrectAnswer
                        val isWrong = showResults && isSelected && !isCorrectAnswer

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable(enabled = !showResults) {
                                    selectedAnswers = selectedAnswers + (questionIndex to optionIndex)
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = when {
                                showResults && isCorrectAnswer -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                isWrong -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
                                isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                                else -> Color.Transparent
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        if (!showResults) {
                                            selectedAnswers = selectedAnswers + (questionIndex to optionIndex)
                                        }
                                    },
                                    enabled = !showResults,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = if (showResults && isCorrectAnswer) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.primary
                                        }
                                    )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                    ),
                                    modifier = Modifier.weight(1f),
                                    color = if (showResults && isCorrectAnswer) {
                                        MaterialTheme.colorScheme.primary
                                    } else if (isWrong) {
                                        MaterialTheme.colorScheme.error
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    }
                                )
                                if (showResults && showAnswer) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Correct",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else if (isWrong) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Incorrect",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (showResults) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = question.explanation,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!showResults) {
            Button(
                onClick = {
                    showResults = true
                    score = quiz!!.questions.countIndexed { index, _ ->
                        selectedAnswers[index] == quiz!!.questions[index].correct
                    }
                },
                enabled = selectedAnswers.size == quiz!!.questions.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Submit Quiz",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            if (selectedAnswers.size < quiz!!.questions.size) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please answer all ${quiz!!.questions.size} questions",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (score == quiz!!.questions.size) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else if (score >= quiz!!.questions.size / 2) {
                        MaterialTheme.colorScheme.tertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.errorContainer
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = if (score == quiz!!.questions.size) {
                            Icons.Default.EmojiEvents
                        } else if (score >= quiz!!.questions.size / 2) {
                            Icons.Default.ThumbUp
                        } else {
                            Icons.Default.School
                        },
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = if (score == quiz!!.questions.size) {
                            MaterialTheme.colorScheme.primary
                        } else if (score >= quiz!!.questions.size / 2) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Quiz Results",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "$score / ${quiz!!.questions.size}",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${(score * 100 / quiz!!.questions.size)}% Correct",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

inline fun <T> List<T>.countIndexed(predicate: (Int, T) -> Boolean): Int {
    var count = 0
    forEachIndexed { index, element ->
        if (predicate(index, element)) count++
    }
    return count
}

@Composable
fun VideoPlayerComponent(videoUrl: String) {
    val context = LocalContext.current
    
    // Check if it's a YouTube URL
    val isYouTube = videoUrl.contains("youtube.com") || videoUrl.contains("youtu.be")
    
    if (isYouTube) {
        // Extract YouTube video ID
        val videoId = extractYouTubeVideoId(videoUrl)
        if (videoId != null) {
            // Use YouTube embed URL
            val embedUrl = "https://www.youtube.com/embed/$videoId"
            YouTubeVideoPlayer(embedUrl = embedUrl)
        } else {
            Text(
                text = "Invalid YouTube URL: $videoUrl",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    } else {
        // Use ExoPlayer for direct video URLs
        ExoVideoPlayer(videoUrl = videoUrl)
    }
}

@Composable
fun YouTubeVideoPlayer(embedUrl: String) {
    val context = LocalContext.current
    
    AndroidView(
        factory = { ctx ->
            android.webkit.WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                webViewClient = android.webkit.WebViewClient()
                
                // Load YouTube embed with proper HTML
                val html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <style>
                            body { margin: 0; padding: 0; background: #000; }
                            iframe { width: 100%; height: 100%; border: 0; }
                        </style>
                    </head>
                    <body>
                        <iframe 
                            src="$embedUrl?autoplay=0&rel=0&modestbranding=1" 
                            frameborder="0" 
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                            allowfullscreen>
                        </iframe>
                    </body>
                    </html>
                """.trimIndent()
                
                loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    )
}

@Composable
fun ExoVideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = false
            repeatMode = Player.REPEAT_MODE_OFF
        }
    }
    
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = true
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    )
}

fun extractYouTubeVideoId(url: String): String? {
    val patterns = listOf(
        "(?:youtube\\.com\\/watch\\?v=|youtu\\.be\\/)([^&\\n?#]+)",
        "youtube\\.com\\/embed\\/([^&\\n?#]+)",
        "youtube\\.com\\/v\\/([^&\\n?#]+)"
    )
    
    for (pattern in patterns) {
        val regex = Regex(pattern)
        val match = regex.find(url)
        if (match != null) {
            return match.groupValues[1]
        }
    }
    
    return null
}
