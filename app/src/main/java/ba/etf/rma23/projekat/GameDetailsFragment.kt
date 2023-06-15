package ba.etf.rma23.projekat

import android.content.Context
import kotlin.random.Random
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.GameData.VideoGames.getAll
import ba.etf.rma23.projekat.GameData.VideoGames.getDetails
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.unsa.rma23.projekat.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import okhttp3.Dispatcher

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
    private lateinit var ratingList : List<GameReview>
    private lateinit var ratings: RecyclerView
    private lateinit var ratingAdapter: ReviewAdapter
    private lateinit var favoriteButton : Button
    private lateinit var removeButton : Button
    private lateinit var usernameEditText: EditText
    private lateinit var reviewEditText: EditText
    private lateinit var ratingEditText: EditText
    private lateinit var addReview : Button


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
        removeButton = view.findViewById(R.id.remove)
        usernameEditText = view.findViewById(R.id.username_editText)
        reviewEditText = view.findViewById(R.id.review_editText)
        ratingEditText = view.findViewById(R.id.rating_editText)
        addReview = view.findViewById(R.id.reviewButton)

        var gid  = 0
        val bundle:Bundle? = arguments
        if (bundle != null) {
            gid = bundle.getInt("id",0)
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

        runBlocking {
           ratingList = GameReviewsRepository.getReviewsForGame(gid)
        }
        ratings.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        //ratingAdapter = ReviewAdapter(game.userImpressions.sortedByDescending { it.timestamp })
        ratingAdapter = ReviewAdapter(ratingList)
        ratings.adapter = ratingAdapter
        ratingAdapter.updateReviews(ratingList)
        //ratingAdapter.updateReviews(game.userImpressions.sortedByDescending { it.timestamp })

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

            addReview.setOnClickListener {
                val username = usernameEditText.text.toString()
                val review = reviewEditText.text.toString()
                val rating = ratingEditText.text.toString().toInt()
                val idReview = getRandomNumberInRange(1,1000)
                val gameReview = GameReview(idReview,rating, review, gid, false, System.currentTimeMillis(), username)
                CoroutineScope(Job() + Dispatchers.Main).launch{
                    val response = GameReviewsRepository.sendReview(requireContext(),gameReview)
                    if (response) onSuccess()
                    else onError()
                }

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
            removeButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    AccountGamesRepository.removeGame(game.id)
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

    private fun getRandomNumberInRange(start: Int, end: Int): Int {
        require(start <= end) { "Invalid range" }

        return Random.nextInt(start, end + 1)
    }

    fun onSuccess() {
        val toast = Toast.makeText(context, "Your review has been added", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onError() {
        val toast = Toast.makeText(context, "There was an error, please try again!", Toast.LENGTH_SHORT)
        toast.show()
    }
}