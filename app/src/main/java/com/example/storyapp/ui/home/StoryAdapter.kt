package com.example.storyapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.databinding.ListStoriesBinding
import com.example.storyapp.ui.detail.DetailActivity

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    class ViewHolder(private val binding: ListStoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.apply {
                tvItemTitle.text = data.name
                tvItemSubtitle.text = data.description

                Glide.with(itemView.context)
                    .load(data.photoUrl)
                    .into(ivItemImage)

                root.setOnClickListener {
                    val moveToDetail = Intent(itemView.context, DetailActivity::class.java)

//                    moveToDetail.putExtra(EXTRA_USER_NAME, data.name)
//                    moveToDetail.putExtra(EXTRA_UPLOAD_DATE, data.createdAt)
//                    moveToDetail.putExtra(EXTRA_CAPTION, data.description)
//                    moveToDetail.putExtra(EXTRA_IMAGE, data.photoUrl)
//
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(ivStory, "story_image"),
//                            Pair(tvUploadDate, "upload_date"),
//                            Pair(tvUserName, "user_name"),
//                            Pair(tvCaption, "caption"),
//                        )

//                    itemView.context.startActivity(moveToDetail, optionsCompat.toBundle())
                }
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}