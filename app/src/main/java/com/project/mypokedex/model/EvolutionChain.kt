package com.project.mypokedex.model

import kotlin.math.max

class EvolutionChain(
    val chain: EvolutionChainItem
) {
    fun getColumnsList(): List<List<Pokemon>> {
        val columnsList = ArrayList<List<Pokemon>>()
        repeat(chain.numberOfColumns()) { index ->
            getColumByIndex(index).let { column ->
                columnsList.add(column)
            }
        }
        return columnsList
    }

    private fun EvolutionChainItem.numberOfColumns(): Int {
        var countEvolvesTo = 0
        evolvesTo.forEach { evolutionChainItem ->
            countEvolvesTo = max(countEvolvesTo, evolutionChainItem.numberOfColumns())
        }
        return countEvolvesTo + 1
    }

    private fun getColumByIndex(index: Int): List<Pokemon> {
        return chain.getColumByIndex(index, 0)
    }

    private fun EvolutionChainItem.getColumByIndex(
        targetIndex: Int,
        currentIndex: Int
    ): List<Pokemon> {
        return if (targetIndex == currentIndex) {
            return listOf(pokemon)
        } else {
            val list = ArrayList<Pokemon>()
            evolvesTo.forEach { evolutionChainItem ->
                list.addAll(evolutionChainItem.getColumByIndex(targetIndex, currentIndex + 1))
            }
            list
        }
    }
}
