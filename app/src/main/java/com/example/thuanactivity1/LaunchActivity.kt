package com.example.thuanactivity1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.*

lateinit var textView: TextView
var GAME_STATE_KEY = "GAME_STATE_KEY"

// some transient state for the activity instance
var gameState: String? = null

class LaunchActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState)

        // recovering the instance state
        gameState = savedInstanceState?.getString(GAME_STATE_KEY)
        if (gameState == null)
            gameState = "LEVEL 1"
        // set the user interface layout for this activity
        // the layout file is defined in the project res/layout/main_activity.xml file
        setContentView(R.layout.activity_launch)

        // initialize member TextView so we can manipulate it later
        textView = findViewById(R.id.textView)

        Log.v("MYAPP", "The application has been created")

        findViewById<Button>(R.id.callButton).setOnClickListener {
            val callUri: Uri = Uri.parse("tel:5551234")
            val callIntent = Intent(Intent.ACTION_DIAL, callUri)
            startActivity(callIntent)
        }
        findViewById<Button>(R.id.gotoBrno).setOnClickListener {
            Log.v("MYAPP", "Go to Brno called")
            // Map point based on address
            val mapIntent: Intent = Uri.parse(
                "geo:0,0?q=" + Uri.encode("Brno, Czech Republic")
            ).let { location ->
                Intent(Intent.ACTION_VIEW, location)
            }
            startActivity(mapIntent);
        }
        findViewById<Button>(R.id.openUtb).setOnClickListener {
            Log.v("MYAPP", "Go to UTB website called")
            val webIntent: Intent = Uri.parse("https://www.utb.cz").let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            startActivity(webIntent);
        }
        findViewById<Button>(R.id.setEvent).setOnClickListener {
            Log.v("MYAPP", "Go to Calendar")
            // Event is on January 23, 2021 -- from 7:30 AM to 10:30 AM.
            val calendarIntent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
                val eventTime: Calendar = Calendar.getInstance().apply {
                    set(2022, 12, 31)
                }
                putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, eventTime.timeInMillis)
                putExtra(CalendarContract.Events.TITLE, "New Year's Eve")
                putExtra(CalendarContract.Events.EVENT_LOCATION, "My room")
            }
            startActivity(calendarIntent);
        }
    }
    // This callback is called only when there is a saved instance that is previously saved by using
    // onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the one used in onCreate().
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        textView.text = savedInstanceState?.getString(GAME_STATE_KEY)
        Log.v("MYAPP", "Restored")
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    override fun onSaveInstanceState(outState: Bundle) {
        outState?.run {
            putString(GAME_STATE_KEY, gameState)
        }
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState)
        Log.v("MYAPP", "Saved" + gameState)
    }
    override fun onStart()
    {
        super.onStart()
        Log.v("MYAPP", "The application has started")
    }

    override fun onResume()
    {
        super.onResume()
        Log.v("MYAPP", "The application has resumed")
    }

    override fun onPause()
    {
        super.onPause()
        Log.v("MYAPP", "The application has paused")
    }

    override fun onDestroy()
    {
        super.onDestroy()
        Log.v("MYAPP", "The application has stopped")
    }
}