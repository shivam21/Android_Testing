package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        val tasks = listOf<Task>(Task("task1"), Task("task2"), Task("task3"), Task("task4"))
        val result = getActiveAndCompletedStats(tasks)
        assertThat( result.activeTasksPercent,`is`(100f))
        assertThat( result.completedTasksPercent,`is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_40Completed_returnsFortySixty() {
        val tasks = listOf<Task>(Task("task1",isCompleted = true), Task("task2",isCompleted = true), Task("task3"), Task("task4"),Task("task5"))
        val result = getActiveAndCompletedStats(tasks)
        assertThat( result.activeTasksPercent,`is`(60f))
        assertThat( result.completedTasksPercent,`is`(40f))
    }

    @Test
    fun getActiveAndCompletedStats_emptyList_returnsZeroZero() {
        val tasks = emptyList<Task>()
        val result = getActiveAndCompletedStats(tasks)
        assertThat( result.activeTasksPercent,`is`(0f))
        assertThat( result.completedTasksPercent,`is`(0f))
    }
}