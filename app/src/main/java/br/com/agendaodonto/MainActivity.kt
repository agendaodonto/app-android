package br.com.agendaodonto

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        requestPermissionIfNecessary()
        log_id_button.setOnClickListener {
            logDeviceId()
        }

        update_id_button.setOnClickListener {
            updateDeviceIdOnServer()
        }

    }

    private fun requestPermissionIfNecessary() {
        val necessaryPermissions = arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS)

        val toBeRequested = ArrayList<String>()

        necessaryPermissions.iterator().forEach {
            if (ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED) {
                toBeRequested.add(it)
            }
        }
        if (toBeRequested.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, toBeRequested.toTypedArray(), 1)
        }
    }

    private fun logDeviceId() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Log.d(TAG, "Token => " + it.token)
        }
    }

    private fun updateDeviceIdOnServer() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            val body = TokenData(it.token)
            val token = "Token " + Preferences(this).token
            val job = RetrofitConfig().getLoginService().updateDeviceId(token, body)

            job.enqueue(object : Callback<DentistData> {
                override fun onResponse(call: Call<DentistData>, response: Response<DentistData>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Sucesso !", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Falhou !", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DentistData>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Falhou !", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}
