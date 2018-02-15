package unet.othello_android;

import android.os.AsyncTask;
import android.widget.Toast;


public class Timer2 extends AsyncTask<Void,Void,Boolean>
{
    GameActivity g;

    public Timer2()
    {

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... Voids)
    {
        try {Thread.sleep(750);} catch (InterruptedException e) { e.printStackTrace();}

        return true;
    }

    @Override
    protected void onPostExecute(Boolean b)
    {
        Toast.makeText(g.getApplicationContext(),"No hay jugada disponible, pierde el turno",Toast.LENGTH_SHORT).show();
        if(!g.turno)
        {
            g.ejecutar_timer();
        }
    }

}
