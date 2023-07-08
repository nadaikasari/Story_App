package com.example.storyapp.data.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class StoriesResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem>,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Entity(tableName = "stories")
data class ListStoryItem(

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("lon")
	val lon: Double? = null

)
