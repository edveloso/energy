<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leitor de Porta Serial</title>
</head>
<body>
<hr>
	<h3>EnergyCalc</h3>

	<form action="ControleLeitura?cmd=calculaConsumo" method="post">

		Horas/Dia <select name="horasDia">
			<option selected value="0,25">15 Minutos</option>
			<option selected value="0,5">30 Minutos</option>
			<option selected value="0,75">45 Minutos</option>
			<option selected value="1">1 Hora</option>
			<option selected value="2">2 Horas</option>
			<option selected value="3">3 Horas</option>
			<option selected value="4">4 Horas</option>
			<option selected value="5">5 Horas</option>
			<option selected value="6">6 Horas</option>
			<option selected value="7">7 Horas</option>
			<option selected value="8">8 Horas</option>
			<option selected value="9">9 Horas</option>
			<option selected value="10">10 Horas</option>
			<option selected value="11">11 Horas</option>
			<option selected value="12">12 Horas</option>
			<option selected value="13">13 Horas</option>
			<option selected value="14">14 Horas</option>
			<option selected value="15">15 Horas</option>
			<option selected value="16">16 Horas</option>
			<option selected value="17">17 Horas</option>
			<option selected value="18">18 Horas</option>
			<option selected value="19">19 Horas</option>
			<option selected value="20">20 Horas</option>
			<option selected value="21">21 Horas</option>
			<option selected value="22">22 Horas</option>
			<option selected value="23">23 Horas</option>
			<option selected value="24">24 Horas</option>
		</select> <br> Dias/Semana <select name="diaSemana">
			<option selected value="1">1 Dia</option>
			<option selected value="2">2 Dias</option>
			<option selected value="3">3 Dias</option>
			<option selected value="4">4 Dias</option>
			<option selected value="5">5 Dias</option>
			<option selected value="6">6 Dias</option>
			<option selected value="7">7 Dias</option>
		</select> <br> <input type="submit" value="Calcular o consumo!" />

	</form>




<hr>
</body>
</html>