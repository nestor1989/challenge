package com.example.challengeapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.ui.main.MainViewModel
import com.example.challengeapp.main.utils.Constants
import com.idea3d.idea3d.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @RelaxedMockK
    private lateinit var repo: Repo
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val news1 = News()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        repo = mockk()
        mainViewModel = MainViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(repo)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addedToFavorite calls repository method`() {
        val news = news1

        mainViewModel.addedToFavorite(news)

        coVerify { repo.addedNewsToFav(news) }
    }

    @Test
    fun `deleteFavorite calls repository method`() {
        val news = news1

        mainViewModel.deleteFavorite(news)

        coVerify { repo.deleteFavorite(news) }
    }

    @Test
    fun `when fetching results ok then return a list successfully`() {
        // GIVEN
        testCoroutineRule.runBlockingTest {
            // WHEN
            mainViewModel.fetchMostPopular(Constants.VIEWED, "7")
            // THEN
            TestCase.assertNotNull(mainViewModel.fetchMostPopular(Constants.VIEWED, "7"))
        }
    }

}