package com.kryptkode.template.subcategories

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kryptkode.adbase.Ad
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.utils.extensions.beVisibleIf
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.cardlist.CardListFragment
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.FragmentSubcategoryBinding
import com.kryptkode.template.subcategories.adapter.SubcategoryFragmentAdapter
import com.kryptkode.template.subcategories.model.SubCategoryForView
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by kryptkode on 3/10/2020.
 */
class SubcategoriesFragment :
    BaseViewModelFragment<FragmentSubcategoryBinding, SubcategoriesViewModel>(SubcategoriesViewModel::class) {

    @Inject
    lateinit var advert:Ad

    private val category by lazy {
        requireArguments().getParcelable<CategoryForView>(ARG_CATEGORY)!!
    }

    private val subcategory by lazy {
        requireArguments().getParcelable<SubCategoryForView>(ARG_SUBCATEGORY)
    }

    private var fragmentAdapter: SubcategoryFragmentAdapter? = null
    private var subcategories: List<SubCategoryForView>? = null
    private var firstTime = true


    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        val favoriteMenuItem = menu.findItem(R.id.action_favorites)
        val currentlySelectedSubCategory = subcategories?.get(binding.viewPager.currentItem)
        currentlySelectedSubCategory?.let {
            favoriteMenuItem.title =
                if (it.favorite) getString(R.string.subcategory_remove_from_favorites) else getString(
                    R.string.subcategory_add_to_favorites
                )
            favoriteMenuItem.setIcon(if (it.favorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite_outline)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_subcategories, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorites) {
            val currentlySelectedSubCategory = subcategories?.get(binding.viewPager.currentItem)
            currentlySelectedSubCategory?.let {
                viewModel.toggleSubcategoryFavorite(it)
                requireActivity().invalidateOptionsMenu()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutResource() = R.layout.fragment_subcategory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initAds()
        setupObservers()
        viewModel.loadSubcategories(category.id)
    }

    private fun initAds() {
        advert.initInterstitialAd()
    }

    private fun initViews() {
        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                advert.showInterstitialAd()
            }
        })
    }

    private fun initTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = subcategories?.get(position)?.name
        }.attach()
    }

    private fun setupObservers() {
        viewModel.subcategoryList.observe(this) {
            Timber.d("LIST: $it")
            subcategories = it
            val cardList = it?.map {
                CardListFragment.newInstance(it)
            } ?: listOf()
            initAdapter(cardList)
            toggleEmptyViewVisibility(it.isNullOrEmpty())
            scrollToTabIfCategoryPresent()
        }

        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                Timber.d("Loading Subcategories... $it")
            }
        }

        viewModel.getErrorMessageEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                showToast(it)
                binding.emptyStateLayout.emptyViewTv.text = getString(R.string.swipe_down_msg, it)
            }
        }
    }

    private fun initAdapter(cardList: List<CardListFragment>) {
        fragmentAdapter = SubcategoryFragmentAdapter(cardList, this)
        binding.viewPager.adapter = fragmentAdapter
        initTabs()
    }

    private fun toggleEmptyViewVisibility(visible: Boolean) {
        binding.emptyStateLayout.emptyView.beVisibleIf(visible)
    }

    private fun scrollToTabIfCategoryPresent() {
        if(firstTime && !subcategories.isNullOrEmpty()){
            subcategory?.let {
                val subcategoryIndex = subcategories?.indexOfFirst { item ->
                    it.id == item.id
                }
                binding.viewPager.currentItem = subcategoryIndex ?: 0
            }
            firstTime = false
        }
    }

    companion object {
        private const val ARG_CATEGORY = "category"
        private const val ARG_SUBCATEGORY = "subcategory"
        fun newInstance(
            category: CategoryForView,
            subcategory: SubCategoryForView? = null
        ): SubcategoriesFragment {
            val fragment = SubcategoriesFragment()
            val args = Bundle()
            args.putParcelable(ARG_CATEGORY, category)
            args.putParcelable(ARG_SUBCATEGORY, subcategory)
            fragment.arguments = args
            return fragment
        }
    }
}