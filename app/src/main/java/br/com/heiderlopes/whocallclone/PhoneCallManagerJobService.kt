package br.com.heiderlopes.whocallclone

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.telephony.TelephonyManager
import br.com.heiderlopes.whocallclone.api.getContatoAPI
import org.jetbrains.anko.doAsync

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class PhoneCallManagerJobService : JobService() {

    private var state: String? = null

    override fun onStartJob(job: JobParameters): Boolean {
        state = job.extras.getString(TelephonyManager.EXTRA_STATE)

        val numero = job?.extras?.getString("NUMBER")


        doAsync {
            val contato = getContatoAPI().buscarPor(numero!!).execute()
            val detailIntent = Intent(applicationContext, CallDetailActivity::class.java)
            detailIntent.putExtra("NUMBER", numero)
            detailIntent.putExtra("CONTATO", contato?.body())
            startActivity(detailIntent)
        }

        /*getContatoAPI().buscarPor(numero!!)
                .enqueue(object : Callback<Contato> {
                    override fun onFailure(call: Call<Contato>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<Contato>?, response: Response<Contato>?) {
                        if (response?.isSuccessful == true) {
                            val detailIntent = Intent(applicationContext, CallDetailActivity::class.java)
                            detailIntent.putExtra("NUMBER", numero)
                            detailIntent.putExtra("CONTATO", response?.body())
                            startActivity(detailIntent)
                        } else {
                            Log.i("TAG", "A")
                        }
                    }
                })*/
        return true
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }
}