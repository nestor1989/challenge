package com.example.challengeapp.main.data.repo

import com.example.challengeapp.main.data.datasource.DataSource
import javax.inject.Inject

class RepoImpl
@Inject constructor(private val dataSource: DataSource)
:Repo {
}