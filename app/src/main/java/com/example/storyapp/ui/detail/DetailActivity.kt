package com.example.storyapp.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.title_detail)

        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data: ListStoryItem? = intent.getParcelableExtra(EXTRA_DATA)

        Glide.with(this@DetailActivity)
            .load(data?.photoUrl)
            .into(binding.ivDetailImage)
        binding.content.tvTitle.text = data?.name.toString()
        binding.content.tvDescription.text = data?.description.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}