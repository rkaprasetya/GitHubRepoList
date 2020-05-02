package com.raka.repositorieslistgithub.presentation.repolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.phelat.navigationresult.BundleFragment
import com.raka.repositorieslistgithub.R
import com.raka.repositorieslistgithub.base.BaseViewModel.State.LOADING
import com.raka.repositorieslistgithub.base.BaseViewModel.State.SUCCESS
import com.raka.repositorieslistgithub.data.model.compact.RepoResponseCompact
import com.raka.repositorieslistgithub.databinding.FragmentRepoListBinding
import com.raka.repositorieslistgithub.di.Injectable
import com.raka.repositorieslistgithub.presentation.common.SharedViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.fragment_repo_list.view.*
import javax.inject.Inject

class RepoListFragment : BundleFragment(), Injectable {
    private lateinit var dataBinding: FragmentRepoListBinding
    private lateinit var adapter: RepoListAdapter
    private val compositeDisposable = CompositeDisposable()
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var mView: View
    private var count = 0
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentRepoListBinding.inflate(inflater, container, false).apply {
            viewmodel =
                ViewModelProviders.of(this@RepoListFragment, factory)
                    .get(RepoListViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mView = dataBinding.root
        setSearchButton(mView)
        return mView
    }

    private fun setSearchButton(view: View) {
        view.et_repo_list_search.setOnClickListener {
            //            view.findNavController().navigate(R.id.action_repoListFragment_to_searchRepoFragment)
            val bundle = bundleOf("searchkeyword" to dataBinding.viewmodel!!.searchKeyWord.value)
            navigate(R.id.action_repoListFragment_to_searchRepoFragment, bundle, 10)
        }
    }

    override fun onFragmentResult(requestCode: Int, bundle: Bundle) {
        super.onFragmentResult(requestCode, bundle)
        if (requestCode == 10) {
            val data = bundle.getString("SEARCH_KEYWORDS", "")
            setSearchKeyword(data)
            dataBinding.viewmodel!!.search()
            Log.e("ehe", "ehe $data")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!dataBinding.viewmodel!!.isLoaded.value!!) {
            dataBinding.viewmodel!!.loadRepoDataCA()
        }
        setupAdapter()
        setupObserver()
    }

    private fun setupObserver() {
        dataBinding.viewmodel!!.toastMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfnotHandled()?.let {
                showToast(it)
            }
        })
        dataBinding.viewmodel!!.repoCompactData.observe(viewLifecycleOwner, Observer {
            loadResponse(it)
        })
        dataBinding.viewmodel!!.disposable.observe(viewLifecycleOwner, Observer {
            compositeDisposable.add(it)
        })
    }

    private fun setSearchKeyword(keyword: String) {
        dataBinding.viewmodel!!.searchKeyWord.value = keyword
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun loadResponse(data: RepoResponseCompact) {
        when (data.state) {
            SUCCESS -> {
                showList()
                adapter.updateList(data.data!!)
                hideLoading()
            }
            LOADING -> showLoading()
            else -> {
                showNoData()
                hideLoading()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun showList() {
        repo_list_rv.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pb_repo_list.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pb_repo_list.visibility = View.GONE
    }

    private fun showNoData() {
        tv_repo_list_no_data.visibility = View.VISIBLE
        repo_list_rv.visibility = View.GONE
        showToast("Data Gagal diambil")
    }

    private fun setupAdapter() {
        val viewModel = dataBinding.viewmodel
        if (viewModel != null) {
            adapter = RepoListAdapter(dataBinding.viewmodel!!)
            val layoutManager = LinearLayoutManager(activity)
            repo_list_rv.layoutManager = layoutManager
            repo_list_rv.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    layoutManager.orientation
                )
            )
            repo_list_rv.adapter = adapter
        }
    }
}
