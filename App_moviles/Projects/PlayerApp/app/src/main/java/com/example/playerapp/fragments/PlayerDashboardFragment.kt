    package com.example.playerapp.fragments

    import androidx.lifecycle.ViewModelProvider
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import androidx.recyclerview.widget.RecyclerView.Recycler
    import com.example.playerapp.R
    import com.example.playerapp.adapters.PlayersAdapter
    import com.example.playerapp.entities.PlayerManager
    import com.google.android.material.snackbar.Snackbar

    class PlayerDashboardFragment : Fragment() {

        lateinit var v         : View
        lateinit var recPlayer : RecyclerView
        lateinit var adapter   : PlayersAdapter
        var playersRepository  : PlayerManager = PlayerManager()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            v = inflater.inflate(R.layout.fragment_player_dashboard, container, false)

            recPlayer = v.findViewById(R.id.recPlayer)
            return v
        }

        override fun onStart() {
            super.onStart()

            adapter  = PlayersAdapter(playersRepository.getPlayersList()){position ->
                Snackbar.make(v,"click en ${playersRepository.players[position].name}",Snackbar.LENGTH_LONG).show()
            }
            recPlayer.layoutManager = LinearLayoutManager(context)
            recPlayer.adapter = adapter
        }

    }