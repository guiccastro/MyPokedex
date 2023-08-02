package com.project.mypokedex.model

class EvolutionChain(
    val chain: EvolutionChainItem
) {

    fun getAllPaths(): List<List<Int>> {
        val allPaths = ArrayList<List<Int>>()
        findPathsDFS(chain, ArrayList(), allPaths)
        return allPaths
    }

    private fun findPathsDFS(
        currentItem: EvolutionChainItem,
        currentPath: ArrayList<Int>,
        allPaths: ArrayList<List<Int>>
    ) {
        currentPath.add(currentItem.pokemonId)

        if (currentItem.evolvesTo.isEmpty()) {
            allPaths.add(currentPath.toList())
        } else {
            currentItem.evolvesTo.forEach { item ->
                findPathsDFS(item, currentPath, allPaths)
            }
        }

        currentPath.removeAt(currentPath.size - 1)
    }
}
