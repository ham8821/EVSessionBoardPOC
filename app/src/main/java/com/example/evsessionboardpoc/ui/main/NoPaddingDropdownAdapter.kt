package com.example.evsessionboardpoc.ui.main

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

open class NoPaddingDropdownAdapter<T>(context: Context, layoutId: Int, items: List<T>) : ArrayAdapter<T>(context, layoutId, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        view.setPadding(view.paddingLeft,0,0,0)
        return view
    }
}