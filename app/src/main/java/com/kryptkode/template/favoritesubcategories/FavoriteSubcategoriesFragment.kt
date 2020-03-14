package com.kryptkode.template.favoritesubcategories

import android.content.Context
import android.os.Bundle
import android.view.View
import com.kryptkode.template.Navigator
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.FragmentFavoriteSubcategoriesBinding
import com.kryptkode.template.favoritesubcategories.adapter.SubcategoriesAdapter
import com.kryptkode.template.favoritesubcategories.adapter.SubcategoriesListener
import com.kryptkode.template.subcategories.model.SubCategoryForView
import javax.inject.Inject

/**
 * Created by kryptkode on 3/13/2020.
 */
class FavoriteSubcategoriesFragment :
    BaseViewModelFragment<FragmentFavoriteSubcategoriesBinding, FavoriteSubcategoriesViewModel>(
        FavoriteSubcategoriesViewModel::class
    ) {

    @Inject
    lateinit var navigator: Navigator

    private val subcategoriesListener = object : SubcategoriesListener {
        override fun onItemClick(item: SubCategoryForView?) {
            viewModel.handleSubcategoryItemClick(item)
        }

        override fun onItemFavoriteClick(isFavorite: Boolean, item: SubCategoryForView?) {
            viewModel.handleSubcategoryFavoriteClick(item, isFavorite)
        }
    }

    private val subcategoriesAdapter = SubcategoriesAdapter(subcategoriesListener)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun getLayoutResource() = R.layout.fragment_favorite_subcategories

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
        binding.recyclerView.adapter = subcategoriesAdapter
        binding.recyclerView.setEmptyView(binding.emptyStateLayout)
    }

    private fun setupObservers() {
        viewModel.favoriteSubcategoryList.observe(viewLifecycleOwner) {
            subcategoriesAdapter.submitList(it ?: listOf())
        }

        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.getGoToSubCategoryEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                openSubCategory(it.first, it.second)
            }
        }
    }

    private fun openSubCategory(category: CategoryForView, subcategory: SubCategoryForView) {
        navigator.openSubCategories(category, subcategory)
    }
}