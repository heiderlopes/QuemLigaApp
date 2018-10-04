package br.com.heiderlopes.whocallclone

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.telephony.PhoneStateListener
import br.com.heiderlopes.whocallclone.model.Contato
import kotlinx.android.synthetic.main.activity_call_detail.*

class CallDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_detail)

        val phoneListener = PhoneCallListener()

        val telephonyManager = this
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE)

        val contato = intent?.extras?.getParcelable<Contato>("CONTATO")
        tvTipo.text = if (contato?.tipo?.isEmpty() == true) "Não identificado" else contato?.tipo
    }

    private inner class PhoneCallListener : PhoneStateListener() {

        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            when (state) {
                TelephonyManager.CALL_STATE_RINGING -> {
                }
                TelephonyManager.CALL_STATE_OFFHOOK -> {
                }
                TelephonyManager.CALL_STATE_IDLE -> {
                    finish()
                }
            }
        }
    }
}
