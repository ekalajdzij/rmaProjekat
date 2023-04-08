package ba.etf.unsa.rma.videogameproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameDetailsFragment : Fragment() {
    private lateinit var game: Game
    private lateinit var game_title: TextView
    private lateinit var coverImageView: ImageView
    private lateinit var platfrom: TextView
    private lateinit var rdate: TextView
    private lateinit var esrb: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView

    private var games = GameData.getAll()

    private lateinit var ratings: RecyclerView
    private lateinit var ratingAdapter: ReviewAdapter
    private lateinit var home: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_details, container, false)
        game_title = view.findViewById(R.id.item_title_textview)
        platfrom = view.findViewById(R.id.platform_textview)
        rdate = view.findViewById(R.id.release_date_textview)
        esrb = view.findViewById(R.id.esrb_rating_textview)
        coverImageView = view.findViewById(R.id.cover_imageview)
        description = view.findViewById(R.id.description_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)

        ratings = view.findViewById(R.id.review_list)
        ratings.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)


        val extras = activity?.intent?.extras
        if (extras != null) {
            game = GameData.getDetails(extras.getString("title", ""))!!
            populateDetails()
        }
        home = view.findViewById(R.id.home_button)
        home.setOnClickListener {
            val intent =
                Intent(activity, HomeActivity::class.java).apply { putExtra("title", game.title) }
            startActivity(intent)
        }
        ratingAdapter = ReviewAdapter(game.userImpressions.sortedByDescending { it.timestamp })
        ratings.adapter = ratingAdapter
        ratingAdapter.updateReviews(game.userImpressions.sortedByDescending { it.timestamp })

        return view
    }

    private fun populateDetails() {
        game_title.text = game.title
        esrb.text = game.esrbRating
        platfrom.text = game.platform
        rdate.text = game.releaseDate
        developer.text = game.developer
        publisher.text = game.publisher
        description.text = game.description
        genre.text = game.genre
        val context: Context = coverImageView.context
        var id: Int = context.resources.getIdentifier(
            game.title.replace(" ", "").lowercase(),
            "drawable",
            context.packageName
        )
        if (id == 0) id = context.resources.getIdentifier("games", "drawable", context.packageName)
        coverImageView.setImageResource(id)


    }
}