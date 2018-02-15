package unet.othello_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import unet.pr43545_dnl.R;

public class InstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inst);

        TextView inst1 = (TextView)findViewById(R.id.instrucciones1);
        inst1.setText("Durante su turno, coloque una pieza en la cuadrícula para que se alinee con otra pieza suya en una línea recta, mientras que al menos una pieza del oponente esté completamente rodeada y se convertirá en su pieza.");
        TextView inst2 = (TextView)findViewById(R.id.instrucciones2);
        inst2.setText("No puedes moverte sin capturar piezas. Cuando ambos jugadores no pueden moverse, el juego termina.");
        TextView inst3 = (TextView)findViewById(R.id.instrucciones3);
        inst3.setText("El jugador con más piezas convertidas a su color correspondiente gana.");
    }
}
