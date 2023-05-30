package ba.etf.rma23.projekat
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.GameData.VideoGames.getAll
import ba.etf.rma23.projekat.GameData.VideoGames.getDetails
import ba.etf.unsa.rma23.projekat.R
import ba.etf.rma23.projekat.repositories.GamesRepository.getGamesByName
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

class HomeFragment : Fragment() {
    private lateinit var gameView: RecyclerView
    private lateinit var gameAdapter: VideoGameAdapter
    private lateinit var searchButton: Button
    private lateinit var searchText: EditText
    private var games = getAll()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        gameView = view.findViewById(R.id.game_list)

        searchText = view.findViewById(R.id.search_query_edittext)
        searchButton = view.findViewById(R.id.search_button)

        gameView.layoutManager = LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
        gameAdapter = VideoGameAdapter(games) { game -> showGameDetails(game) }
        gameView.adapter = gameAdapter
        gameAdapter.updateGames(games)

        searchButton.setOnClickListener {
            val query = searchText.text.toString().trim()
            if (query.isNotEmpty()) {
                onClick(query)
            }
        }

        val bundle: Bundle? = arguments
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navigation: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            val detailsNavItem: BottomNavigationItemView = navigation.findViewById(R.id.gameDetailsItem)
            if (bundle?.getString("title") == null) {
                navigation.menu.getItem(0).isEnabled = false
                navigation.menu.getItem(1).isEnabled = false
            }
            navigation.menu.getItem(0).isEnabled = false
            detailsNavItem.setOnClickListener {
                var game = getDetails(bundle!!.getString("title", ""))
                showGameDetails(game!!)
            }
            return view
        }

        return view
    }
    private fun onClick(query: String) {
        val toast = Toast.makeText(context, "Search start", Toast.LENGTH_SHORT)
        toast.show()
        searchGames(query,onSuccess = ::onSuccess, onError = ::onError)
    }

    private fun searchGames(
        query: String,
        onSuccess: KFunction1<List<Game>, Unit>,
        onError: KFunction0<Unit>, ) {
            lifecycleScope.launch {
                try {
                    val games = getGamesByName(query)
                    //gameAdapter.updateGames(games)
                    onSuccess(games)
                } catch (e: Exception) {
                    onError()
                }
            }
    }
    fun onSuccess(games:List<Game>){
        val toast = Toast.makeText(context, "Game found", Toast.LENGTH_SHORT)
        toast.show()
        gameAdapter.updateGames(games)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showGameDetails(game: Game) {
        val bundle = bundleOf("title" to game.title)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val homeFragment = HomeFragment()
            val gameDetailsFragment = GameDetailsFragment()
            gameDetailsFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_right, gameDetailsFragment)
                .commit()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_left, homeFragment)
                .commit()
        } else {
            requireView().findNavController().navigate(R.id.action_home_to_gameDetails, bundle)
        }
    }
}


/*
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.unsa.rma23.projekat.GameData.VideoGames.getAll
import ba.etf.unsa.rma23.projekat.GameData.VideoGames.getDetails
import ba.etf.unsa.rma23.projekat.R.*
import ba.etf.unsa.rma23.projekat.repositories.IGDBApiConfig
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {
    private lateinit var gameView: RecyclerView
    private lateinit var gameAdapter: VideoGameAdapter
    //////////////////////////////////////////////
    private var games = getAll()
    //private var games = ApiAdapter.retrofit.getGames()

    private lateinit var searchButton: Button
    private lateinit var searchText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout.fragment_home, container, false)

        gameView = view.findViewById(R.id.game_list)

        //spirala3
        searchText = view.findViewById(R.id.search_query_edittext)
        searchButton = view.findViewById(R.id.search_button)
        arguments?.getString("search")?.let { searchText.setText(it) }




        gameView.layoutManager = LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
        gameView.layoutManager = LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
        gameAdapter = VideoGameAdapter(games) { game -> showGameDetails(game) }
        gameView.adapter = gameAdapter
        gameAdapter.updateGames(games)

        val bundle: Bundle? = arguments
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navigation: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            val detailsNavItem: BottomNavigationItemView =
                navigation.findViewById(R.id.gameDetailsItem)
            if (bundle?.getString("title") == null) {
                navigation.menu.getItem(0).isEnabled = false
                navigation.menu.getItem(1).isEnabled = false
            }
            navigation.menu.getItem(0).isEnabled = false
            detailsNavItem.setOnClickListener {
                var game = getDetails(bundle!!.getString("title", ""))
                showGameDetails(game!!)
            }
            return view
        }

        return view
    }

    private fun showGameDetails(game: Game) {
        val bundle = bundleOf("title" to game.title)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val homeFragment = HomeFragment()   /////
            val gameDetailsFragment = GameDetailsFragment()
            gameDetailsFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_right, gameDetailsFragment)
                .commit()
            requireActivity().supportFragmentManager.beginTransaction()/////////
                .replace(R.id.fragment_container_left, homeFragment)
                .commit()
        } else {
            requireView().findNavController().navigate(R.id.action_home_to_gameDetails, bundle)
        }
    }
}
*/

