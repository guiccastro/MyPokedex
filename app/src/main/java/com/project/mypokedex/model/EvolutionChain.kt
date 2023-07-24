package com.project.mypokedex.model

class EvolutionChain(
    val chain: EvolutionChainItem
) {

    fun getAllPaths(): List<List<Pokemon>> {
        val allPaths = ArrayList<List<Pokemon>>()
        findPathsDFS(chain, ArrayList(), allPaths)
        return allPaths
    }

    private fun findPathsDFS(
        currentItem: EvolutionChainItem,
        currentPath: ArrayList<Pokemon>,
        allPaths: ArrayList<List<Pokemon>>
    ) {
        currentPath.add(currentItem.pokemon)

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
