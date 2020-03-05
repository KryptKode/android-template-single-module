package com.xeenvpn.android.app.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kryptkode.template.R
import com.kryptkode.template.app.utils.extensions.beVisible

import kotlinx.android.synthetic.main.xeen_dialog_default.*

class InfoDialog : DialogFragment() {
    private var title: String? = null
    private var message: String? = null
    private var buttonText: String? = null
    private var neutralButtonText: String? = null
    private var hasNeutralButton: Boolean? = false
    private var listener: InfoListener? = null
    private var neutralListener: NeutralListener? = null

    companion object {
        private const val TITLE = "title"
        private const val MESSAGE = "message"
        private const val BUTTON_TEXT = "button_text"
        private const val NEUTRAL_BUTTON = "neutral_button"
        private const val NEUTRAL_BUTTON_TEXT = "neutral_button_text"

        @JvmStatic
        fun newInstance(
            title: String?,
            message: String?,
            buttonText: String? = null,
            listener: InfoListener? = null,
            hasNeutralButton: Boolean = false,
            neutralButtonText: String? = null,
            neutralListener: NeutralListener? = null
        ): InfoDialog {
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putString(MESSAGE, message)
            bundle.putString(BUTTON_TEXT, buttonText)
            bundle.putString(NEUTRAL_BUTTON_TEXT, neutralButtonText)
            bundle.putBoolean(NEUTRAL_BUTTON, hasNeutralButton)
            val fragment = InfoDialog()
            fragment.arguments = bundle
            fragment.listener = listener
            fragment.neutralListener = neutralListener
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(TITLE)
        message = arguments?.getString(MESSAGE)
        buttonText = arguments?.getString(BUTTON_TEXT)
        neutralButtonText = arguments?.getString(NEUTRAL_BUTTON_TEXT)
        hasNeutralButton = arguments?.getBoolean(NEUTRAL_BUTTON)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.xeen_dialog_default, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (title != null) {
            main_text.text = title
        }

        if (message == null) {
            secondary_text.visibility = View.GONE
        } else {
            secondary_text.text = message
        }

        if (hasNeutralButton == true) {
            btn_no.beVisible()
            btn_no.text = neutralButtonText
        }

        btn_ok.text = buttonText

        btn_ok.setOnClickListener {
            dismiss()
            listener?.onConfirm()
        }

        btn_no.setOnClickListener {
            dismiss()
            neutralListener?.onNeutralClick()
        }
    }

    interface InfoListener {
        fun onConfirm()
    }

    interface NeutralListener {
        fun onNeutralClick()
    }

}