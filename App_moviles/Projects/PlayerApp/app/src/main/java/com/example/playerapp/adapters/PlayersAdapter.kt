package com.example.playerapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.playerapp.R
import com.example.playerapp.entities.Player

class PlayersAdapter(
    private var playerlist:MutableList<Player>,
    var onClick: (Int) -> Unit
):RecyclerView.Adapter<PlayersAdapter.PlayerHolder>(){

    class PlayerHolder(v:View):RecyclerView.ViewHolder(v){ //el holder se comunica con la vista
        private lateinit var view: View
        init{
            this.view = v
        }
        fun setName(name: String){
            val txtName : TextView = view.findViewById(R.id.txtName)
            txtName.text = name
        }
        fun setTeam(team: String){
            val txtTeam : TextView = view.findViewById(R.id.txtTeam)
            txtTeam.text = team
        }
        //Si tuviese más interacciones con la lista hay que agregar más interacciones.

        fun getCard(): CardView{
            return view.findViewById(R.id.cardPlayer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player,parent,false)
        return (PlayerHolder(view))
    }

    override fun getItemCount(): Int {  
        return playerlist.size
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.setName(playerlist[position].name)
        holder.setTeam(playerlist[position].team)
        holder.getCard().setOnClickListener{
            //lo que se quiera hacer cuando se hace click
            onClick(position) // ejecuta la función onClick que se la pasa una position
        }
    }
}