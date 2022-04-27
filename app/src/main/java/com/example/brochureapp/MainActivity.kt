package com.example.brochureapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brochureapp.domain.entities.Content
import com.example.brochureapp.domain.entities.ContentTypes
import com.example.brochureapp.presentation.ui.RecyclerAdapter
import com.example.brochureapp.presentation.viewmodel.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var gridLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private var contentList: MutableList<Content> = mutableListOf()
    private val viewModel: HomeScreenViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(myToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false);

        val orientation = resources.configuration.orientation
        val totalSpanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
        gridLayoutManager = GridLayoutManager(this, totalSpanCount)

        (gridLayoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (contentList.elementAt(position).contentType == ContentTypes.BrochurePremium.type) {
                        totalSpanCount
                    } else {
                        1
                    }

                }
            }
        recyclerView.layoutManager = gridLayoutManager
        adapter = RecyclerAdapter(contentList)
        recyclerView.adapter = adapter

        observeViewModelChanges()

    }

    private fun observeViewModelChanges() {
        viewModel.getBrochureLists().observe(this) { contents ->
            contentList.clear()
            contentList.addAll(contents)
            adapter.notifyItemRangeInserted(0, contents.size)
        }
        viewModel.getProgressLiveData().observe(this) { isProgress ->
            Log.w("getProgressLiveData", isProgress.toString())
            if (isProgress) progressBar.visibility = View.VISIBLE else progressBar.visibility =
                View.INVISIBLE
        }

    }


}