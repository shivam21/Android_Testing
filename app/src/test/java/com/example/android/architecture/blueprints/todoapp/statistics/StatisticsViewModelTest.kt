package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTaskRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StatisticsViewModelTest {

    private lateinit var repository: FakeTaskRepository
    private lateinit var viewModel: StatisticsViewModel

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repository = FakeTaskRepository()
        viewModel = StatisticsViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadTasks_loading() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.refresh()
        MatcherAssert.assertThat(viewModel.dataLoading.getOrAwaitValue(), `is`(true))
        mainCoroutineRule.resumeDispatcher()
        MatcherAssert.assertThat(viewModel.dataLoading.getOrAwaitValue(), `is`(false))

    }

    @Test
    fun loadStatisticsWhenTasksAreUnavailable_callErrorToDisplay() {
        repository.setReturnError(true)
        viewModel.refresh()
        MatcherAssert.assertThat(viewModel.error.getOrAwaitValue(), `is`(true))
        MatcherAssert.assertThat(viewModel.empty.getOrAwaitValue(), `is`(true))
    }
}