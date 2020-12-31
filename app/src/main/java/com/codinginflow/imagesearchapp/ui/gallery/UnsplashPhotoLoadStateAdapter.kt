package com.codinginflow.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.imagesearchapp.databinding.UnsplashPhotoLoadStateFooterBinding

//Arrow function when you pass a function with corresponding return type
class UnsplashPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnsplashPhotoLoadStateAdapter.UnsplashPhotoLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): UnsplashPhotoLoadStateViewHolder {
        val binding = UnsplashPhotoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UnsplashPhotoLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnsplashPhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    //Inner to be used only if viewholder is tightly coupled to adapter.
    inner class UnsplashPhotoLoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            //Set on clicks on init because if it's in bind it will be set over and over.
            binding.buttonRetry.setOnClickListener {
                retry.invoke() //call invoke on caller. 
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible =
                    (loadState is LoadState.Loading) //when not loading it will automatically take care.
                buttonRetry.isVisible =
                    (loadState !is LoadState.Loading) //when not loading it will automatically take care.
                textViewError.isVisible =
                    (loadState !is LoadState.Loading) //when not loading it will automatically take care.
            }
        }
    }

}