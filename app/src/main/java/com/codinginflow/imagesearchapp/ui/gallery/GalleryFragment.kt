package com.codinginflow.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    companion object {
        private const val TAG = "GalleryFragment"
    }

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<GalleryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        setHasOptionsMenu(true)
        val adapter = UnsplashPhotoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter {
                    adapter.retry() //Function of paging 3 to retry.
                },
                footer = UnsplashPhotoLoadStateAdapter {
                    adapter.retry() //Function of paging 3 to retry.
                })
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchActionView =
            menu.findItem(R.id.action_search).actionView as SearchView //Casting important
        searchActionView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0) //Jumps to first since query is different user would have missed some in top for new posts.
                    viewModel.searchPhotos(query)
                    searchActionView.clearFocus()
                }
                return true //Indicates we handled submit button
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true //Do nothing, probably is there for autocomplete but here we return true saying we have handled it.
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}