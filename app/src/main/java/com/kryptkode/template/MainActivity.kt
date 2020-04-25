package com.kryptkode.template

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.kryptkode.template.ads.NativeAdHelper
import com.kryptkode.template.app.base.activity.BaseViewModelActivity
import com.kryptkode.template.app.dialogs.ContactUsDialog
import com.kryptkode.template.app.dialogs.InfoDialog
import com.kryptkode.template.app.dialogs.exit.ExitDialog
import com.kryptkode.template.app.utils.AppToastCreator
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.app.utils.inappupdates.InAppUpdateChecker
import com.kryptkode.template.app.utils.rating.RatingManager
import com.kryptkode.template.app.utils.sharing.PlayStoreUtils
import com.kryptkode.template.app.utils.sharing.ShareUtils
import com.kryptkode.template.databinding.ActivityMainBinding
import com.kryptkode.template.databinding.NavEndHeaderBinding
import com.kryptkode.template.startnav.StartNavFragment
import javax.inject.Inject

class MainActivity :
    BaseViewModelActivity<ActivityMainBinding, MainActivityViewModel>(MainActivityViewModel::class) {

    @Inject
    lateinit var ratingManager: RatingManager

    @Inject
    lateinit var shareUtils: ShareUtils

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var nativeAdHelper: NativeAdHelper

    @Inject
    lateinit var appToastCreator: AppToastCreator

    @Inject
    lateinit var playStoreUtils: PlayStoreUtils

    @Inject
    lateinit var inAppUpdateChecker: InAppUpdateChecker

    var doubleBackToExitPressedOnce = false


    override fun getLayoutResourceId() = R.layout.activity_main

    private val contactUsDialogFragmentListener =
        object : ContactUsDialog.OnContactUsDialogFragmentListener {
            override fun onContactUsClicked(subject: String?, message: String?) {
                //simulate sending feedback
                viewModel.sendFeedBack(subject, message)
            }
        }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        getScreenComponent().inject(this)
        super.onCreate(savedInstanceState)
        showRatingDialogIfConditionsAreMet()
        setSupportActionBar(binding.appBarMain.toolbar)
        loadNativeAd()
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_categories
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        initNavigationView()

        setupObservers()
        checkForUpdates()
    }

    private fun checkForUpdates() {
        inAppUpdateChecker.checkForUpdates()
    }

    private fun loadNativeAd() {
        nativeAdHelper.loadNativeAds()
    }

    private fun initNavigationView() {
        initEndNavigationView()
        initStartNavigationView()
    }

    private fun initEndNavigationView() {
        val navEndHeader = NavEndHeaderBinding.inflate(layoutInflater, null, false)
        navEndHeader.viewModel = viewModel
        binding.endNav.addHeaderView(navEndHeader.root)
    }

    private fun initStartNavigationView() {
        initStartNavigationViewFragment()
    }

    private fun initStartNavigationViewFragment() {
        var startNavFragment =
            supportFragmentManager.findFragmentById(R.id.root_start_drawer) as? StartNavFragment
        if (startNavFragment == null) {
            startNavFragment = StartNavFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_start_drawer, startNavFragment)
            .commit()
    }

    private fun setupObservers() {
        viewModel.getToggleEndNavDrawerEvent().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                toggleEndNavDrawer()
            }
        }

        viewModel.getToggleStartNavDrawerEvent().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                toggleStartNavDrawer()
            }
        }

        viewModel.goToFavorites.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                openFavorites()
            }
        }

        viewModel.goToFeedBack.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                openContactUsDialog()
            }
        }

        viewModel.goToLicences.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                openLicences()
            }
        }

        viewModel.goToMoreApps.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                openMoreApps()
            }
        }

        viewModel.goToPrivacyPolicy.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                openPrivacyPolicy()
            }
        }

        viewModel.shareApp.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                shareApp()
            }
        }

        viewModel.rateApp.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                rateApp()
            }
        }
    }

    private fun openPrivacyPolicy() {
        navigator.openPrivacyPolicy()
    }

    private fun openMoreApps() {
        playStoreUtils.openPlayStoreForDeveloper()
    }

    private fun openLicences() {
        navigator.openOssLicences()
    }

    private fun openFavorites() {
        navigator.openFavorites()
    }

    private fun shareApp() {
        startActivity(shareUtils.createShareAppIntent())
    }

    private fun toggleEndNavDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }
    }

    private fun toggleStartNavDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun openContactUsDialog() {
        val dialog = ContactUsDialog.newInstance(contactUsDialogFragmentListener)
        dialog.show(supportFragmentManager, dialog.javaClass.simpleName)
    }

    protected fun showRatingDialogIfConditionsAreMet() {
        ratingManager.monitor()
        ratingManager.setDebug(BuildConfig.DEBUG)
        if (ratingManager.showRateIfMeetsConditions()) {
            val dialogFragment = InfoDialog.newInstance(
                title = getString(R.string.rate_dialog_title),
                message = getString(R.string.rate_dialog_message),
                buttonText = getString(R.string.rate_dialog_ok),
                hasNeutralButton = true,
                neutralButtonText = getString(R.string.rate_dialog_cancel),
                neutralListener = object : InfoDialog.NeutralListener {
                    override fun onNeutralClick() {

                    }
                },
                listener = object : InfoDialog.InfoListener {
                    override fun onConfirm() {
                        rateApp()
                    }
                }
            )
            dialogFragment.show(supportFragmentManager, dialogFragment.javaClass.name)
        }
    }

    private fun rateApp() {
        startActivity(shareUtils.createRateIntentForGooglePlay())
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_more -> {
                viewModel.handleMoreOptionsClick()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            binding.drawerLayout.isDrawerOpen(GravityCompat.END) -> {
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            }
            else -> {
                if (doubleBackToExitPressedOnce) {
                    val nativeAd = nativeAdHelper.getNativeAd()
                    if (nativeAd != null) {
                        val dialog: ExitDialog =
                            ExitDialog.newInstance(nativeAd, object : ExitDialog.ExitListener{
                                override fun onExit() {
                                    this@MainActivity.finish()
                                }
                            })
                        dialog.show(supportFragmentManager, ExitDialog::class.java.simpleName)
                    } else {
                        super.onBackPressed()
                    }
                }
                doubleBackToExitPressedOnce = true
                appToastCreator.showToast(getString(R.string.press_to_exit))
                Handler()
                    .postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdateChecker.onActivityResult(requestCode, resultCode)
    }
}
