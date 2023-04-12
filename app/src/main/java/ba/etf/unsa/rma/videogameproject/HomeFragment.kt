package ba.etf.unsa.rma.videogameproject

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getAll
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getDetails
import ba.etf.unsa.rma.videogameproject.R.*
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
class HomeFragment : Fragment() {
    private lateinit var gameView: RecyclerView
    private lateinit var gameAdapter: VideoGameAdapter
    private var games = getAll()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout.fragment_home, container, false)

        gameView = view.findViewById(R.id.game_list)

        gameView.layoutManager = LinearLayoutManager(container!!.context, LinearLayoutManager.VERTICAL, false)
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
            val gameDetailsFragment = GameDetailsFragment()
            gameDetailsFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_right, gameDetailsFragment)
                .commit()
        } else {
            requireView().findNavController().navigate(R.id.action_home_to_gameDetails, bundle)
        }
    }
}


