package com.kryptkode.template.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.utils.extensions.beVisibleIf
import com.kryptkode.template.databinding.FragmentFavoritesBinding
import com.kryptkode.template.favoritecards.FavoriteCardsFragment
import com.kryptkode.template.favoritecategories.FavoriteCategoriesFragment
import com.kryptkode.template.favorites.adapter.FavoritesFragmentAdapter

/**
 * Created by kryptkode on 3/13/2020.
 */
class FavoritesFragment : BaseViewModelFragment<FragmentFavoritesBinding, FavoritesViewModel>(FavoritesViewModel::class) {

    private val fragments = listOf(FavoriteCategoriesFragment(), FavoriteCardsFragment())
    private val fragmentsTitles  by lazy {
        listOf(getString(R.string.favorite_title_categories), getString(R.string.favorite_title_cards))
    }
    private val adapter by lazy {
        FavoritesFragmentAdapter(fragments, this)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun getLayoutResource() = R.layout.fragment_favorites

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 3
        initTabs()
        toggleEmptyViewVisibility(fragments.isNullOrEmpty())
    }

    private fun initTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentsTitles[position]
        }.attach()
    }

    private fun toggleEmptyViewVisibility(visible:Boolean){
        binding.emptyStateLayout.emptyView.beVisibleIf(visible)
    }
}