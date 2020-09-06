package grandi.bambino.mynote.ui.main



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import grandi.bambino.mynote.R
import grandi.bambino.mynote.data.model.Note
import grandi.bambino.mynote.ui.base.BaseActivity
import grandi.bambino.mynote.ui.note.NoteActivity
import grandi.bambino.mynote.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val model: MainViewModel by viewModel()

    override val layoutRes = R.layout.activity_main
    lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = RecyclerViewAdapter { note ->
            NoteActivity.start(this, note.id)
        }

        rv_notes.adapter = adapter
        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) =
        MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logout -> showLogoutDialog()?.let { true }
        else -> false
    }

    private fun showLogoutDialog() {
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.logout_dialog_ok) { onLogout() }
            negativeButton(R.string.logout_dialog_cancel) { dialog -> dialog.dismiss() }
        }.show()
    }

    private fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

}
