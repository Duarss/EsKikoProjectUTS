package com.example.eskikoprojectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eskikoprojectuts.R
import com.example.eskikoprojectuts.databinding.FragmentDataListBinding
import com.example.eskikoprojectuts.viewmodel.DataListViewModel
import com.example.eskikoprojectuts.viewmodel.ListViewModel

class DataListFragment : Fragment() {
    private lateinit var binding: FragmentDataListBinding
    private lateinit var viewModel: DataListViewModel
    private val dataListAdapter = DataListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init the vm
        viewModel = ViewModelProvider(this)[DataListViewModel::class.java]
        observeViewModel()

        // setup recycler view
        binding.recViewData.layoutManager = LinearLayoutManager(context)
        binding.recViewData.adapter = dataListAdapter

        // swipe refresh
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        // obeserve - live data - arraylist student
        viewModel.dataLD.observe(viewLifecycleOwner, Observer {
            dataListAdapter.updateDataList(it)
        })

        // observe - live data - loadingLD
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                // still loading
                binding.progressLoad.visibility = View.VISIBLE
                binding.recViewData.visibility = View.INVISIBLE

            } else {
                // sudah ga loading
                binding.progressLoad.visibility = View.INVISIBLE
                binding.recViewData.visibility = View.VISIBLE

            }
        })

        viewModel.dataLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                // ada error
                binding.txtError.text = "Something wrong when load data"
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.INVISIBLE
            }
        })
    }
}