package controle;

public class LeitorConsumo extends SerialCom {

	public static void main(String[] args) {
		
		
		//Iniciando leitura serial

        SerialComLeitura leitura = new SerialComLeitura("COM1", 9600, 0);

        leitura.HabilitarLeitura();

        leitura.ObterIdDaPorta();

        leitura.AbrirPorta();

        leitura.LerDados();
        
        //Controle de tempo da leitura aberta na serial
        try {

            Thread.sleep(6000);

        } catch (InterruptedException ex) {

            System.out.println("Erro na Thread: " + ex);

        }
        
        System.out.println(leitura.getConsumo());
        leitura.FecharCom();

    }

	}


