package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.unsa.rma23.projekat.R

class ReviewAdapter(private var impressions: List<GameReview>
):RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rating_view, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int = impressions.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            if (impressions[position].review != null) {
                val comments = impressions[position].review
                holder.review.text = comments
                holder.username.text = impressions[position].userName
                //holder.ratingBar.isVisible = false
            }
            if (impressions[position].rating != null) {
                val number = impressions[position].rating
                holder.ratingBar.rating = number!!.toFloat()
                holder.username.text = impressions[position].userName
                //holder.review.isVisible = false
            }
    }

    fun updateReviews(impressions: List<GameReview>) {
        this.impressions  = impressions
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ratingBar : RatingBar = itemView.findViewById(R.id.rating_bar)
        val username : TextView = itemView.findViewById(R.id.username_textview)
        val review : TextView = itemView.findViewById(R.id.review_textview)
    }
}