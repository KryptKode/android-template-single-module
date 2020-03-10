package com.kryptkode.template.startnav

import android.content.Context
import android.os.Bundle
import android.view.View
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.databinding.FragmentStartNavBinding
import com.kryptkode.template.startnav.adapter.StartNavAdapter

/**
 * Created by kryptkode on 3/4/2020.
 */
class StartNavFragment :
    BaseViewModelFragment<FragmentStartNavBinding, StartNavViewModel>(StartNavViewModel::class) {

    private val adapter = StartNavAdapter()

    override fun getLayoutResource() = R.layout.fragment_start_nav

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setEmptyView(binding.emptyStateLayout.rootView)
    }

    private fun initObservers() {
        viewModel.categoryWithSubcategoriesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}