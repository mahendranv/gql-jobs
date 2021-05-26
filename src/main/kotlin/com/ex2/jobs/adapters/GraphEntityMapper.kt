package com.ex2.jobs.adapters

interface GraphEntityMapper<G, E> {

    fun toEntity(g: G): E

    fun toGraph(e: E): G

}