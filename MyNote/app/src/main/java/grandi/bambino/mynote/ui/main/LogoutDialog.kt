package grandi.bambino.mynote.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import grandi.bambino.mynote.R

class LogoutDialog : DialogFragment() {
    companion object{
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context!!)
            .setTitle(R.string.logout_menu_title)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(R.string.logout_dialog_ok) { _, _ ->
                (activity as LogoutListener).onLogout()
            }
            .setNegativeButton(R.string.logout_dialog_cancel) { _, _ ->
                dismiss()

            }
            .create()
    interface LogoutListener {
        fun onLogout()
    }
}