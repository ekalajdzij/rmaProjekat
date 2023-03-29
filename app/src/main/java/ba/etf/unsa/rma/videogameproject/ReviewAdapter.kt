package ba.etf.unsa.rma.videogameproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private var impressions: List<UserImpression>
):RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rating_view, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int = impressions.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            if (impressions[position] is UserReview) {
                val comments = impressions[position] as UserReview
                holder.review.text = comments.review
                holder.username.text = impressions[position].userName
                holder.ratingBar.isVisible = false
            }
            if (impressions[position] is UserRating) {
                val number = impressions[position] as UserRating
                holder.ratingBar.rating = number.rating.toFloat()
                holder.username.text = impressions[position].userName
                holder.review.isVisible = false
            }
    }

    fun updateReviews(impressions: List<UserImpression>) {
        this.impressions  = impressions
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ratingBar : RatingBar = itemView.findViewById(R.id.rating_bar)
        val username : TextView = itemView.findViewById(R.id.username_textview)
        val review : TextView = itemView.findViewById(R.id.review_textview)
    }
}