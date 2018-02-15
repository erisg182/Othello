package unet.othello_android;

import android.os.AsyncTask;


public class Timer1 extends AsyncTask<Void,Void,Boolean>
{
    GameActivity g;

    public Timer1()
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
        try {Thread.sleep(1500);} catch (InterruptedException e) { e.printStackTrace();}

        return true;
    }

    @Override
    protected void onPostExecute(Boolean b)
    {
        super.onPostExecute(b);
        int jug = g.heuristica(g.tablero, 0, g.actual, g.contra, false);
        g.jugar(jug);

    }

}
