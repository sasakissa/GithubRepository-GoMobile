package com.sasakissa.githubsample.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.sasakissa.githubsample.R
import com.sasakissa.githubsample.databinding.ActivityMainBinding

class SearchRepoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val listView = binding.listView
        val adapter = SearchRepoAdapter()
        listView.layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayoutManager.VERTICAL }
        listView.adapter = adapter


        val viewModel = ViewModelProviders.of(this).get(SearchRepoViewModel::class.java)
        viewModel.items.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.isError.observe(this, Observer {
            if (it != null && it) {
                Toast.makeText(this, "通信エラー", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.isLoading.observe(this, Observer {
            it?.let { binding.isLoading = it }
        })


        binding.input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(binding.input.editableText.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }
}
