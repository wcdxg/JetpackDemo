package com.yuaihen.jetpack.room

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.yuaihen.jetpack.R
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider(activity!!).get(WordViewModel::class.java)

        buttonSubmit.isEnabled = false

        editTextEnglish.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextEnglish, 0)


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val english = editTextEnglish.text.toString().trim()
                val chinese = editTextChinese.text.toString().trim()
                buttonSubmit.isEnabled = english.isNotEmpty() && chinese.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        editTextEnglish.addTextChangedListener(textWatcher)
        editTextChinese.addTextChangedListener(textWatcher)

        buttonSubmit.setOnClickListener {
            val english = editTextEnglish.text.toString().trim()
            val chinese = editTextChinese.text.toString().trim()
            val word = Word(english, chinese)
            viewModel.insertWord(word)
            Navigation.findNavController(it).navigateUp()

            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onPause() {
        super.onPause()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken,0)
    }

}