package com.example.challengeapp.main.data.datasource

import com.example.challengeapp.main.data.network.WebService
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val webService: WebService,
): DataSource {}