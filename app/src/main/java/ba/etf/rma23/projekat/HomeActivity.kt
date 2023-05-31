package ba.etf.rma23.projekat

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.unsa.rma23.projekat.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val config: Configuration = baseContext.resources.configuration
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_right, GameDetailsFragment()).commit()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_left, HomeFragment()).commit()
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container_right, GameDetailsFragment()).commit()
        } else {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
        }
    }
}
