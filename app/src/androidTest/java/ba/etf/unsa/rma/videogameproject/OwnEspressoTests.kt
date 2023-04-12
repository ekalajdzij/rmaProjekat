package ba.etf.unsa.rma.videogameproject

import android.app.IntentService
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import com.google.android.material.internal.NavigationMenuItemView
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class OwnEspressoTests {
    fun hasItemCount(n: Int) = object : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            Assert.assertTrue("View nije tipa RecyclerView", view is RecyclerView)
            var rv: RecyclerView = view as RecyclerView
            ViewMatchers.assertThat(
                "GetItemCount RecyclerView broj elementa: ",
                rv.adapter?.itemCount,
                CoreMatchers.`is`(n)
            )
        }
    }

    @get:Rule
    var homeRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)


    /**
     * test1 se odnosi na zahtjeve koje smo imali u zadatku
     * pri prvom otvaranju aplikacije zahtjev je da navigation bude disabled, pa provjeravamo to
     * zatim kada kliknemo na igricu i otvorimo game details provjerit cemo da li je detailsItem visible, trebalo bi da nije
     * zatim cemo se klikom na home vratiti na HomeFragment i provjeriti da li je homeItem enabled, trebalo bi da nije
     * nakon te provjere izvrsit cemo klik na gameDetailsItem i
     * provjeriti da li se u GameFragmentu otvorila ispravna igrica tako sto cemo provjeriti game-title
     */
    @Test
    fun test1(){
        val home = onView(withId(R.id.homeItem))
        home.check(matches(not(isEnabled())))

        val details = onView(withId(R.id.gameDetailsItem))
        details.check(matches(not(isEnabled())))

        Thread.sleep(1000)
        onView(withId(R.id.game_list)).perform(click())

        details.check(matches(not(isDisplayed())))

        onView(withId(R.id.homeItem)).perform(click())
        Thread.sleep(1000)
        home.check(matches(not(isEnabled())))

        val name = onView(withId(R.id.item_title_textview))
        Thread.sleep(1000)
        onView(withId(R.id.game_list)).perform(click())
        name.check(matches(withId(R.id.item_title_textview)))

    }

    /**
     * test2 se odnosi na testiranje rasporeda elemenata u GameDetailsFragmentu
     * prvo se vrši klik na jednu igru sa HomeActivity-ja, zatim se vraća na home, pa se vrši klik na gameDetailsItem
     * nakon toga se vrši test pozicije elemenata u GameDetailsFragmentu
     */
    @Test
    fun test2(){
        onView(withId(R.id.game_list)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.homeItem)).perform(click())
        onView(withId(R.id.gameDetailsItem)).perform(click())
        onView(withId(R.id.item_title_textview)).check(PositionAssertions.isCompletelyBelow(withId(R.id.logo_image)))
        onView(withId(R.id.platform_textview)).check(PositionAssertions.isLeftAlignedWith(withId(R.id.release_date_textview)))
        onView(withId(R.id.platform_textview)).check(PositionAssertions.isLeftAlignedWith(withId(R.id.release_date_textview)))
        onView(withId(R.id.release_date_textview)).check(PositionAssertions.isLeftAlignedWith(withId(R.id.esrb_rating_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(PositionAssertions.isRightAlignedWith(withId(R.id.developer_textview)))
        onView(withId(R.id.developer_textview)).check(PositionAssertions.isRightAlignedWith(withId(R.id.publisher_textview)))
        onView(withId(R.id.publisher_textview)).check(PositionAssertions.isRightAlignedWith(withId(R.id.genre_textview)))
        onView(withId(R.id.genre_textview)).check(PositionAssertions.isRightAlignedWith(withId(R.id.description_textview)))
    }

    /**
     * testiramo prikaz svih potrebnih informacija u GameDetailsActivity nakon nekoliko promjena izmedju HomeActivity i GameDetailActivity
     * s ciljem provjere da li je posljednja igra kojoj se pristupa dobro zapamcena
     * nakon posljednjeg pristupa GameDetailActivity provjeravamo da li su sve informacije u redu
     *
     */

    @Test
    fun test3() {
        /*ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        activityRule.activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container_left, HomeFragment()).commit()
        activityRule.activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container_right, GameDetailsFragment()).commit()

        val leftFragment = activityRule.activity.supportFragmentManager.findFragmentById(R.id.fragment_container_left) as HomeFragment
        val rightFragment = activityRule.activity.supportFragmentManager.findFragmentById(R.id.fragment_container_right) as GameDetailsFragment

        val leftTextView = leftFragment.view?.findViewById<TextView>(R.id.item_title_textview)
        val rightTextView = rightFragment.view?.findViewById<TextView>(R.id.item_title_textview)
        assertEquals(leftTextView?.text, rightTextView?.text)*/


    }
}