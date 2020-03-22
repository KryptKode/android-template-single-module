package com.kryptkode.template.categories

import android.content.Context
import android.os.Bundle
import android.view.View
import com.kryptkode.adbase.Ad
import com.kryptkode.adbase.rewardedvideo.Reward
import com.kryptkode.adbase.rewardedvideo.RewardedVideoListener
import com.kryptkode.template.Navigator
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.customviews.SpacesItemDecoration
import com.kryptkode.template.app.dialogs.InfoDialog
import com.kryptkode.template.app.utils.Constants
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.categories.adapter.CategoriesAdapter
import com.kryptkode.template.categories.adapter.CategoriesListener
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.FragmentCategoryBinding
import javax.inject.Inject

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoriesFragment :
    BaseViewModelFragment<FragmentCategoryBinding, CategoriesViewModel>(CategoriesViewModel::class) {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var advert: Ad

    private val categoriesListener = object : CategoriesListener {
        override fun onItemClick(item: CategoryForView?) {
            viewModel.handleCategoryItemClick(item)
        }

        override fun onItemFavoriteClick(isFavorite: Boolean, item: CategoryForView?) {
            viewModel.handleCategoryFavoriteClick(item, isFavorite)
        }
    }

    private val rewardedVideoListener = object : RewardedVideoListener() {
        override fun onRewarded(reward: Reward?) {
            viewModel.onVideoRewarded()
        }
    }

    private val categoriesAdapter = CategoriesAdapter(categoriesListener)

    override fun getLayoutResource() = R.layout.fragment_category

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initAds()
        setupObservers()
    }

    private fun initAds() {
        advert.initBannerAd(binding.adContainer)
        advert.initRewardedVideoAd(rewardedVideoListener)
    }

    private fun initViews() {
        binding.swipe.setOnRefreshListener {
            viewModel.refresh()
        }
        initList()
    }

    private fun initList() {
        binding.categoryRecyclerView.adapter = categoriesAdapter
        binding.categoryRecyclerView.addItemDecoration(SpacesItemDecoration(Constants.LIST_SPACING))
        binding.categoryRecyclerView.setEmptyView(binding.emptyStateLayout.emptyView)
    }

    private fun setupObservers() {
        viewModel.categoriesList.observe(viewLifecycleOwner) {
            categoriesAdapter.submitList(it ?: listOf())
        }

        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.getGoToSubCategoryEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                openSubCategory(it)
            }
        }

        viewModel.getErrorMessageEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                showToast(it)
                binding.emptyStateLayout.emptyViewTv.text = getString(R.string.swipe_down_msg, it)
            }
        }

        viewModel.getShowWatchVideoConfirmDialogEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                openWatchVideoConfirmDialog()
            }
        }

        viewModel.getShowVideoAdEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                showVideoAd()
            }
        }

        viewModel.getShowMessageEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                showToast(getString(it))
            }
        }
    }

    private fun showVideoAd() {
        if (advert.showRewardedVideoAdImmediately()) {
            viewModel.videoAdShown()
        } else {
            viewModel.videoAdNotLoaded()
        }
    }

    private fun openWatchVideoConfirmDialog() {
        val dialogFragment = InfoDialog.newInstance(
            title = getString(R.string.watch_video_dialog_title),
            message = getString(R.string.watch_video_dialog_msg),
            buttonText = getString(R.string.watch_video_dialog_confirm_text),
            hasNeutralButton = true,
            neutralButtonText = getString(R.string.watch_video_dialog_cancel_btn_text),
            listener = object : InfoDialog.InfoListener {
                override fun onConfirm() {
                    viewModel.onWatchVideo()
                }
            }
        )
        dialogFragment.show(childFragmentManager, dialogFragment.javaClass.name)
    }

    private fun openSubCategory(category: CategoryForView) {
        navigator.openSubCategories(category)
    }
}