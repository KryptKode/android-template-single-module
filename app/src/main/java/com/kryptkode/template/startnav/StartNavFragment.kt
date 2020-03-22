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
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.FragmentStartNavBinding
import com.kryptkode.template.startnav.adapter.ChildItem
import com.kryptkode.template.startnav.adapter.HeaderItem
import com.kryptkode.template.subcategories.model.SubCategoryForView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import timber.log.Timber
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
        if (item is HeaderItem){
            viewModel.onCategoryClick(item.categoryForView)
        }else if (item is ChildItem) {
            viewModel.onSubCategoryClick(item.categoryForView, item.subCategoryForView)
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
        binding.recyclerView.setEmptyView(binding.emptyStateLayout.emptyView)
    }

    private fun initObservers() {
        viewModel.categoryWithSubcategoriesList.observe(viewLifecycleOwner) {
            adapter.populate(it)
        }

        viewModel.getGoToSubCategoryEvent().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                openSubCategory(it.first, it.second)
            }
        }
        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                Timber.d("Loading... $it")
            }
        }

        viewModel.getErrorMessageEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                showToast(it)
                binding.emptyStateLayout.emptyViewTv.text = getString(R.string.swipe_down_msg, it)
            }
        }
    }

    private fun openSubCategory(categoryForView: CategoryForView, subCategoryForView: SubCategoryForView?=null) {
        navigator.openSubCategories(categoryForView, subCategoryForView)
    }

}