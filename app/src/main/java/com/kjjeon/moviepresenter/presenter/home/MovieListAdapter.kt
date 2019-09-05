package com.kjjeon.moviepresenter.presenter.home

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kjjeon.moviepresenter.R
import com.kjjeon.moviepresenter.domain.model.Card
import kotlinx.android.synthetic.main.menu_movie_card.view.*

class MovieListAdapter
 : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies: List<Card> = emptyList()

    fun set(movies: List<Card>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.menu_movie_card,
            parent,
            false
        ))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = movies[position]

        holder.itemView.run {
            Glide.with(this)
                .load(item.image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.terrain_black)
                .fitCenter()
                .into(image)

            title.text = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
//            rating.text = item.userRating.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble().toString()
            rating.text = String.format("%.2f", item.userRating)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}