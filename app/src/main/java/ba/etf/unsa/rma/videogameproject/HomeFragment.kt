package ba.etf.unsa.rma.videogameproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getAll
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getDetails
import com.google.android.material.navigation.NavigationBarView

class HomeFragment : Fragment() {
    private lateinit var gameView: RecyclerView
    private lateinit var gameAdapter: VideoGameAdapter
    private var games = getAll()
    private lateinit var details: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        gameView = view.findViewById(R.id.game_list)
        details = view.findViewById(R.id.details_button)

        val extras = activity?.intent?.extras
        details.isEnabled = extras != null
        details.setOnClickListener {
            if (extras != null) {
                val game = getDetails(extras.getString("title", ""))
                showGameDetails(game!!)
            }
        }

        gameView.layoutManager = LinearLayoutManager (activity, LinearLayoutManager.VERTICAL, false)

        gameAdapter = VideoGameAdapter(listOf()) { game -> showGameDetails(game) }
        gameView.adapter = gameAdapter
        gameAdapter.updateGames(games)

        return view
    }


    private fun showGameDetails(game: Game) {
        val intent = Intent(activity, GameDetailsActivity::class.java).apply {
            putExtra("title", game.title)
        }
        startActivity(intent)
    }

}