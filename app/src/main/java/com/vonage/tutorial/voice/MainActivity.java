package com.vonage.tutorial.voice;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.nexmo.client.NexmoClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavManager.getInstance().init(navController);

        String[] callsPermissions = { Manifest.permission.RECORD_AUDIO };
        ActivityCompat.requestPermissions(this, callsPermissions, 123);

        new NexmoClient.Builder().build(this);
    }

    @Override
    public void onBackPressed() {
        FragmentManager childFragmentManager =
                getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager();

        Fragment currentNavigationFragment = childFragmentManager.getFragments().get(0);
        BackPressHandler backPressHandler = (BackPressHandler) currentNavigationFragment;

        if (backPressHandler != null) {
            backPressHandler.onBackPressed();
        }

        super.onBackPressed();
    }
}
