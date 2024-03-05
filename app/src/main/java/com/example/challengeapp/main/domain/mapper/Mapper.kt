package com.example.challengeapp.main.domain.mapper

interface Mapper<I, O> {
    fun mapToUI(input: I): O
    fun mapToDomain(input: O): I
}