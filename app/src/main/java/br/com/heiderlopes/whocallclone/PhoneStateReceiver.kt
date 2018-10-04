package br.com.heiderlopes.whocallclone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.os.Build
import android.os.PersistableBundle
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.app.job.JobScheduler
import android.app.job.JobInfo
import android.content.ComponentName

class PhoneStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val number = intent.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)

        when (state) {
            TelephonyManager.EXTRA_STATE_RINGING -> startService(context, number!!, intent)

            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
            }
            TelephonyManager.EXTRA_STATE_IDLE -> {
            }
        }
    }

    private fun startService(context: Context, number: String, intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scheduleJob(context, intent)
        } else {
            callIntentService(context, number)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun scheduleJob(context: Context, intent: Intent) {
        val serviceComponent = ComponentName(context, PhoneCallManagerJobService::class.java)

        //SetMinimuLatency: Especifique que este trabalho deve ser atrasado pelo período de tempo fornecido.
        //SetOverrideDealine: Defina o prazo final, que é a latência máxima de agendamento.
        val builder = JobInfo.Builder(0, serviceComponent)
                .setExtras(convertBundle(intent.extras))
                .setMinimumLatency(1000)
                .setOverrideDeadline((3 * 1000).toLong())
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun convertBundle(bundle: Bundle?): PersistableBundle {
        val persistableBundle = PersistableBundle()
        persistableBundle.putString("NUMBER", bundle?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER))
        return persistableBundle
    }

    private fun callIntentService(context: Context, number: String) {
        val checkServiceIntent = Intent(context, CheckPhoneIntentService::class.java)
        checkServiceIntent.putExtra("NUMBER", number)
        context.startService(checkServiceIntent)
    }
}