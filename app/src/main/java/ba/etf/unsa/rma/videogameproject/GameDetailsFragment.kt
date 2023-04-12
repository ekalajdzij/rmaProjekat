package ba.etf.unsa.rma.videogameproject

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getAll
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getDetails
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.navigation.NavigationView

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
    private lateinit var ratings: RecyclerView
    private lateinit var ratingAdapter: ReviewAdapter

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

        val bundle:Bundle? = arguments
        if (bundle != null) {
            game = getDetails(bundle.getString("title", ""))!!
            populateDetails()
        } else {
            val all = getAll()
            game = all[0]
            populateDetails()
        }

        ratings.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        ratingAdapter = ReviewAdapter(game.userImpressions.sortedByDescending { it.timestamp })
        ratings.adapter = ratingAdapter
        ratingAdapter.updateReviews(game.userImpressions.sortedByDescending { it.timestamp })

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navigation: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            navigation.menu.getItem(0).isEnabled = true
            navigation.menu.getItem(1).isEnabled = true
            val homeNavItem: BottomNavigationItemView = navigation.findViewById(R.id.homeItem)
            navigation.findViewById<BottomNavigationItemView>(R.id.gameDetailsItem).isVisible =
                false
            homeNavItem.setOnClickListener {
                val bundle = bundleOf("title" to game.title)
                requireView().findNavController().navigate(R.id.action_gameDetails_to_home, bundle)
            }
        }


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