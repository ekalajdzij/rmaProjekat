package ba.etf.unsa.rma.videogameproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getAll
import ba.etf.unsa.rma.videogameproject.GameData.VideoGames.getDetails
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
        navView.setupWithNavController(navController)

    }
}

        /*if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
        {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                val bundle = bundleOf("search" to it)
                navView.selectedItemId= R.id.searchFragment
                navController.navigate(R.id.searchFragment,bundle)
            }
        }
    }
    {
    private lateinit var gameView : RecyclerView
    private lateinit var gameAdapter: VideoGameAdapter
    private var games = getAll()
    private lateinit var details : Button

    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        gameView = findViewById(R.id.game_list)

        details = findViewById(R.id.details_button)

        val extras = intent.extras
        details.isEnabled = extras != null
        details.setOnClickListener{
            if (extras != null) {
                val game = getDetails(extras.getString("title",""))
                showGameDetails(game!!)
            }
        }

        gameView.layoutManager = LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false)

        gameAdapter = VideoGameAdapter(listOf()) {game -> showGameDetails(game)}
        gameView.adapter = gameAdapter
        gameAdapter.updateGames(games)

    }

    private fun showGameDetails(game: Game) {
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("title", game.title)
        }
        startActivity(intent)
    }
}*/