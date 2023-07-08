package com.example.storyapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.adapter.LoadingStateAdapter
import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.ui.addstory.AddStoryActivity
import com.example.storyapp.ui.login.LoginActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var storyAdapter: StoryAdapter

    private val viewModel by viewModels<MainViewModel>()
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        showRecyclerList()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_story -> {
                val intent = Intent(this, AddStoryActivity::class.java)
                startActivity(intent)
                true
            } R.id.logout -> {
                userViewModel.logout()
                true
            }
            else -> true
        }
    }

    private fun setupViewModel() {
        userViewModel.getSession().observe(this) {
            if (!it.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                        viewModel.listStory.observe(this@MainActivity) { data ->
                            updateRecyclerViewData(data)
                        }
                    }
                }
            }
        }

        viewModel.listStory.observe(this) {
            storyAdapter.submitData(lifecycle, it)
        }

        viewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun showRecyclerList() {
        storyAdapter = StoryAdapter()
        recyclerView = binding.rvStories

        recyclerView.apply {
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
            val layoutManager = LinearLayoutManager(context)
            binding.rvStories.layoutManager = layoutManager
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun updateRecyclerViewData(stories: PagingData<ListStoryItem>) {
        val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
        storyAdapter.submitData(lifecycle, stories)
        recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }
}