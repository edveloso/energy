package controle;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControleLeitura extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ControleLeitura() {
		super();
		
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			
			String cmd = request.getParameter("cmd");
			
			int horasDiaPre = Integer.parseInt(request.getParameter("horasDia"));
			
			int diaSemanaPre = Integer.parseInt(request.getParameter("diaSemana"));
			

			if (cmd.equalsIgnoreCase("calculaConsumo")) {

				SerialComLeitura leitura = new SerialComLeitura("COM1", 9600, 0);

				leitura.HabilitarLeitura();

				leitura.ObterIdDaPorta();

				leitura.AbrirPorta();

				leitura.LerDados();

				// Controle de tempo da leitura aberta na serial
				try {

					Thread.sleep(6000);
					leitura.FecharCom();
				} catch (InterruptedException ex) {

					System.out.println("Erro na Thread: " + ex);

				}

				 DecimalFormat fmt = new DecimalFormat("0.00");
				
				String consumoPre = leitura.getConsumo();
				double consumo = Double.parseDouble(consumoPre);
				
				double estimativa = ((((horasDiaPre * diaSemanaPre) * consumo)*4)/1000);
				double valorCobrado = (estimativa * 0.31);
				
				String valorCobradoFormat = fmt.format(valorCobrado);
				
				request.setAttribute("consumo", consumo);
				request.setAttribute("estimativa", estimativa);
				request.setAttribute("valorCobrado", valorCobradoFormat);
				

				request.getRequestDispatcher("result.jsp").forward(request,
						response);

			}
			
			
		} catch (Exception e) {
			request.setAttribute("msg", "Erro: " + e.getMessage());
			request.getRequestDispatcher("result.jsp").forward(request,
					response);
		}
	}

}
