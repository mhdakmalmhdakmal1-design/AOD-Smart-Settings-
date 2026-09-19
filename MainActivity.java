package com.example.aodsettings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Switch switchService;
    private Switch switchTorch;
    private Switch switchRecorder;
    private Switch switchMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        switchService = findViewById(R.id.switchService);
        switchTorch = findViewById(R.id.switchTorch);
        switchRecorder = findViewById(R.id.switchRecorder);
        switchMusic = findViewById(R.id.switchMusic);

        switchService.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                    Toast.makeText(this, "Please enable overlay permission", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "AOD Service Enabled", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "AOD Service Disabled", Toast.LENGTH_SHORT).show();
            }
        });

        switchTorch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
                    switchTorch.setChecked(false);
                } else {
                    Toast.makeText(this, "Torch Shortcut Enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchRecorder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 102);
                    switchRecorder.setChecked(false);
                } else {
                    Toast.makeText(this, "Audio Recorder Enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Music Controls Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Music Controls Disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

