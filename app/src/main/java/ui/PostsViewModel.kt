package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Post
import data.PostRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    val posts: StateFlow<List<Post>> = repo.observePosts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun addPost(content: String) {
        viewModelScope.launch { repo.addPost(content) }
    }

    fun editPost(post: Post, newContent: String) {
        viewModelScope.launch { repo.editPost(post, newContent) }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch { repo.removePost(post) }
    }
}