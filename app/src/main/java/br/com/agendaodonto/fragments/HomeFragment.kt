package br.com.agendaodonto.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import br.com.agendaodonto.*
import br.com.agendaodonto.database.MessageService
import br.com.agendaodonto.services.DentistData
import br.com.agendaodonto.services.TokenData
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.log_id_button).setOnClickListener {
            logDeviceId()
        }
        view.findViewById<Button>(R.id.update_id_button).setOnClickListener {
            updateDeviceIdOnServer()
        }
        view.findViewById<Button>(R.id.log_db).setOnClickListener {
            logDb()
        }
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply { arguments = Bundle().apply { } }

        private const val TAG = "HOME_FRAGMENT"
    }

    private fun logDeviceId() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Log.d(Companion.TAG, "Token => " + it.token)
        }
    }

    private fun updateDeviceIdOnServer() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            val body = TokenData(it.token)
            val token = "Token " + Preferences(context!!).token
            val job = RetrofitConfig().getLoginService().updateDeviceId(token, body)

            job.enqueue(object : Callback<DentistData> {
                override fun onResponse(call: Call<DentistData>, response: Response<DentistData>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Falhou !", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DentistData>, t: Throwable) {
                    Toast.makeText(context, "Falhou !", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun logDb() {
        val messages = MessageService.getInstance(context!!).messageDao().getAll()
        messages.forEach {
            Log.d(TAG, "Content => " + it.content)
        }
    }

}
