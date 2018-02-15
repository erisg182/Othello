package unet.othello_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import unet.pr43545_dnl.R;

public class GameActivity extends AppCompatActivity{

    GridLayout gridLayout;
    int tablero[][] = new int[8][8];
    int recompensa[][] = new int[8][8];
    boolean turno = true;
    int actual = 2;
    int contra = 1;

    TextView Tturno, blancas, negras, ganador, fin;

    ArrayList<Integer> casillas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridLayout = (GridLayout)findViewById(R.id.tablero);

        inicializar_valores();

        Tturno = (TextView) findViewById(R.id.turno);
        blancas = (TextView) findViewById(R.id.blancas);
        negras = (TextView) findViewById(R.id.negras);
        ganador = (TextView) findViewById(R.id.ganador);
        fin = (TextView) findViewById(R.id.fin);

        ((ImageButton)gridLayout.getChildAt(27)).setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        gridLayout.getChildAt(27).setVisibility(View.VISIBLE);
        ((ImageButton)gridLayout.getChildAt(28)).setBackgroundDrawable(getResources().getDrawable(R.drawable.black));
        gridLayout.getChildAt(28).setVisibility(View.VISIBLE);
        ((ImageButton)gridLayout.getChildAt(35)).setBackgroundDrawable(getResources().getDrawable(R.drawable.black));
        gridLayout.getChildAt(35).setVisibility(View.VISIBLE);
        ((ImageButton)gridLayout.getChildAt(36)).setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        gridLayout.getChildAt(36).setVisibility(View.VISIBLE);

        Intent i = getIntent();
        turno = i.getBooleanExtra("turno",true);

        if(!turno)
        {
            Timer1 t = new Timer1();
            t.g = this;
            t.execute();
        }

    }

    public void inicializar_valores()
    {
        for (int i = 0; i < 64; i++)
        {
            int x = (int)i/8;
            int y = i%8;
            gridLayout.getChildAt(i).setId(1000*x+10*y);
            tablero[x][y] = 0;
            recompensa[x][y] = 0;
        }

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    if((i==k || j==k) && recompensa[i][j]==0)
                    {
                        int val = (4-k)*10+50;
                        int bono = 0;

                        if(i%2==0)
                        {
                            bono+=5;
                        }
                        if(j%2==0)
                        {
                            bono+=5;
                        }
                        if(i==0 && j==0)
                        {
                            bono+=10;
                        }

                        val+=bono;

                        recompensa[i][j]=val;
                        recompensa[i][7-j]=val;
                        recompensa[7-i][j]=val;
                        recompensa[7-i][7-j]=val;
                    }
                }
            }
        }

        tablero[3][3] = 1;
        tablero[3][4] = 2;
        tablero[4][3] = 2;
        tablero[4][4] = 1;
    }

    public ArrayList buscar_jugadas(int tab[][],int a,int c)
    {
        ArrayList<Integer> jugadas = new ArrayList<>();
        for (int pos = 0; pos < 64; pos++)
        {
            int x = (int)pos/8;
            int y = pos%8;

            if(tab[x][y]==0)
            {
                int aux[][] = new int[8][8];

                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++)
                        aux[i][j] = tab[i][j];

                int cont = ejecutar_jugada(x, y, tab, false, a, c);


                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++)
                        tab[i][j] = aux[i][j];

                if (cont > 0) {
                    jugadas.add(pos);
                }
                casillas.clear();
            }

        }
        return jugadas;
    }

    public void cambiar_imagen(int pos)
    {
       if(contra==2)
            ((ImageButton)gridLayout.getChildAt(pos)).setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        else
            ((ImageButton)gridLayout.getChildAt(pos)).setBackgroundDrawable(getResources().getDrawable(R.drawable.black));
    }


    public void cambiar_color(int cas, int tab[][], boolean cambiar, int a)
    {
        int x = (int)(cas/8);
        int y = cas%8;
        tab[x][y]=a;
        if(cambiar)
        {
            tablero[x][y]=a;
            cambiar_imagen(cas);
        }
    }

    public void chequear_direccion(int x, int y, int dx, int dy, int tab[][], int a, int c)
    {
        int ax = x+dx;
        int ay = y+dy;
        boolean enc = true;
        int cont = 0;
        while(ax>=0 && ax<=7 && ay>=0 && ay<=7 && enc)
        {
            enc = (tab[ax][ay] == c);
            if(enc)
            {
                cont++;
                ax+=dx;
                ay+=dy;
            }
        }
        if(ax>=0 && ax<=7 && ay>=0 && ay<=7)
        {
            if(tab[ax][ay]==a && cont>0)
            {
                for (int i = 1; i <= cont; i++)
                {
                    casillas.add((x+i*dx)*8+(y+i*dy));
                }
            }
        }
    }

    public void actualizar_datos()
    {
        blancas.setText("x"+contar_fichas(tablero,1));
        negras.setText("x"+contar_fichas(tablero,2));

        if(actual==1)
        {
            Tturno.setText("Turno: blancas");
        }
        else
        {
            Tturno.setText("Turno: negras");
        }
    }

    public int ejecutar_jugada(int x, int y, int tab[][], boolean cambiar, int a, int c)
    {
        chequear_direccion(x,y,-1,0,tab,a,c);
        chequear_direccion(x,y,-1,1,tab,a,c);
        chequear_direccion(x,y,0,1,tab,a,c);
        chequear_direccion(x,y,1,1,tab,a,c);
        chequear_direccion(x,y,1,0,tab,a,c);
        chequear_direccion(x,y,1,-1,tab,a,c);
        chequear_direccion(x,y,0,-1,tab,a,c);
        chequear_direccion(x,y,-1,-1,tab,a,c);

        for (int cas : casillas)
        {
            cambiar_color(cas,tab,cambiar,a);
        }

        return casillas.size();
    }

    public void jugada(View view)
    {
        if(turno)
        {
            int id = view.getId();
            int x = (int) (id / 1000);
            int y = (id % 1000) / 10;
            if(tablero[x][y]==0)
            {
                ejecutar_jugada(x, y, tablero, true, actual, contra);
                if(casillas.size()>0)
                {
                    cambiar_imagen(x*8+y);
                    tablero[x][y]=actual;
                    int aux = contra;
                    contra = actual;
                    actual = aux;
                    casillas.clear();
                    turno=!turno;

                    actualizar_datos();

                    ArrayList<Integer> jug1 = buscar_jugadas(tablero,actual,contra);
                    ArrayList<Integer> jug2 = buscar_jugadas(tablero,contra,actual);

                    if(jug1.size()==0)
                    {
                        if (jug2.size() == 0)
                        {
                            int total_a = contar_fichas(tablero,1);
                            int total_b = contar_fichas(tablero,2);
                            if (total_a > total_b)
                                ganador.setText("Ganador: Blancas");
                            else if (total_b > total_a)
                                ganador.setText("Ganador: Negras");
                            else
                                ganador.setText("Empate");
                            fin.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            aux = contra;
                            contra = actual;
                            actual = aux;
                            turno=!turno;
                            Timer2 t = new Timer2();
                            t.g = this;
                            t.execute();
                        }
                    }
                    else
                    {
                        Timer1 t = new Timer1();
                        t.g = this;
                        t.execute();
                    }
                }
            }
        }
    }

    public int sumar_recompensa(int tab[][], int val)
    {
        int total=0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(tab[i][j]==val)
                {
                    total+=recompensa[i][j];
                }
            }
        }
        return total;
    }

    public int contar_fichas(int tab[][], int val)
    {
        int total=0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(tab[i][j]==val)
                    total++;
            }
        }
        return total;
    }

    public void ejecutar_timer()
    {
        Timer1 t = new Timer1();
        t.g = this;
        t.execute();
    }

    public int heuristica(int tab[][], int nivel, int a, int c, boolean paso)
    {
       if(nivel<2)
       {
           ArrayList<Integer> jugadas = buscar_jugadas(tab,a,c);
           int opt = -1;
           int comp = 0;

           if(nivel%2==0)
           comp = -20000;
           else
           comp = 20000;

           for (int jug: jugadas)
           {
               int px = (int)(jug/8);
               int py = jug%8;
               int aux[][] = new int[8][8];

               for (int i = 0; i < 8; i++)
                   for (int j = 0; j < 8; j++)
                       aux[i][j] = tab[i][j];

               ejecutar_jugada(px,py,tab,false,a,c);
               casillas.clear();
               tab[px][py]=a;
               int val=heuristica(tab,nivel+1,c,a,false);

               for (int i = 0; i < 8; i++)
                   for (int j = 0; j < 8; j++)
                       tab[i][j] = aux[i][j];

               if(nivel%2==0)
               {
                   if(val>comp)
                   {
                       comp=val;
                       opt=jug;
                   }
               }
               else
               {
                   if(val<comp)
                   {
                       comp=val;
                       opt=jug;
                   }
               }

           }

           if(jugadas.size()==0)
           {
               if (paso)
               {
                   int total_a = contar_fichas(tab,actual);
                   int total_c = contar_fichas(tab,contra);

                   if (total_a > total_c)
                   {
                       return 20000;
                   }
                   else if (total_c > total_a)
                   {
                       return -20000;
                   }
                   else
                   {
                       return 0;
                   }
               }
               else
               {
                   heuristica(tab,nivel+1,c,a,true);
               }
           }
           else
           {
               if(nivel>0)
                   return comp;
               else
                   return opt;
           }
       }
        int rec1 = sumar_recompensa(tab,actual)-sumar_recompensa(tab,contra);
        int rec2 = (contar_fichas(tab,actual)-contar_fichas(tab,contra))*150;

        return rec1+rec2;
    }

    public void jugar(int cod)
    {
        int x = (int) (cod / 8);
        int y = (cod % 8);

        ejecutar_jugada(x, y, tablero, true ,actual, contra);

        tablero[x][y] = actual;
        cambiar_imagen(x * 8 + y);
        int aux = contra;
        contra = actual;
        actual = aux;
        casillas.clear();
        turno = !turno;

        actualizar_datos();

        ArrayList<Integer> jug1 = buscar_jugadas(tablero,actual,contra);
        ArrayList<Integer> jug2 = buscar_jugadas(tablero,contra,actual);

        if(jug1.size()==0)
        {
            if(jug2.size()==0)
            {
                int total_a = contar_fichas(tablero,1);
                int total_b = contar_fichas(tablero,2);
                if (total_a > total_b)
                    ganador.setText("Ganador: Blancas");
                else if (total_b > total_a)
                    ganador.setText("Ganador: Negras");
                else
                    ganador.setText("Empate");
                fin.setVisibility(View.VISIBLE);
            }
            else
            {
                aux = contra;
                contra = actual;
                actual = aux;
                turno=!turno;
                Timer2 t = new Timer2();
                t.g = this;
                t.execute();
            }
        }
    }
}
