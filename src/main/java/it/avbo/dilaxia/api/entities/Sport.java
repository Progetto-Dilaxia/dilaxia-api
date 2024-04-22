package it.avbo.dilaxia.api.entities;

public class Sport{
	
	private int id;
	private String nome_sport;
	private String descrizione;

	public Sport(int id, String nome_sport, String descrizione) {
		super();
		this.id = id;
		this.nome_sport = nome_sport;
		this.descrizione = descrizione;
	}

	public int getId() {
		return id;
	}

	public String getNome_sport() {
		return nome_sport;
	}


	public String getDescrizione() {
		return descrizione;
	}

}