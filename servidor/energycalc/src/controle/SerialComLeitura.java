package controle;

import gnu.io.CommPortIdentifier;

import gnu.io.SerialPort;

import gnu.io.SerialPortEvent;

import gnu.io.SerialPortEventListener;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Iterator;


public class SerialComLeitura implements Runnable, SerialPortEventListener {
	
	public String Dadoslidos;

	public int nodeBytes;

	private int baudrate;

	private int timeout;

	private CommPortIdentifier cp;

	private SerialPort porta;

	private OutputStream saida;

	private InputStream entrada;

	private Thread threadLeitura;

	private boolean IDPortaOK;

	private boolean PortaOK;

	private boolean Leitura;

	private boolean Escrita;

	private String Porta;

	protected String consumo;

	public void setConsumo(String consumo){

		        this.consumo = consumo;

}

 

public String getConsumo(){

        return consumo;

}

public SerialComLeitura( String p , int b , int t ){

    this.Porta = p;

    this.baudrate = b;

    this.timeout = t;

}

public void HabilitarEscrita(){

    Escrita = true;

    Leitura = false;

}



public void HabilitarLeitura(){

    Escrita = false;

    Leitura = true;

}

public void ObterIdDaPorta(){

    try {

        cp = CommPortIdentifier.getPortIdentifier(Porta);

        if ( cp == null ) {

        	System.out.println("Erro na porta!");
            

            IDPortaOK = false;

            System.exit(1);

        }

        IDPortaOK = true;

    } catch (Exception e) {

    	System.out.println("Erro no ID da porta");
        

        IDPortaOK = false;

        System.exit(1);

    }

}

public void AbrirPorta(){

    try {

        porta = (SerialPort)cp.open("SerialComLeitura", timeout);

        PortaOK = true;

        //configurar parāmetros

        porta.setSerialPortParams(baudrate,

        porta.DATABITS_8,

        porta.STOPBITS_1,

        porta.PARITY_NONE);

        porta.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

    }catch(Exception e){

        PortaOK = false;

        System.out.println("Erro na abertura de conexao");
        
        System.exit(1);

    }

}

public void LerDados(){

    if (Escrita == false){

        try {

            entrada = porta.getInputStream();

        } catch (Exception e) {
        	
        	System.out.println("Erro de Stream " + e);

            

            System.exit(1);

        }

        try {

            porta.addEventListener(this);

        } catch (Exception e) {

        	System.out.println("Erro de Listener " + e);
            

            System.exit(1);

        }

        porta.notifyOnDataAvailable(true);

        try {

            threadLeitura = new Thread(this);

            threadLeitura.start();

           run();

        } catch (Exception e) {

        	System.out.println("Erro de Thread" + e);
            

        }

    }

}

public void EnviarUmaString(String msg){

    if (Escrita==true) {

        try {

            saida = porta.getOutputStream();

            System.out.println("FLUXO OK!");

        } catch (Exception e) {

            System.out.println("Erro.STATUS: " + e );

        }

        try {

            System.out.println("Enviando um byte para " + Porta );

            System.out.println("Enviando : " + msg );

            saida.write(msg.getBytes());

            Thread.sleep(100);

            saida.flush();

        } catch (Exception e) {

            System.out.println("Houve um erro durante o envio. ");

            System.out.println("STATUS: " + e );

            System.exit(1);

        }

    } else {

        System.exit(1);

    }

}



public void run(){

    try {

        Thread.sleep(5);

    } catch (Exception e) {

    	System.out.println("Erro de Thread " + e);
        

    }

}

public void serialEvent(SerialPortEvent ev){       

    StringBuffer bufferLeitura = new StringBuffer();

    int novoDado = 0;

   

    switch (ev.getEventType()) {

        case SerialPortEvent.BI:

        case SerialPortEvent.OE:

        case SerialPortEvent.FE:

        case SerialPortEvent.PE:

        case SerialPortEvent.CD:

        case SerialPortEvent.CTS:

        case SerialPortEvent.DSR:

        case SerialPortEvent.RI:

        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:

        break;

        case SerialPortEvent.DATA_AVAILABLE:

            //Novo algoritmo de leitura.

            while(novoDado != -1){

                try{

                    novoDado = entrada.read();

                    if(novoDado == -1){

                        break;

                    }

                    if('\r' == (char)novoDado){

                        bufferLeitura.append('\n');

                    }else{

                        bufferLeitura.append((char)novoDado);

                    }

                }catch(IOException ioe){
                	
                	System.out.println("Erro de leitura " + ioe);
                    

                }

            }

            setConsumo(new String(bufferLeitura));

            //System.out.println(getConsumo());

        break;

    }

}


public void FecharCom(){

    try {

        porta.close();

    } catch (Exception e) {

    	System.out.println("Erro de fechamento de porta " + e);
        

        System.exit(0);

    }

}

public String obterPorta(){

    return Porta;

}



public int obterBaudrate(){

    return baudrate;

}

}

