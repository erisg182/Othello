package unet.othello_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import unet.pr43545_dnl.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jugar(View view)
    {
        Intent i = new Intent(this.getApplicationContext(),GameActivity.class);
        i.putExtra("turno",true);
        startActivity(i);
    }

    public void jugar2(View view)
    {
        Intent i = new Intent(this.getApplicationContext(),GameActivity.class);
        i.putExtra("turno",false);
        startActivity(i);
    }

    public void inst(View view)
    {
        Intent i = new Intent(this.getApplicationContext(),InstActivity.class);
        startActivity(i);
    }
}
