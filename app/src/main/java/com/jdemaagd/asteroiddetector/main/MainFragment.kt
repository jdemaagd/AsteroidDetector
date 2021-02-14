package com.jdemaagd.asteroiddetector.main

import android.os.Bundle
import android.view.*

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.jdemaagd.asteroiddetector.R
import com.jdemaagd.asteroiddetector.api.repositories.AsteroidRepository
import com.jdemaagd.asteroiddetector.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidsAdapter(AsteroidsAdapter.AsteroidClickListener { asteroid ->
            viewModel.asteroidClicked(asteroid)
        })

        viewModel.navigateToDetails.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { asteroid ->
                findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_week_menu -> viewModel.onApplyFilter(AsteroidRepository.FilterType.WEEKLY)
            R.id.show_saved_menu -> viewModel.onApplyFilter(AsteroidRepository.FilterType.SAVED)
            R.id.show_today_menu -> viewModel.onApplyFilter(AsteroidRepository.FilterType.TODAY)
        }
        return true
    }
}
