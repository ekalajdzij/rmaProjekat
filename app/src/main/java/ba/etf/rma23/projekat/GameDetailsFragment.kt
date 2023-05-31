package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.GameData.VideoGames.getAll
import ba.etf.rma23.projekat.GameData.VideoGames.getDetails
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.unsa.rma23.projekat.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.runBlocking

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
    private lateinit var favoriteButton : Button

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
        favoriteButton = view.findViewById(R.id.favoriteButton)

        val bundle:Bundle? = arguments
        if (bundle != null) {
            val gid = bundle.getInt("id",0)
            runBlocking {
                val gameList = GamesRepository.getGameById(gid)
                game = gameList[0]
            }
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

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                favoriteButton.setOnClickListener {
                    runBlocking {
                        AccountGamesRepository.saveGame(game)
                    }
                }
        }


        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navigation: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            navigation.menu.getItem(0).isEnabled = true
            navigation.menu.getItem(1).isEnabled = true
            val homeNavItem: BottomNavigationItemView = navigation.findViewById(R.id.homeItem)
            navigation.findViewById<BottomNavigationItemView>(R.id.gameDetailsItem).isVisible =
                false
            homeNavItem.setOnClickListener {
                val bundle2 = bundleOf("id" to game.id)
                requireView().findNavController().navigate(R.id.action_gameDetails_to_home, bundle2)
            }
            var ima : Boolean = false
            runBlocking {
                val games = AccountGamesRepository.getSavedGames()
                for (i in games.indices) {
                    if (games[i].title == game.title) ima = true;
                }
            }
            if(ima) favoriteButton.isVisible = false;
            if (!ima) {
                favoriteButton.setOnClickListener {
                    runBlocking {
                        AccountGamesRepository.saveGame(game)
                    }
                }
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



        val id = requireContext().resources.getIdentifier("error", "drawable", coverImageView.context.packageName)
        Glide.with(coverImageView.context).
        load("https://"+game.coverImage).
        placeholder(R.drawable.placeholder).error(id).fallback(id).into(coverImageView)


    }

}