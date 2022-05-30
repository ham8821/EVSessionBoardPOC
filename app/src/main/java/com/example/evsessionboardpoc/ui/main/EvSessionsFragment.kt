package com.example.evsessionboardpoc.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evsessionboardpoc.EVApplication
import com.example.evsessionboardpoc.data.model.Session
import com.example.evsessionboardpoc.databinding.FragmentMainBinding
import com.example.evsessionboardpoc.di.SessionsModule
import com.example.evsessionboardpoc.presenter.EVPresenter
import com.example.evsessionboardpoc.presenter.EVPresenterFactory
import com.example.evsessionboardpoc.presenter.PresenterProviders
import java.util.HashMap
import javax.inject.Inject

class EvSessionsFragment : Fragment(), SessionsView {

    @Inject
    lateinit var presenterFactory: EVPresenterFactory

    private lateinit var presenter: EVPresenter

    private val sessionAdapter = SessionsAdapter(TYPE_SESSIONS)
    private val summaryAdapter = SessionsAdapter(TYPE_SUMMARY)

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        presenter = PresenterProviders.of(this, presenterFactory).get(EVPresenter::class.java)
        presenter.attachView(this, lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initSpinner()
        presenter.loadSessions()
    }
    private fun injectDependencies() {
        val app = activity?.application as EVApplication
        app.getComponent()
            .plus(SessionsModule())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }


    private fun setupRecyclerView() {

        binding.recyclerviewSessions.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recyclerviewSessions.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewSessions.adapter = sessionAdapter

        binding.recyclerviewSummary.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.recyclerviewSummary.adapter = summaryAdapter
    }

    private fun initSpinner() {
        val items = arrayOf("All", "NY", "NC", "ND")
        val adapter = NoPaddingDropdownAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items.toMutableList())
        binding.spinner1.adapter = adapter
    }

    companion object {

        const val TYPE_SESSIONS = "adapter_sessions"
        const val TYPE_SUMMARY = "adapter_summary"
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
        binding.progressCircular.visibility = View.VISIBLE
        binding.constraintLayout.visibility = View.GONE
        binding.sessionsRow.visibility = View.GONE
        binding.summaryRow.visibility = View.GONE
        TODO("Not yet implemented")
    }

    override fun showRefresh() {
        TODO("Not yet implemented")
    }

    override fun showSessions(sessions: HashMap<String, List<Session>>) {
        binding.constraintLayout.visibility = View.VISIBLE
        binding.sessionsRow.visibility = View.VISIBLE
        binding.summaryRow.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE
        sessionAdapter.updateSessions(sessions)
        summaryAdapter.updateSummaryItems(sessions)
    }

    override fun showLoadingError(message: String) {
        TODO("Not yet implemented")
    }

    override fun showRefreshError(message: String) {
        TODO("Not yet implemented")
    }
}