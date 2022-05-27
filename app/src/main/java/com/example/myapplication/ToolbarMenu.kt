package com.example.myapplication

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class ToolbarMenu(val activity: AppCompatActivity, val menu: Menu) {

    init {
        Log.i("info", menu.size().toString())

        val inflater = activity.menuInflater;
        inflater.inflate(R.menu.menu_main, menu)

        val settingsItem = menu.findItem(R.id.action_settings)
        settingsItem.setOnMenuItemClickListener (
            MenuItem.OnMenuItemClickListener {
                onClickPref();
            }
        );
    }


    private fun onClickPref(): Boolean {
        val intent = Intent(activity, SettingsActivity::class.java)
        activity.startActivity(intent)
        return true;
    }
}
