package com.sasakissa.githubsample.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import com.sasakissa.githubsample.entity.Repo
import com.sasakissa.githubsample.entity.RepoSearchResponse
import github.Github
import github.GithubService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchRepoViewModel : ViewModel() {
    val items: MutableLiveData<List<Repo>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isError: MutableLiveData<Boolean> = MutableLiveData()

    val githubService: GithubService = Github.defaultClient()

    fun search(term: String) {
        isLoading.value = true

        Single.fromCallable { githubService.searchRepos(term) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result.status) {
                    Github.success() -> {
                        val repoSearchResponse = Gson().fromJson(result.result, RepoSearchResponse::class.java)
                        items.value = repoSearchResponse.items
                        isError.value = false
                    }
                    Github.errorNetwork() -> {
                        isError.value = true
                    }
                    Github.errorHttp() -> {
                        isError.value = true
                    }
                }
                isLoading.value = false
            }
    }

}