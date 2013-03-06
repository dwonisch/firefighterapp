package woni.firefighter.stmk;

import woni.FireFighter.FireFighterActivity;
import android.os.Bundle;

public class MainActivity extends FireFighterActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.configuration = new Configuration();
        super.onCreate(savedInstanceState);
    }
}
