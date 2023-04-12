package ba.etf.unsa.rma.videogameproject

import android.app.IntentService
import android.view.View
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
     * pri otvaranju aplikacije provjeravamo da li je details button aktivan - zahtjev zadatke je da bude disabled
     * pri kliku na igricu provjeravamo da li se otvaraju detalji o igrici, odnosno klasa GameDetailsActivity
     */
    @Test
    fun test1(){
        Intents.init()
        onView(withId(R.id.game_list)).perform(click())
        intended(hasComponent(GameDetailsActivity::class.java.name))
        Intents.release()

        val detailButton = onView(withId(R.id.details_button))
        detailButton.check(matches(not(isEnabled())))
    }

    /**
     * test2 se odnosi na testiranje rasporeda elemenata u GameDetails fragmentu
     * prvo se vrši klik na jednu igru sa HomeActivity-ja, zatim se vraća na home, pa se vrši klik na game details opciju navigation bara
     * nakon toga se vrši test pozicije elemenata u GameDetails fragmentu
     */
    @Test
    fun test2(){
        onView(withId(R.id.game_list)).perform(click())
        onView(withId(R.id.bottom_nav)).perform(NavigationViewActions.navigateTo(R.id.homeItem),click())
        onView(withId(R.id.bottom_nav)).perform(NavigationViewActions.navigateTo(R.id.gameDetailsItem),click())
        Espresso.onView(ViewMatchers.withId(R.id.item_title_textview)).check(PositionAssertions.isCompletelyBelow(ViewMatchers.withId(R.id.logo_image)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview)).check(PositionAssertions.isLeftAlignedWith(ViewMatchers.withId(R.id.release_date_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.platform_textview)).check(PositionAssertions.isLeftAlignedWith(ViewMatchers.withId(R.id.release_date_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.release_date_textview)).check(PositionAssertions.isLeftAlignedWith(ViewMatchers.withId(R.id.esrb_rating_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.esrb_rating_textview)).check(PositionAssertions.isRightAlignedWith(ViewMatchers.withId(R.id.developer_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.developer_textview)).check(PositionAssertions.isRightAlignedWith(ViewMatchers.withId(R.id.publisher_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.publisher_textview)).check(PositionAssertions.isRightAlignedWith(ViewMatchers.withId(R.id.genre_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.genre_textview)).check(PositionAssertions.isRightAlignedWith(ViewMatchers.withId(R.id.description_textview)))
    }

    /**
     * testiramo prikaz svih potrebnih informacija u GameDetailsActivity nakon nekoliko promjena izmedju HomeActivity i GameDetailActivity
     * s ciljem provjere da li je posljednja igra kojoj se pristupa dobro zapamcena
     * nakon posljednjeg pristupa GameDetailActivity provjeravamo da li su sve informacije u redu
     *
     */

    @Test
    fun test3() {
        onView(withId(R.id.game_list)).perform(click())
        onView(withId(R.id.home_button)).perform(click())
        onView(withId(R.id.details_button)).perform(click())
        onView(withId(R.id.home_button)).perform(click())
        onView(withId(R.id.game_list)).perform(click())
        onView(withId(R.id.home_button)).perform(click())
        onView(withId(R.id.details_button)).perform(click())
        onView(withId(R.id.home_button)).perform(click())


        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)).check(matches(
            CoreMatchers.allOf(
                hasDescendant(withId(R.id.item_title_textview)),
                hasDescendant(withId(R.id.game_rating_textview)),
                hasDescendant(withId(R.id.game_release_date_textview)),
                hasDescendant(withId(R.id.game_platform_textview)),
                hasDescendant(withId(R.id.game_rating_textview))
            )
        ))

    }
}