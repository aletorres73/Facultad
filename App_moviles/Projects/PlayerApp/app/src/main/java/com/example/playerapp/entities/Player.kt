package com.example.playerapp.entities

const val TEAM_NOT_SET = ""

class PlayerManager {
    val players = mutableListOf<Player>()
    init {
        players.addAll(listOf(
        Player(name = "Lio",    lastname = "Messi",    age = 35, team = TEAM_NOT_SET, imageUrl = "imageURL"),
        Player(name = "Angel",  lastname = "Di Maria", age = 35, team = TEAM_NOT_SET, imageUrl = "imageURL"),
        Player(name = "Leandro",lastname = "Paredes",  age = 30, team = TEAM_NOT_SET, imageUrl = "imageURL"),
        Player(name = "Dibu",   lastname = "Martinez", age = 35, team = TEAM_NOT_SET, imageUrl = "imageURL"),
        Player(name = "Papu",   lastname = "Gomez",    age = 36, team = TEAM_NOT_SET, imageUrl = "imageURL"),
        Player(name = "Rodrigo",lastname = "De Paul",  age = 29, team = TEAM_NOT_SET, imageUrl = "imageURL")
    ).sortedBy { it.name })
    }

    fun getPlayersList(): MutableList<Player> {
        return players }

    fun addPlayers(player: Player) {
        players.add(player) }

    fun removePlayers(player: Player) {
        players.remove(player) }
}

class Player (
    val name:       String,
    val lastname:   String,
    var age:        Int,
    var team:       String,
    var imageUrl:   String)

