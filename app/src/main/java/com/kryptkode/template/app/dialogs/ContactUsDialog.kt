package com.kryptkode.template.app.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.kryptkode.template.R

/**
 * Created by Cyberman on 10/7/2017.
 */
class ContactUsDialog : DialogFragment(), TextWatcher {
    private var messageEditText: EditText? = null
    private var subjectEditText: EditText? = null
    private var dialog: AlertDialog? = null
    private var listener: OnContactUsDialogFragmentListener? = null

    interface OnContactUsDialogFragmentListener {
        fun onContactUsClicked(subject: String?, message: String?)
    }

    fun setListener(listener: OnContactUsDialogFragmentListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = requireActivity().layoutInflater
        val view = layoutInflater.inflate(R.layout.xeen_dialog_contact, null)
        messageEditText = view.findViewById<View>(R.id.contact_message) as EditText
        subjectEditText = view.findViewById<View>(R.id.contact_subjectt) as EditText
        messageEditText!!.addTextChangedListener(this)
        subjectEditText!!.addTextChangedListener(this)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        builder.setPositiveButton(R.string.contact_us_dialog_positive_text) { _: DialogInterface?, _: Int ->
            listener!!.onContactUsClicked(subjectEditText!!.text.toString(), messageEditText!!.text.toString())
            dismiss()
        }
        builder.setNegativeButton(R.string.contact_us_close_dialog) { _: DialogInterface?, _: Int -> dismiss() }
        dialog = builder.create()
        return dialog!!
    }

    override fun onStart() {
        super.onStart()
        dialog!!.getButton(Dialog.BUTTON_POSITIVE).isEnabled = false
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun afterTextChanged(editable: Editable) {
        dialog!!.getButton(Dialog.BUTTON_POSITIVE).isEnabled = !TextUtils.isEmpty(messageEditText!!.text.toString()) &&
                !TextUtils.isEmpty(subjectEditText!!.text.toString())
    }

    companion object {
        fun newInstance(listener: OnContactUsDialogFragmentListener?): ContactUsDialog {
            val args = Bundle()
            val fragment = ContactUsDialog()
            fragment.setListener(listener)
            fragment.arguments = args
            return fragment
        }
    }
}