package ru.eeone.movies.presentation.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.eeone.movies.databinding.FragmentTopMoviesBinding
import ru.eeone.movies.presentation.main.MainActivity
import ru.eeone.movies.presentation.utils.MoviesAdapter
import ru.eeone.movies.presentation.utils.ResponseWrapper

@AndroidEntryPoint
class TopMoviesFragment : Fragment() {
    private var _binding: FragmentTopMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TopMoviesViewModel by viewModels()

    private val adapter = MoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewMovies.adapter = adapter

        adapter.onItemClick = {
            findNavController().navigate(
                TopMoviesFragmentDirections.actionTopMoviesFragmentToMovieDetailsFragment(
                    it
                )
            )
        }

        lifecycleScope.launch {
            viewModel.getTop250Movies()
        }

        viewModel.moviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseWrapper.Loading -> {
                    (requireActivity() as MainActivity).showProgressBar()
                }

                is ResponseWrapper.Error -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is ResponseWrapper.Success -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    adapter.setItems(result.movie ?: listOf())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}