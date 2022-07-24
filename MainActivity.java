package br.com.dlweb.lvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Defini qual será o conteúdo da interface (arquivo XML da pasta RES)
        setContentView(R.layout.activity_main);

        // Se a instância for nula (primeiro acesso), carrega o fragment Main do objeto
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new MainFragment()).commit();
        }

    }
}