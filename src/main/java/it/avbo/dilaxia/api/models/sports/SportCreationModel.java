package it.avbo.dilaxia.api.models.sports;

import com.google.gson.annotations.SerializedName;

public class SportCreationModel {

	@SerializedName("id")
	private int id;
	@SerializedName("nome_sport")
	private String nome_sport;
	@SerializedName("descrizione")
	private String descrizione;

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
