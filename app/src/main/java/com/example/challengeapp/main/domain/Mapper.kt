package com.example.challengeapp.main.domain

interface Mapper<I, O> {
    fun map(input: I): O
}