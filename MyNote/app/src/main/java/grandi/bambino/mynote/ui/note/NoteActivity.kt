package grandi.bambino.mynote.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler

import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.ViewModelProvider
import grandi.bambino.mynote.R
import grandi.bambino.mynote.data.model.Note
import grandi.bambino.mynote.data.model.NoteResult
import grandi.bambino.mynote.getColorInt
import grandi.bambino.mynote.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<NoteViewState.Data, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            context.startActivity(intent)
        }
    }

    override val layoutRes = R.layout.activity_note
    override val model: NoteViewModel by viewModel()
    private var note: Note? = null
    var color = Note.Color.WHITE

    val textChahgeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let {
            model.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note_title)
            initView()
        }
    }

    override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) finish()
        this.note = data.note
        initView()
    }

    fun initView() {
        note?.let { note ->
            removeEditListener()
            if(ed_title.text.toString() != note.title) ed_title.setText(note.title)
            if(et_body.text.toString() != note.text) et_body.setText(note.text)
            color = note.color
            toolbar.setBackgroundColor(note.color.getColorInt(this))
            supportActionBar?.title = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note.lastChanged)
        } ?: let {
            supportActionBar?.title =   getString(R.string.new_note_title)
        }

        setEditListener()

        colorPicker.onColorClickListener = {
            toolbar.setBackgroundColor(color.getColorInt(this))
            color = it
            saveNote()
        }
    }

    private fun removeEditListener(){
        ed_title.removeTextChangedListener(textChahgeListener)
        et_body.removeTextChangedListener(textChahgeListener)
    }

    private fun setEditListener(){
        ed_title.addTextChangedListener(textChahgeListener)
        et_body.addTextChangedListener(textChahgeListener)
    }

    fun saveNote() {
        if (ed_title.text == null || ed_title.text!!.length < 3) return

        note = note?.copy(
            title = ed_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date(),
            color = color
        ) ?: Note(
            UUID.randomUUID().toString(),
            ed_title.text.toString(),
            et_body.text.toString(),
            color
        )

        note?.let {
            model.save(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.note_menu, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.palette -> togglePalette().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.note_delete_message
            negativeButton(R.string.note_delete_cancel) { dialog -> dialog.dismiss() }
            positiveButton(R.string.note_delete_ok) { model.deleteNote() }
        }.show()
    }
}

