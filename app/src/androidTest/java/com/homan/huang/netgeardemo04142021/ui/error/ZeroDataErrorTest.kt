package com.homan.huang.netgeardemo04142021.ui.error

import android.R
import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.homan.huang.netgeardemo04142021.data.local.ImageManifestDatabase
import com.homan.huang.netgeardemo04142021.data.remote.service.ImageApiService
import com.homan.huang.netgeardemo04142021.di.RepositoryModule
import com.homan.huang.netgeardemo04142021.di.data.DatabaseModule
import com.homan.huang.netgeardemo04142021.di.remote.BaseUrlModule
import com.homan.huang.netgeardemo04142021.di.storage.CacheFolderNameModule
import com.homan.huang.netgeardemo04142021.hellper.Constants.ERRMSG_SERVER
import com.homan.huang.netgeardemo04142021.repository.BaseRepository
import com.homan.huang.netgeardemo04142021.repository.EmptyManifestRepository
import com.homan.huang.netgeardemo04142021.ui.MainActivity
import com.homan.huang.netgeardemo04142021.util.CoroutineTestRule
import com.homan.huang.netgeardemo04142021.util.json.getBlankManifestJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * Trigger the UI Error: Zero Data Error
 *                       empty in room and
 *                       download failed
 *
 * Condition:
 *      ApiManifest = [] by MockWebserver
 *      ManifestData = null by EmptyManifestRepository
 */
@ExperimentalCoroutinesApi
@LargeTest
@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
@UninstallModules(
    CacheFolderNameModule::class,
    BaseUrlModule::class,
    DatabaseModule::class,
    RepositoryModule::class
)
class ZeroDataErrorTest {
    @get:Rule(order = 0)
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE")

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 3)
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule(order = 4)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 5)
    var coroutinesTestRule = CoroutineTestRule()

    @Inject
    lateinit var apiService: ImageApiService


    @Module
    @InstallIn(SingletonComponent::class)
    class FakeModule {
        // fake cache folder
        @Provides
        @Singleton
        @Named("cache_dir")
        fun provideCacheFolderName(): String = "fake_cache"

        // fake url
        @Provides
        @Singleton
        fun provideUrl(): String = "http://localhost:8080/"

        // fake database
        @Provides
        @Singleton
        fun provideInMemoryDb(@ApplicationContext context: Context) =
            Room.inMemoryDatabaseBuilder(context, ImageManifestDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        // fake repository
        @Provides
        @Singleton
        fun provideFakeRepository(
            imageCachedFolder: File,
            imageApiService: ImageApiService,
            imageDb: ImageManifestDatabase
        ): BaseRepository =
            EmptyManifestRepository(imageCachedFolder, imageApiService, imageDb)

    }

    // Mock Webserver
    protected lateinit var mockWebServer: MockWebServer

    // Mock: Bad manifest data
    private fun getBadManifestDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                MockResponse().setResponseCode(200)
                    .setBody(getBlankManifestJson())
        }
    }

    @Before
    fun setup() {
        hiltRule.inject()

        // Web server
        mockWebServer = MockWebServer()
    }


    // When the download = 0 and room -- manifest = 0,
    // zero_data is triggered.
    @Test
    fun test_zero_data_error() {
        // start web server
        mockWebServer.dispatcher = getBadManifestDispatcher()
        mockWebServer.start(8080)

        // start activity
        val scenario = activityRule.getScenario()

        Thread.sleep(2000L)

        // espresso: verify dialog data
        val dialogTitle = Espresso.onView(
            Matchers.allOf(
                IsInstanceOf.instanceOf(TextView::class.java),
                withText("Alert! Data Error..."),
                withParent(
                    Matchers.allOf(
                        IsInstanceOf.instanceOf(LinearLayout::class.java),
                        withParent(IsInstanceOf.instanceOf(LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        dialogTitle.check(ViewAssertions.matches(withText("Alert! Data Error...")))

        val errMessage = Espresso.onView(withId(R.id.message))
        errMessage.check(ViewAssertions.matches(withText(ERRMSG_SERVER)))

        mockWebServer.shutdown()
    }

}