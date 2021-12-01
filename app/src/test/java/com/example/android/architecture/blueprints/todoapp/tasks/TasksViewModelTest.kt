package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTaskRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.annotation.Config


@Config(manifest = Config.NONE)
class TasksViewModelTest {

    private lateinit var viewModel: TasksViewModel
    private lateinit var tasksRepository: FakeTaskRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        tasksRepository = FakeTaskRepository().apply {
            val task1 = Task("Title1", "Description1")
            val task2 = Task("Title2", "Description2", true)
            val task3 = Task("Title3", "Description3", true)
            addTasks(task1, task2, task3)
        }
        viewModel = TasksViewModel(tasksRepository)
    }

    @Test
    fun addNewTask_setNewTaskEvent() {
        viewModel.addNewTask()
        val result = viewModel.newTaskEvent.getOrAwaitValue()
        assertThat(result.getContentIfNotHandled(), (not(nullValue())))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        viewModel.setFiltering(TasksFilterType.ALL_TASKS)
        val result: Boolean = viewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(result, `is`(true))
    }

    @Test
    fun completeTask_dataAndSnackbarUpdated() {
        // Create an active task and add it to the repository.
        val task = Task("Title", "Description")
        tasksRepository.addTasks(task)

        // Mark the task as complete task.
        viewModel.completeTask(task, true)

        // Verify the task is completed.
        assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted, `is`(true))

        // Assert that the snackbar has been updated with the correct text.
        val snackbarText: Event<Int> = viewModel.snackbarText.getOrAwaitValue()
        assertThat(snackbarText.getContentIfNotHandled(), `is`(R.string.task_marked_complete))
    }
}