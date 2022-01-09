package com.francivaldo.lembretedebeberagua;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button bt_calcular,bt_lembrete,bt_alarme;
    private EditText edt_peso;
    private EditText edt_idade;
    private CalcularIngestaoDiaria calcularIngestaoDiaria;
    private TextView txt_resultado_ml,txt_hora,txt_minutos;
    private ImageView ic_redefinir_dados;
    private double resultadoMl = 0.0;
    private Calendar calendario;
    private int horaAtual;
    private int minutosAtuais;
    private TimePickerDialog timePickerdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_calcular = findViewById(R.id.bt_calcular);
        edt_peso = findViewById(R.id.edt_peso);
        edt_idade = findViewById(R.id.edt_idade);
        txt_resultado_ml = findViewById(R.id.txt_resultado_ml);
        ic_redefinir_dados = findViewById(R.id.ic_redefinir);
        bt_lembrete = findViewById(R.id.bt_definir_lembrete);
        txt_hora = findViewById(R.id.txt_hora);
        txt_minutos = findViewById(R.id.txt_minutos);
        bt_alarme = findViewById(R.id.bt_alarme);
        calcularIngestaoDiaria = new CalcularIngestaoDiaria();

        bt_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_peso.getText().toString())){
//                    Toast.makeText(this,"informe seu peso",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(edt_idade.getText().toString())){
//                    Toast.makeText(this,"informe sua idade", Toast.LENGTH_SHORT).show();
                }else{
                    double peso = Integer.getInteger(edt_peso.getText().toString());
                    int idade = Integer.getInteger(edt_idade.getText().toString());
                    calcularIngestaoDiaria.CalcularTotal(peso,idade);
                    resultadoMl = calcularIngestaoDiaria.ResultadoML();
                    NumberFormat formatar = NumberFormat.getNumberInstance(new Locale("pt-BR"));
                    formatar.setGroupingUsed(false);
                    txt_resultado_ml.setText(formatar.format(resultadoMl) + " "+"ml");
                }
            }
        });
        ic_redefinir_dados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Redefinir Dados")
                        .setMessage("deseja excluir todos os dados existentes?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                edt_peso.setText("");
                                edt_idade.setText("");
                                txt_resultado_ml.setText("");
                            }
                        });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });
        bt_lembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendario = Calendar.getInstance();
                horaAtual = calendario.get(Calendar.HOUR_OF_DAY);
                minutosAtuais = calendario.get(Calendar.MINUTE);
                timePickerdialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        txt_hora.setText(String.format("%02d",hourOfDay));
                        txt_minutos.setText(String.format("%02d",minutes));
                    }
                },horaAtual,minutosAtuais,true);
                timePickerdialog.show();
            }
        });
        bt_alarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(txt_hora.getText().toString()) && !TextUtils.isEmpty(txt_minutos.getText().toString())){
                    int hour,minute;
                    hour = Integer.parseInt(txt_hora.getText().toString());
                    minute = Integer.parseInt(txt_minutos.getText().toString());
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_HOUR,hour);
                    intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE,"hora de beber agua!");
                    if(hour <=24 && minute <= 60){
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this,"formato de hora nao aceiravel",Toast.LENGTH_LONG);
                    }
                }else{
                    Toast.makeText(MainActivity.this,"preencha os campos",Toast.LENGTH_LONG);
                }
            }
        });

    }
}