package ba.etf.unsa.rma.videogameproject

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
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
        val config: Configuration = baseContext.resources.configuration
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            /*super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)*/
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_right, GameDetailsFragment()).commit()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_left, HomeFragment()).commit()
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container_right, GameDetailsFragment()).commit()
        } else {
            /*super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)*/
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
        }
    }
}
