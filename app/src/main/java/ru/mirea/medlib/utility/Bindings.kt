package ru.mirea.medlib.utility

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("android:rating")
fun RatingBar.bindRating(rating: Double) {
    setRating(rating.toFloat())
}

@BindingAdapter("android:visibility")
fun View.bindVisibility(visible: Boolean) {
    isVisible = visible == true
}
