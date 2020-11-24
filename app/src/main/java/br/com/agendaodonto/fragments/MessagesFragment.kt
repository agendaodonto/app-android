package br.com.agendaodonto.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import br.com.agendaodonto.R
import br.com.agendaodonto.database.MessageService


class MessagesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var listView: ListView
    private lateinit var parentContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.list_messages)
        loadMessages()
    }

    private fun loadMessages() {
        val messages = MessageService.getInstance(context!!).messageDao().getAll()
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, messages)
        listView.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
    }


}
