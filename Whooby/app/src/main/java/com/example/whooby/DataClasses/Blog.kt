package com.example.whooby.DataClasses

data class Blog(
    val author: Author,
    val coverImageUrl: Any,
    val `data`: Data,
    val description: String,
    val id: Int,
    val profileId: Int,
    val title: String
)