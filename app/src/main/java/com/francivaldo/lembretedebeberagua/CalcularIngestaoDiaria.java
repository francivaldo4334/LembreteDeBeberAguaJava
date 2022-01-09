package com.francivaldo.lembretedebeberagua;

public class CalcularIngestaoDiaria {
    private double ML_JOVEM = 40.0;
    private double ML_ADULTO = 35.0;
    private double ML_IDOSO = 30.0;
    private double ML_MAIS_DE_66_ANOS = 25.0;

    private double resultado = 0.0;
    private double resultado_total_ml = 0.0;

    public void CalcularTotal(double peso,int idade){
        if (idade <= 17){
            resultado = peso * ML_JOVEM;
            resultado_total_ml = resultado;
        }else if(idade <= 35){
            resultado = peso * ML_ADULTO;
            resultado_total_ml = resultado;
        }else if(idade <= 65){
            resultado = peso * ML_IDOSO;
            resultado_total_ml = resultado;
        }else{
            resultado = peso * ML_MAIS_DE_66_ANOS;
            resultado_total_ml = resultado;
        }
    }
    public double ResultadoML(){
        return resultado_total_ml;
    }
}
