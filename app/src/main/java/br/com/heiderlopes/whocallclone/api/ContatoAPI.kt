package br.com.heiderlopes.whocallclone.api

import br.com.heiderlopes.whocallclone.model.Contato
import retrofit2.Call
import retrofit2.http.*

interface ContatoAPI {

    @GET("/quemliga/{numero}")
    fun buscarPor(@Path("numero") numero: String): Call<Contato>

    @POST("/quemliga")
    fun salvar(@Body contato: Contato): Call<Contato>
}