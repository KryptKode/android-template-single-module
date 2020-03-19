package com.kryptkode.template.carddetails

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.app.utils.sharing.ShareUtils
import com.kryptkode.template.carddetails.adapter.CardDetailsAdapter
import com.kryptkode.template.carddetails.adapter.CardDetailsListener
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.FragmentCardDetailBinding
import com.xeenvpn.android.app.dialogs.InfoDialog
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by kryptkode on 3/14/2020.
 */

@RuntimePermissions
class CardDetailFragment :
    BaseViewModelFragment<FragmentCardDetailBinding, CardDetailViewModel>(CardDetailViewModel::class) {

    @Inject
    lateinit var shareUtils: ShareUtils

    private val card by lazy {
        arguments?.getParcelable<CardForView>(ARG_CARD)!!
    }

    private val shareListener = object : ShareUtils.ShareListener {
        override fun onShareError(message: String) {
            showToast(message)
        }
    }

    private val cardDetailsListener = object : CardDetailsListener {
        override fun onShareWhatsAppClick(cardForView: CardForView?) {
            viewModel.onShareWhatsApp(cardForView)
        }

        override fun onShareTwitterClick(cardForView: CardForView?) {
            viewModel.onShareTwitter(cardForView)
        }

        override fun onShareFacebookClick(cardForView: CardForView?) {
            viewModel.onShareFacebook(cardForView)
        }

        override fun onShareOtherAppClick(cardForView: CardForView?) {
            viewModel.onShareOtherApps(cardForView)
        }

        override fun onItemFavoriteClick(isFavorite: Boolean, item: CardForView?) {
            viewModel.toggleFavorite(item, isFavorite)
        }
    }

    private val adapter = CardDetailsAdapter(cardDetailsListener)
    private var firstTime = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun getLayoutResource() = R.layout.fragment_card_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareUtils.listener = shareListener
        initViews()
        setupObservers()
        loadData()
    }

    private fun loadData() {
        viewModel.loadData(card.subcategoryId)
    }

    private fun initViews() {
        initToolbar()
        initSwipeRefresh()
        initViewPager()
    }

    private fun initToolbar() {
        (activity as? CardDetailActivity)?.setSupportActionBar(binding.toolbar)
        (activity as? CardDetailActivity)?.setActionBarTitle(getString(R.string.card_detail_title))
        (activity as? CardDetailActivity)?.showUpEnabled(true)
    }

    private fun initSwipeRefresh() {
        binding.swipe.isEnabled = false
        binding.swipe.setOnRefreshListener {
            viewModel.loadData(card.subcategoryId)
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.cardList.observe(this) {
            adapter.submitList(it)
            scrollToTabIfFirstTimeAndCardPresent(it)
        }

        viewModel.getShareOthersState().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                shareUtils.shareFileOthers(it)
            }
        }

        viewModel.getShareFacebookState().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                shareUtils.shareViaFacebook(it)
            }
        }


        viewModel.getShareWhatsAppState().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                shareUtils.shareViaWhatsApp(it)
            }
        }
        viewModel.getShareTwitterState().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                shareUtils.shareViaTwitter(it)
            }
        }

        viewModel.getAskForStoragePermissionEvent().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                checkStoragePermission()
            }
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun checkStoragePermission() {
        viewModel.onPermissionStorageGranted(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showPermissionsRationale(request: PermissionRequest) {
        val dialogFragment = InfoDialog.newInstance(
            title = getString(R.string.allow_permission),
            message = getString(R.string.allow_permission_msg),
            buttonText = getString(android.R.string.ok),
            listener = object : InfoDialog.InfoListener {
                override fun onConfirm() {
                    request.proceed()
                }
            })
        dialogFragment.show(childFragmentManager, dialogFragment.javaClass.name)
    }

    @OnPermissionDenied(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun showDeniedForPermissions() {
        showToast(getString(R.string.permission_denied))
    }

    @OnNeverAskAgain(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun neverAskForPermissions() {
        openAppSettings()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun scrollToTabIfFirstTimeAndCardPresent(cards: List<CardForView>?) {
        if (firstTime) {
            val cardIndex = cards?.indexOfFirst { item ->
                card.id == item.id
            }
            Timber.d("CARD INDEX: $cardIndex")
            binding.viewPager.setCurrentItem(cardIndex ?: 0, false)
            firstTime = false
        }
    }

    private fun openAppSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }


    companion object {
        private const val ARG_CARD = "arg.card"
        fun newInstance(cardForView: CardForView): CardDetailFragment {
            val fragment = CardDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_CARD, cardForView)
            fragment.arguments = args
            return fragment
        }
    }

}