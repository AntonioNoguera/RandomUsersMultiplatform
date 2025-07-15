package com.codingambitions.kmmapp1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import data.Login
import data.Name
import data.Picture
import data.RandomUser

// SOLUCIÓN 1: Componente UserCard con fallback para preview
@Composable
fun UserCard(user: RandomUser, isPreview: Boolean = false) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 2.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isPreview) {
                // Para preview: usar un placeholder
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.primary.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${user.name?.first?.firstOrNull()}${user.name?.last?.firstOrNull()}",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary
                    )
                }
            } else {
                // Para app real: usar Coil
                Image(
                    painter = rememberAsyncImagePainter(
                        model = user.picture?.thumbnail,
                        error = painterResource(id = android.R.drawable.ic_menu_gallery)
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }

            Column(verticalArrangement = Arrangement.Center) {
                Text(text = "${user.name?.first} ${user.name?.last}")
                Text(text = user.phone.toString())
            }
        }
    }
}

// SOLUCIÓN 2: Componente específico para preview con placeholders
@Composable
private fun UserCardPreviewComponent(
    firstName: String,
    lastName: String,
    phone: String,
    avatarColor: Color = MaterialTheme.colors.primary
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 2.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar con iniciales
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(avatarColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${firstName.firstOrNull()}${lastName.firstOrNull()}",
                    style = MaterialTheme.typography.h6,
                    color = avatarColor
                )
            }

            Column(
                modifier = Modifier.padding(start = 0.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$firstName $lastName",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = phone,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }
        }
    }
}

// SOLUCIÓN 3: Usar drawable resources (si tienes imágenes locales)
@Composable
fun UserCardWithLocalImage(user: RandomUser) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 2.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_camera), // placeholder system
                contentDescription = "",
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Column(verticalArrangement = Arrangement.Center) {
                Text(text = "${user.name?.first} ${user.name?.last}")
                Text(text = user.phone.toString())
            }
        }
    }
}

// ========================================
// PREVIEWS CON DIFERENTES ENFOQUES
// ========================================

// Preview 1: Usando el componente original con flag isPreview
@Preview(showBackground = true, name = "User Card with Placeholder")
@Composable
fun UserCardPreviewWithPlaceholder() {
    MaterialTheme {
        val sampleUser = RandomUser(
            name = Name(first = "John", last = "Doe"),
            phone = "+1234567890",
            picture = Picture(thumbnail = "https://randomuser.me/api/portraits/thumb/men/1.jpg"),
            login = Login(uuid = "sample-uuid-123")
        )

        UserCard(user = sampleUser, isPreview = true)
    }
}

// Preview 2: Usando componente específico para preview
@Preview(showBackground = true, name = "User Card Preview Component")
@Composable
fun UserCardPreviewSpecific() {
    MaterialTheme {
        UserCardPreviewComponent(
            firstName = "Jane",
            lastName = "Smith",
            phone = "+0987654321",
            avatarColor = Color(0xFF6200EA)
        )
    }
}

// Preview 3: Con drawable system
@Preview(showBackground = true, name = "User Card with System Drawable")
@Composable
fun UserCardPreviewWithDrawable() {
    MaterialTheme {
        val sampleUser = RandomUser(
            name = Name(first = "Bob", last = "Johnson"),
            phone = "+1122334455",
            picture = Picture(thumbnail = ""),
            login = Login(uuid = "sample-uuid-456")
        )

        UserCardWithLocalImage(user = sampleUser)
    }
}

// Preview 4: Lista con diferentes avatares
@Preview(showBackground = true, name = "User List with Avatars", heightDp = 600)
@Composable
fun UserListPreviewWithAvatars() {
    MaterialTheme {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(6) { index ->
                val colors = listOf(
                    Color(0xFF6200EA),
                    Color(0xFF03DAC6),
                    Color(0xFFFF6F00),
                    Color(0xFFE91E63)
                )

                UserCardPreviewComponent(
                    firstName = "User",
                    lastName = "${index + 1}",
                    phone = "+123456789$index",
                    avatarColor = colors[index]
                )
            }
        }
    }
}