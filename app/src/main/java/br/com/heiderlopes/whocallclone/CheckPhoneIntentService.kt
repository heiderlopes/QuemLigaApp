package br.com.heiderlopes.whocallclone

import android.app.IntentService
import android.content.Intent
import br.com.heiderlopes.whocallclone.api.getContatoAPI

class CheckPhoneIntentService : IntentService("CheckPhoneIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val numero = intent?.extras?.getString("NUMBER")
        val contato = getContatoAPI().buscarPor(numero!!).execute()
        val detailIntent = Intent(this, CallDetailActivity::class.java)
        detailIntent.putExtra("NUMBER", numero)
        detailIntent.putExtra("CONTATO", contato.body())
        startActivity(detailIntent)
    }
}