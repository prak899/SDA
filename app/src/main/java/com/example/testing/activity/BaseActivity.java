package com.example.testing.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.fragments.BuyFragment;
import com.example.testing.fragments.SellFragment;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private FragmentManager mfragmentManager;
    private BuyFragment buyFragment;
    private SellFragment sellFragment;
    private String fragmentChecker = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();

        Intent intent = getIntent();
        fragmentChecker = intent.getStringExtra("whichFragment");
        if (null != fragmentChecker) {
            switch (fragmentChecker) {
                case "one":
                    fragmentViewer(buyFragment);
                    break;
                case "two":
                    fragmentViewer(sellFragment);
                    break;
                default:
                    Toast.makeText(this, "Oops we will solve this asap", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void fragmentViewer(Fragment fragment) {
        mfragmentManager.beginTransaction()
                .replace(R.id.adapterFrame, fragment)
                .addToBackStack(null) // Optional: Add to back stack
                .commit();
    }

    private void init() {
        mfragmentManager = getSupportFragmentManager();
        buyFragment = new BuyFragment();
        sellFragment = new SellFragment();

        //get bundle data
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle){
            Bundle bundle1 = new Bundle();
            bundle1.putString("carData", bundle.getString("carData"));
            if (Objects.equals(fragmentChecker, "one"))
                buyFragment.setArguments(bundle1);
            else if (Objects.equals(fragmentChecker, "two"))
                sellFragment.setArguments(bundle1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //moveTaskToBack(true);

    }
}