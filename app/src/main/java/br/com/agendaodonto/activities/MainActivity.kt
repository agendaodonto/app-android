package br.com.agendaodonto.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import br.com.agendaodonto.R
import br.com.agendaodonto.fragments.HomeFragment
import br.com.agendaodonto.fragments.MessagesFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    private lateinit var homeFragment: HomeFragment
    private lateinit var messagesFragment: MessagesFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                setFragment(messagesFragment)
                return@OnNavigationItemSelectedListener true
            }
        }

        return@OnNavigationItemSelectedListener false
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        requestPermissionIfNecessary()

        homeFragment = HomeFragment()
        messagesFragment = MessagesFragment()
        setFragment(homeFragment)
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
}
