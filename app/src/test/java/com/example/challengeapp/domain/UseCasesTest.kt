package com.example.challengeapp.domain

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.domain.favorites.GetFavoritesUseCase
import com.example.challengeapp.main.domain.favorites.GetFavoritesUseCaseImpl
import com.example.challengeapp.main.domain.mapper.GetNewsMapper
import com.example.challengeapp.main.domain.most_popular.FetchMostPopularUseCase
import com.example.challengeapp.main.domain.most_popular.FetchMostPopularUseCaseImpl
import com.idea3d.idea3d.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UseCasesTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val repo = mockk<Repo>()
    private val getNewsMapper = mockk<GetNewsMapper>()

    private lateinit var fetchMostPopularUseCase: FetchMostPopularUseCase
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Before
    fun setup() {
        fetchMostPopularUseCase = FetchMostPopularUseCaseImpl(repo)
        getFavoritesUseCase = GetFavoritesUseCaseImpl(repo)
    }

    @Test
    fun `fetchMostPopular calls repo`() = mainCoroutineRule.runBlockingTest {
        coEvery { repo.getMostPopular(any(), any()) } returns emptyList()

        fetchMostPopularUseCase("viewed", "7")

        coVerify { repo.getMostPopular("viewed", "7") }
    }

    @Test
    fun `getFavorites calls repo`() = mainCoroutineRule.runBlockingTest {
        coEvery { repo.getNewsFav() } returns emptyList()

        getFavoritesUseCase()

        coVerify { repo.getNewsFav() }
    }
}