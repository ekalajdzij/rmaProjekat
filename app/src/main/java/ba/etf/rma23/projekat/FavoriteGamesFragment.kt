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
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.unsa.rma23.projekat.R
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGamesByName
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

class FavoriteGamesFragment : Fragment() {
    private lateinit var gameView: RecyclerView
    private lateinit var gameAdapter: VideoGameAdapter
    private var games = emptyList<Game>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        gameView = view.findViewById(R.id.game_list)
        runBlocking {
            games = AccountGamesRepository.getSavedGames()
        }

        gameView.layoutManager = LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
        gameAdapter = VideoGameAdapter(games) { game -> showGameDetails(game) }
        gameView.adapter = gameAdapter
        gameAdapter.updateGames(games)


        val bundle: Bundle? = arguments
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navigation: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            val detailsNavItem: BottomNavigationItemView = navigation.findViewById(R.id.gameDetailsItem)
            if (bundle?.getInt("id") == 0) {
                navigation.menu.getItem(0).isEnabled = false
                navigation.menu.getItem(1).isEnabled = false
            }
            var game : Game
            detailsNavItem.setOnClickListener {
                val gid = bundle!!.getInt("id",0)
                runBlocking {
                    val gameList = GamesRepository.getGameById(gid)
                    game = gameList[0]
                }
                showGameDetails(game)
            }

            return view
        }


        return view
    }
    private fun showGameDetails(game: Game) {
        val bundle = bundleOf("id" to game.id)

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


