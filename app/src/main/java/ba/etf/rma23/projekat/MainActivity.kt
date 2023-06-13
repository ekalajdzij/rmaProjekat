package ba.etf.rma23.projekat


import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.unsa.rma23.projekat.R
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

    CoroutineScope(Job() + Dispatchers.Main).launch {
       val review = GameReview(10,5,"Ostavlja bez daha", 176032, true)
        GameReviewsRepository.sendReview(applicationContext, review)
        val review3 = GameReview(66,5,"Fenomenalna igrica", 50975, false)
        GameReviewsRepository.addOfflineReviews(applicationContext, review3)
        val review2 = GameReview(60,3,"Solidna igrica", 50975, false)
        GameReviewsRepository.addOfflineReviews(applicationContext, review2)
        val size = GameReviewsRepository.getOfflineReviews(applicationContext)
        runBlocking {
            GameReviewsRepository.sendOfflineReviews(applicationContext)
        }
        val size1 = GameReviewsRepository.getOfflineReviews(applicationContext)
        for (i in size1.indices)
            print(size1[i].online)

    }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val config: Configuration = baseContext.resources.configuration
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_right, GameDetailsFragment()).commit()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_left, HomeFragment()).commit()
        } else {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
        }
    }

}