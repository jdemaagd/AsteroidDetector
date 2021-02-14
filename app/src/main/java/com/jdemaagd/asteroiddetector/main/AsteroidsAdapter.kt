package com.jdemaagd.asteroiddetector.main

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.jdemaagd.asteroiddetector.R
import com.jdemaagd.asteroiddetector.databinding.ItemAsteroidBinding
import com.jdemaagd.asteroiddetector.models.Asteroid

class AsteroidsAdapter internal constructor(private val clickListener : AsteroidClickListener)
    : ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(DiffCallback) {

    class AsteroidViewHolder(val viewDataBinding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_asteroid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val withDataBinding: ItemAsteroidBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AsteroidViewHolder.LAYOUT, parent, false
        )
        return AsteroidViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.viewDataBinding.also { binding ->
            val asteroidDetails = getItem(position)
            binding.asteroid = asteroidDetails
            binding.asteroidCallback = clickListener

            holder.itemView.contentDescription = "Asteroid $position: ${asteroidDetails.codename} " +
                    "${asteroidDetails.closeApproachDate} potentially hazardous: " +
                    "${asteroidDetails.isPotentiallyHazardous}"        }
    }

    class AsteroidClickListener(val block: (Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = block(asteroid)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
            oldItem.id == newItem.id
    }
}