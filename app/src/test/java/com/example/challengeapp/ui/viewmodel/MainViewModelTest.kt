package com.example.challengeapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.Media
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.model.NewsDTO
import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.domain.favorites.GetFavoritesUseCase
import com.example.challengeapp.main.domain.mapper.GetNewsMapper
import com.example.challengeapp.main.domain.mapper.NewMapper
import com.example.challengeapp.main.domain.most_popular.FetchMostPopularUseCase
import com.example.challengeapp.main.ui.main.MainViewModel
import com.idea3d.idea3d.MainCoroutineRule
import com.idea3d.idea3d.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val fetchMostPopularUseCase = mockk<FetchMostPopularUseCase>()
    private val getFavoritesUseCase = mockk<GetFavoritesUseCase>()
    private val getNewsMapper = mockk<GetNewsMapper>()
    private val newMapper = mockk<NewMapper>()
    private val repo = mockk<Repo>()
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val news1 = News()
    private val newsDTO = NewsDTO(0, "dd", "sss", listOf(), "", false)

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        mainViewModel = MainViewModel(repo, fetchMostPopularUseCase, getFavoritesUseCase, getNewsMapper, newMapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(repo, fetchMostPopularUseCase, getFavoritesUseCase, getNewsMapper, newMapper)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addedToFavorite calls repository method`() {
        val news = news1
        val newsDTO = newsDTO

        mainViewModel.addedToFavorite(newsDTO)

        coVerify { repo.addedNewsToFav(news)}
    }

    @Test
    fun `deleteFavorite calls repository method`() {
        val news = news1

        mainViewModel.deleteFavorite(newsDTO)

        coVerify { repo.deleteFavorite(news) }
    }

    @Test
    fun `when fetching results ok then return a list successfully`() {
        // GIVEN
        testCoroutineRule.runBlockingTest {
            // WHEN
            mainViewModel.fetchMostPopular("viewed", "7")
            // THEN
            TestCase.assertNotNull(mainViewModel.fetchMostPopular("viewed", "7"))
        }
    }



}