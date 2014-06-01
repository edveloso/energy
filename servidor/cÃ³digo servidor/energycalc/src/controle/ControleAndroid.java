package controle;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ControleAndroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ControleAndroid() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		execute(request, response);
			
		
	}
	
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		 try {
			 
							
				int length = request.getContentLength();
	            byte[] input = new byte[length];
	            ServletInputStream sin = request.getInputStream();
	            int c, count = 0 ;
	            while ((c = sin.read(input, count, input.length-count)) != -1) {
	                count +=c;
	            }
	            sin.close();
	            
	            SerialComLeitura leitura = new SerialComLeitura("COM1", 9600, 0);

				leitura.HabilitarLeitura();

				leitura.ObterIdDaPorta();

				leitura.AbrirPorta();

				leitura.LerDados();

				
				try {

					Thread.sleep(4000);
					leitura.FecharCom();
				} catch (InterruptedException ex) {

					System.out.println("Erro na Thread: " + ex);

				}
				
				
				String consumoPre = leitura.getConsumo();
				
				double consumo = Double.parseDouble(consumoPre);
				
				consumoPre = String.valueOf(consumo);

	            
	            response.setStatus(HttpServletResponse.SC_OK);
	            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
	            
	           writer.write(consumoPre);
	           writer.flush();
	           writer.close();
	    	 	
			 } catch (IOException e) {
	 
	        } 
		
	}
		 

}
