package com.kryptkode.template.favoritecategories

import android.content.Context
import android.os.Bundle
import android.view.View
import com.kryptkode.template.Navigator
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.customviews.SpacesItemDecoration
import com.kryptkode.template.app.utils.Constants
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.categories.adapter.CategoriesAdapter
import com.kryptkode.template.categories.adapter.CategoriesListener
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.FragmentFavoriteCategoriesBinding
import javax.inject.Inject

/**
 * Created by kryptkode on 3/13/2020.
 */
class FavoriteCategoriesFragment : BaseViewModelFragment<FragmentFavoriteCategoriesBinding, FavoriteCategoriesViewModel>(FavoriteCategoriesViewModel::class) {

    @Inject
    lateinit var navigator: Navigator

    private val categoriesListener = object : CategoriesListener {
        override fun onItemClick(item: CategoryForView?) {
            viewModel.handleCategoryItemClick(item)
        }

        override fun onItemFavoriteClick(isFavorite: Boolean, item: CategoryForView?) {
            viewModel.handleCategoryFavoriteClick(item, isFavorite)
        }
    }

    val categoriesAdapter = CategoriesAdapter(categoriesListener)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun getLayoutResource() = R.layout.fragment_favorite_categories

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun initViews() {
        binding.swipe.setOnRefreshListener {
            viewModel.refresh()
        }
        initList()
    }

    private fun initList() {
        binding.recyclerView.adapter = categoriesAdapter
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(Constants.LIST_SPACING))
        binding.recyclerView.setEmptyView(binding.emptyStateLayout.emptyView)
    }

    private fun setupObservers() {
        viewModel.favoriteCategoryList.observe(viewLifecycleOwner) {
            categoriesAdapter.submitList(it ?: listOf())
        }

        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.getGoToSubCategoryEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                openSubCategory(it)
            }
        }

        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.getErrorMessageEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                showToast(it)
                binding.emptyStateLayout.emptyViewTv.text = getString(R.string.swipe_down_msg, it)
            }
        }
    }

    private fun openSubCategory(category: CategoryForView) {
        navigator.openSubCategories(category)
    }
}