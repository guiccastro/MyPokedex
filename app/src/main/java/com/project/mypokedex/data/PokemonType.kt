package com.project.mypokedex.data

enum class PokemonType(val id: Int) {
    Normal(1),
    Fighting(2),
    Flying(3),
    Poison(4),
    Ground(5),
    Rock(6),
    Bug(7),
    Ghost(8),
    Steel(9),
    Fire(10),
    Water(11),
    Grass(12),
    Electric(13),
    Psychic(14),
    Ice(15),
    Dragon(16),
    Dark(17),
    Fairy(18),
    Unknown(19),
    Shadow(20);


    companion object {
        fun fromName(name: String): PokemonType? {
            return values().firstOrNull { it.name.lowercase() == name.lowercase() }
        }

        fun fromId(id: Int): PokemonType? {
            return values().firstOrNull { it.id == id }
        }
    }

    override fun toString(): String {
        return name
    }
}