package com.kjjeon.moviepresenter.presenter.home

import android.os.Bundle
import com.kjjeon.moviepresenter.databinding.ActivityMainBinding
import com.kjjeon.moviepresenter.presenter.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.Menu
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kjjeon.moviepresenter.extension.observe
import androidx.recyclerview.widget.RecyclerView
import com.kjjeon.moviepresenter.R
import com.kjjeon.moviepresenter.presenter.custom.EndlessRecyclerViewScrollListener
import timber.log.Timber


class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val dataBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater, null, true)
    }
    private val adapter: MovieListAdapter by lazy { dataBinding.list.adapter as MovieListAdapter }
    private lateinit var endlessRecyclerViewScrollListener : EndlessRecyclerViewScrollListener

//    private val movieListAdapter by lazy { MovieListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(dataBinding.root)
        setSupportActionBar(dataBinding.toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        dataBinding.viewModel = mainViewModel

        endlessRecyclerViewScrollListener = object :  EndlessRecyclerViewScrollListener(
            dataBinding.list.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Timber.d("page = $page , totalItemsCount = $totalItemsCount, RecyclerView = $view")
                mainViewModel.queryNext(page)
            }
        }
        dataBinding.list.addOnScrollListener(endlessRecyclerViewScrollListener)
        dataBinding.list.adapter = MovieListAdapter()

        mainViewModel.cardListLiveData.observe(this) {
            Timber.d("cardListLiveData = $it")
            adapter.set(it.second)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.stop()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView

        searchView.findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)
            .setBackgroundResource(R.drawable.abc_textfield_search_default_mtrl_alpha)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    endlessRecyclerViewScrollListener.resetState()
                    mainViewModel.newQuery(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return true
    }




}

