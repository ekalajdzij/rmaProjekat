package ba.etf.unsa.rma.videogameproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getAll
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getDetails

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var game : Game
    private lateinit var game_title : TextView
    private lateinit var coverImageView: ImageView
    private lateinit var platfrom: TextView
    private lateinit var rdate: TextView
    private lateinit var esrb: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView

    private var games = getAll()

    private lateinit var ratings : RecyclerView
    private lateinit var ratingAdapter: ReviewAdapter
    private lateinit var home : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_details_activity)
        game_title = findViewById(R.id.item_title_textview)
        platfrom = findViewById(R.id.platform_textview)
        rdate = findViewById(R.id.release_date_textview)
        esrb = findViewById(R.id.esrb_rating_textview)
        coverImageView = findViewById(R.id.cover_imageview)
        description = findViewById(R.id.description_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)

        ratings = findViewById(R.id.review_list)
        ratings.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val extras = intent.extras
        if (extras != null) {
            game = getDetails(extras.getString("title", ""))!!
            populateDetails()
        }
        home = findViewById(R.id.home_button)
        home.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java).apply { putExtra("title",game.title) }
            startActivity(intent)
        }
        ratingAdapter = ReviewAdapter(game.userImpressions.sortedByDescending { it.timestamp })
        ratings.adapter = ratingAdapter
        ratingAdapter.updateReviews(game.userImpressions.sortedByDescending { it.timestamp })

    }

    private fun populateDetails() {
        game_title.text=game.title
        esrb.text= game.esrbRating
        platfrom.text = game.platform
        rdate.text = game.releaseDate
        developer.text = game.developer
        publisher.text = game.publisher
        description.text = game.description
        genre.text = game.genre
        val context: Context = coverImageView.context
        var id: Int = context.resources.getIdentifier(game.title.replace(" ","").lowercase(), "drawable", context.packageName)
        if (id == 0) id = context.resources.getIdentifier("games", "drawable", context.packageName)
        coverImageView.setImageResource(id)


    }
}