package com.woni.firefighter.noe;

import woni.FireFighter.FireFighterActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends FireFighterActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.configuration = new Configuration();
        super.onCreate(savedInstanceState);
    }

}
