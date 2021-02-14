package com.jdemaagd.asteroiddetector

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso
import com.jdemaagd.asteroiddetector.main.AsteroidsAdapter
import com.jdemaagd.asteroiddetector.models.Asteroid

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageOfDay")
fun bindImageViewToPictureOfDay(imageView: ImageView, url: String?) {
    Picasso.get().load(url)
        .placeholder(R.drawable.placeholder_image_of_day)
        .error(R.drawable.placeholder_image_of_day)
        .into(imageView)
}

@BindingAdapter("asteroidList")
fun bindRecyclerView(recyclerView: RecyclerView, asteroidList: List<Asteroid>?) {
    val adapter: AsteroidsAdapter = recyclerView.adapter as AsteroidsAdapter
    adapter.submitList(asteroidList)
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}
