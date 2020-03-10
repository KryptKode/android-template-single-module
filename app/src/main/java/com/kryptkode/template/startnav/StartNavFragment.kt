package com.kryptkode.template.startnav

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kryptkode.template.Navigator
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.customviews.SpacesItemDecoration
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.app.utils.extensions.populate
import com.kryptkode.template.databinding.FragmentStartNavBinding
import com.kryptkode.template.startnav.adapter.ChildItem
import com.kryptkode.template.subcategories.model.SubCategoryForView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import javax.inject.Inject

/**
 * Created by kryptkode on 3/4/2020.
 */
class StartNavFragment :
    BaseViewModelFragment<FragmentStartNavBinding, StartNavViewModel>(StartNavViewModel::class) {

    @Inject
    lateinit var navigator: Navigator

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val onItemClickListener = OnItemClickListener { item, _ ->
        if (item is ChildItem) {
            viewModel.onSubCategoryClick(item.subCategoryForView)
        }
    }

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
        val groupLayoutManager = GridLayoutManager(context, adapter.spanCount).apply {
            spanSizeLookup = adapter.spanSizeLookup
        }
        adapter.setOnItemClickListener(onItemClickListener)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(4))
        binding.recyclerView.layoutManager = groupLayoutManager
        binding.recyclerView.setEmptyView(binding.emptyStateLayout)
    }

    private fun initObservers() {
        viewModel.categoryWithSubcategoriesList.observe(viewLifecycleOwner) {
            adapter.populate(it)
        }

        viewModel.getGoToSubCategoryEvent().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                openSubCategory(it)
            }
        }
    }

    private fun openSubCategory(subCategory: SubCategoryForView) {
        navigator.openSubCategories(subCategory)
    }

}