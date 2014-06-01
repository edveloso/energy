package com.example.clienteenergycalc;

public class Aparelho {
     
    //variaveis
    int id;
    String nome;
    Double consumo;
	//Date dataLeitura;
     
    // construtor default
    public Aparelho(){
         
    }
    // construtor
    public Aparelho(String nome, Double consumo){
        
        this.nome = nome;
        this.consumo = consumo;
		//this.dataLeitura = dataLeitura;
    }
     
    // GET ID
    public int getID(){
        return this.id;
    }
     
    // setando id
    public void setID(int id){
        this.id = id;
    }
     
    // GET nome
    public String getNome(){
        return this.nome;
    }
     
    // setando nome
    public void setNome(String nome){
        this.nome = nome;
    }
     
    // GET consumo
    public Double getConsumo(){
        return this.consumo;
    }
     
    // setando consumo
    public void setConsumo(Double consumo){
        this.consumo = consumo;
    }
    
    @Override
    public String toString () {
        return nome;
    }
}