package com.example.clienteenergycalc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaInicial extends Activity implements OnClickListener {
	
	Spinner aparelhos;
	Spinner horas;
	Spinner dias;
	
	Integer horaInt;
	Integer diaInt;
	Integer tempoTotalInt;
	
	String horaSelecionada;
	String diaSelecionado;
	String tempoTotal;
	
	
	Button calcular;
	Button novoAparelho;

	
	TextView consumo;
	TextView estimativa;
	TextView cobrado;
	
	EditText itemNome;
	
	String nomeAparelho;
	
	Integer doubledValue=0;
	
	String valorRetornado;
	
	Double valorConsumo;
	
	Double valorRetornadoConvert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_inicial);
	
		aparelhos = (Spinner) findViewById(R.id.aparelhos);
		horas = (Spinner) findViewById(R.id.horas);
		dias = (Spinner) findViewById(R.id.dias);
	
		itemNome = (EditText) findViewById(R.id.nome_aparelho);
		
		consumo = (TextView) findViewById(R.id.textView3);
		estimativa = (TextView) findViewById(R.id.textView4);
		cobrado = (TextView) findViewById(R.id.textView5);
				
		calcular = (Button) findViewById(R.id.button1);
	    calcular.setOnClickListener(this);
	    
	    novoAparelho = (Button) findViewById(R.id.novo_aparelho);
	    novoAparelho.setOnClickListener(this);
	    

	    
	    carregarSpinner();
	
	
		
	     
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		 
        switch (v.getId()){
        case R.id.button1:
        
        horaSelecionada = String.valueOf(horas.getSelectedItem());
		diaSelecionado = String.valueOf(dias.getSelectedItem());
        	
		horaInt = Integer.parseInt(horaSelecionada);
		diaInt = Integer.parseInt(diaSelecionado);
		tempoTotalInt = horaInt * diaInt;
          	
		tempoTotal = String.valueOf(tempoTotalInt); 
		
		nomeAparelho =  String.valueOf(( (Aparelho) aparelhos.getSelectedItem()).getNome()) ;
		
		consumo.setText("Calculando...");
          	
		  final DatabaseHandler db = new DatabaseHandler(this);
		  final Intent intent = new Intent(this, Resultado.class);
              new Thread(new Runnable() {
                    public void run() {
 
                        try{
                            URL url = new URL("http://192.168.0.102:8080/EnergyCalc/ControleAndroid");
                            URLConnection connection = url.openConnection();
 
                            String inputString = tempoTotalInt.toString();
                            //inputString = URLEncoder.encode(inputString, "UTF-8");
 
                            Log.d("inputString", inputString);
 
                            connection.setDoOutput(true);
                            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                            out.write(inputString);
                            out.close();
                  
                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
                            String returnString="";
                            doubledValue =0;
 
                            while ((returnString = in.readLine()) != null) 
                            {
                                valorRetornado= returnString;
                            }
                            in.close();
 
                            valorRetornadoConvert = Double.parseDouble(valorRetornado);
                            
                            final Double valorEstimativa = (((valorRetornadoConvert * tempoTotalInt)*4)/1000);
                            
                            final Double valorCobrado = valorEstimativa * 0.31;
 
                            runOnUiThread(new Runnable() {
                                 public void run() {
                                	 
                                	
                                	 
                                     //consumo.setText("Consumo: " + valorRetornado + "Wh");
                                     //estimativa.setText("Estimativa de Gasto: " + valorEstimativa +"KW/h");
                                     //cobrado.setText("Valor cobrado no mês(LIGHT): " + valorCobradoFormat + "R$");
                                     
                                     Log.d("Insert: ", "Inserindo ..."); 
                                     db.addAparelho(new Aparelho(nomeAparelho, valorRetornadoConvert));
                                     
                                  // Ler contatos
                                     Log.d("Lendo: ", "Lendo todos os contatos.."); 
                                     List<Aparelho> aparelhos = db.getAllAparelhos();       
                                      
                                     for (Aparelho ap : aparelhos) {
                                         String log = "Id: " + ap.getID()+" ,Nome: " + ap.getNome() + " ,Consumo: " + ap.getConsumo();
                                             // Writing Contacts to log
                                     Log.d("Nome: ", log);
                                 }
                                     
                                  // INICIA ACTIVITY COM O RESULTADO, PASSANDO TODOS OS PARAMETROS NECESSARIOS
                                     
                                     Bundle parametros = new Bundle();
                                     parametros.putString("nome", nomeAparelho);
                                     parametros.putDouble("consumo", valorRetornadoConvert);
                                     parametros.putDouble("estimativa", valorEstimativa);
                                     parametros.putDouble("valorCobrado", valorCobrado);
                                     intent.putExtras(parametros);
                                     consumo.setText("");
                                     startActivity(intent);
                                     
                                }
                            });
 
                            }catch(Exception e)
                            {
                                Log.d("Exception",e.toString());
                            }
 
                    }
                  }).start();
             
            break;
            
        case R.id.novo_aparelho:
        	
        	startActivity(new Intent(this, NovoAparelho.class));
        	
        	break;
        	
       
            
            }
        
        }
	
	private void carregarSpinner() {
	    // Instancia a DatabaseHandler
	    DatabaseHandler db = new DatabaseHandler(getApplicationContext()); 
	    // define a lista que sera utilizada no Spinner
	    List <Aparelho> itens = db.getNomeAparelhos();
	    // Cria um adapter para o spinner
	    ArrayAdapter<Aparelho> dataAdapter = new ArrayAdapter<Aparelho>(this,
	    android.R.layout.simple_spinner_item, itens);
	 // Seta o recurso de dropdown do adapter que vai ser usado no spinner
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    // vincula o adapter ao spinner
	    aparelhos.setAdapter(dataAdapter);
	}
}

