package controle;

public class CalcEstimativa {
	
	private float valorConsumo;
	
	
	public float calculaEstimativa(int horasDia, int diaSemana, int consumo){
		
		valorConsumo = ((horasDia * diaSemana) * consumo) * 4;
		
		return valorConsumo;
		
	}

}
