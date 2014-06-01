package com.example.clienteenergycalc;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Resultado extends Activity implements OnClickListener {

	Button voltarInicio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultado);
		
		TextView itemNome = (TextView) findViewById(R.id.nome_aparelho);
		TextView itemConsumo = (TextView) findViewById(R.id.consumo_aparelho);
		TextView itemEstimativa = (TextView) findViewById(R.id.gasto_total);
		TextView itemValorCobrado = (TextView) findViewById(R.id.valor_cobrado);
		
		voltarInicio = (Button) findViewById(R.id.voltar_inicio);
		voltarInicio.setOnClickListener(this);
				
		Intent intent = getIntent();
		Bundle params = intent.getExtras();  
		 if(params!=null)
		  {   
		   String nome = params.getString("nome");
		   itemNome.setText(nome);
		   
		  String consumo = String.valueOf(params.getDouble("consumo"));
		  itemConsumo.setText("Consumo atual: " + consumo + " Watts");
		   
		   String estimativa = String.valueOf(params.getDouble("estimativa"));
		   itemEstimativa.setText("Consumo total: " + estimativa + " KWh");
		   
		   Double valorCobrado = params.getDouble("valorCobrado");
		   DecimalFormat fmt = new DecimalFormat("0.00");        
      	   String valorCobradoFormat = fmt.format(valorCobrado);
		   itemValorCobrado.setText("Valor Cobrado: " + valorCobradoFormat + " R$ (LIGHT)");
		   
		   
		   
		  }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.resultado, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		 switch (v.getId()){
		 
		case R.id.voltar_inicio:
			
			startActivity(new Intent(this, TelaInicial.class));
			
			
			break;
		 
		 }
		
	}

}
