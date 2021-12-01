package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository

class TaskDetailViewModelFactory(private val repository: TasksRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskDetailViewModel(repository) as T
    }
}