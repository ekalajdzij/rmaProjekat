package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches


import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import ba.etf.unsa.rma23.projekat.R
import org.hamcrest.*
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class OwnEspressoTests {

    @get:Rule
    var homeRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)


    /**
     * prvi test se odnosi na zahtjeve koje smo imali u zadatku
     * pri prvom otvaranju aplikacije zahtjev je da navigation bude disabled, pa provjeravamo to
     * zatim cemo se klikom na home vratiti na HomeFragment i provjeriti da li je homeItem enabled, trebalo bi da nije
     * nakon te provjere izvrsit cemo klik na gameDetailsItem i
     * provjeriti da li se u GameFragmentu otvorila ispravna igrica tako sto cemo provjeriti game-title
     */
    @Test
    fun requirementsTest(){

        val home = onView(withId(R.id.homeItem))
        home.check(matches(not(isEnabled())))

        val details = onView(withId(R.id.gameDetailsItem))
        details.check(matches(not(isEnabled())))

        onView(withId(R.id.game_list)).perform(click())

        onView(withId(R.id.homeItem)).perform(click())
        home.check(matches(not(isEnabled())))

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)).check(
            matches(
                hasDescendant(withText(getText(withIndex(withId(R.id.item_title_textview),0))))
            ))
        homeRule.scenario.close()

    }

    /**
     * drugi test se odnosi na testiranje rasporeda elemenata u GameDetailsFragmentu
     * prvo se vrši klik na jednu igru sa HomeActivity-ja, zatim se vraća na home, pa se vrši klik na gameDetailsItem
     * nakon toga se vrši test pozicije elemenata u GameDetailsFragmentu
     */
    @Test
    fun detailsFragmentAppearance(){
        onView(withId(R.id.game_list)).perform(click())
        onView(withId(R.id.homeItem)).perform(click())
        onView(withId(R.id.gameDetailsItem)).perform(click())
        onView(withId(R.id.item_title_textview)).check(PositionAssertions.isCompletelyBelow(withId(R.id.logo_image)))
        onView(withId(R.id.platform_textview)).check(PositionAssertions.isLeftAlignedWith(withId(R.id.release_date_textview)))
        onView(withId(R.id.platform_textview)).check(PositionAssertions.isLeftAlignedWith(withId(R.id.release_date_textview)))
        onView(withId(R.id.release_date_textview)).check(PositionAssertions.isLeftAlignedWith(withId(
            R.id.esrb_rating_textview
        )))
        onView(withId(R.id.esrb_rating_textview)).check(PositionAssertions.isRightAlignedWith(withId(
            R.id.developer_textview
        )))
        onView(withId(R.id.developer_textview)).check(PositionAssertions.isRightAlignedWith(withId(R.id.publisher_textview)))
        onView(withId(R.id.publisher_textview)).check(PositionAssertions.isRightAlignedWith(withId(R.id.genre_textview)))
        onView(withId(R.id.genre_textview)).check(PositionAssertions.isRightAlignedWith(withId(R.id.description_textview)))

        homeRule.scenario.close()
    }

    fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?> {
        return object : TypeSafeMatcher<View>() {
            var currentIndex = 0
            var viewObjHash = 0

            @SuppressLint("DefaultLocale")
            override fun describeTo(description: Description) {
                description.appendText(String.format("with index: %d ", index))
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                if (matcher.matches(view) && currentIndex++ == index) {
                    viewObjHash = view.hashCode()
                }
                return view.hashCode() == viewObjHash
            }
        }
    }


    fun getText(matcher: Matcher<View?>?): String? {
        val stringHolder = arrayOf<String?>(null)
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView //Save, because of check in getConstraints()
                stringHolder[0] = tv.text.toString()
            }
        })
        return stringHolder[0]
    }
    /**
     * pri pokretanju aplikacije provjerit cemo da li je navigation disable
     * zatim cemo izvrsiti klik na igru iz liste igara
     * kada dobijemo detalje igre izvrsit cemo klik na home opciju navigationa
     * kada se vratimo na home izvrsit cemo promjenu orijentacije u landscape mode
     * u landscape-u testiramo da li je prikazan title za odgovarajucu igru
     */

    @Test
    fun testTitleInLandscape() {

        onView(withId(R.id.game_list)).perform(click())
        onView(withId(R.id.homeItem)).perform(click())

        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)).check(
            matches(
                hasDescendant(withText(getText(withIndex(withId(R.id.item_title_textview),0))))
            ))
        homeRule.scenario.close()

    }

}