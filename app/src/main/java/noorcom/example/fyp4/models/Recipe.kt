package noorcom.example.fyp4.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val recipeName: String = "",
    val ingredients: String = "",
    val instructions: String = "",
    val cookTime: String = "",
    val calories: String = "",
    val difficulty: String = "",
    val imageUrl: String = "",
    val preTime: String = "",
    val totalTime: String = "",
    val servings: String = "",
    val yield: String = ""
) : Parcelable
