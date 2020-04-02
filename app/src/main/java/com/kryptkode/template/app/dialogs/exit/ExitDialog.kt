package com.kryptkode.template.app.dialogs.exit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kryptkode.template.R
import com.kryptkode.template.app.base.activity.BaseActivity
import com.kryptkode.template.app.base.fragment.viewModels
import com.kryptkode.template.app.di.screen.ScreenComponent
import com.kryptkode.template.app.utils.extensions.beVisibleIf
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.app.utils.sharing.ShareUtils
import com.kryptkode.template.databinding.DialogExitBinding
import javax.inject.Inject


class ExitDialog : BottomSheetDialogFragment() {

    @Inject
    lateinit var shareUtils: ShareUtils

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var baseActivity: BaseActivity

    private val viewModel by viewModels(ExitDialogViewModel::class) { viewModelFactory }

    private lateinit var binding: DialogExitBinding

    var listener: ExitListener? = null

    var unifiedNativeAd : UnifiedNativeAd? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity: BaseActivity = context
            this.baseActivity = activity
        }
        getScreenComponent().inject(this)
    }

    fun getScreenComponent(): ScreenComponent {
        return baseActivity.getScreenComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_exit, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNativeAd()
    }

    private fun initNativeAd() {
        unifiedNativeAd?.let {
            binding.nativeAd.setNativeAd(it)
            binding.nativeAd.setStyles(
                NativeTemplateStyle.Builder()
                    .withCallToActionBackgroundColorInt(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorPrimary
                        )
                    )
                    .build()
            )
        }
    }

    private fun setupObservers() {
        viewModel.getShowRateButton().observe(this) {
            binding.btnRate.beVisibleIf(it ?: false)
        }

        viewModel.getExit().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                dismiss()
                listener?.onExit()
            }
        }

        viewModel.getClose().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                dismiss()
            }
        }

        viewModel.getOpenPlayStore().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                val intent = shareUtils.createRateIntentForGooglePlay()
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog?
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?

        val behavior = BottomSheetBehavior.from(bottomSheet ?: return)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


    interface ExitListener {
        fun onExit()
    }


    companion object {

        fun newInstance( unifiedNativeAd: UnifiedNativeAd, listener: ExitListener): ExitDialog {
            val args = Bundle()
            val fragment = ExitDialog()
            fragment.unifiedNativeAd = unifiedNativeAd
            fragment.listener = listener
            fragment.arguments = args
            return fragment
        }
    }
}