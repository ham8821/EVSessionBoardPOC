package com.example.evsessionboardpoc.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.evsessionboardpoc.data.model.Session
import com.example.evsessionboardpoc.databinding.FragmentMainBinding

class EvSessionsFragment : Fragment(), SessionsView {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root
        initSpinner()
        return root
    }

    private fun initSpinner() {
        val items = arrayOf("All", "NY", "NC", "ND")
        val adapter = NoPaddingDropdownAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items.toMutableList())
        binding.spinner1.adapter = adapter
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): EvSessionsFragment {
            return EvSessionsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun showRefresh() {
        TODO("Not yet implemented")
    }

    override fun showSessions(sessions: List<Session>) {
        TODO("Not yet implemented")
    }

    override fun showLoadingError(message: String) {
        TODO("Not yet implemented")
    }

    override fun showRefreshError(message: String) {
        TODO("Not yet implemented")
    }
}