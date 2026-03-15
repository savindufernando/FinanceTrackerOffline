package com.financetracker.offline.data.local

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

data class RawSms(
    val body: String,
    val sender: String,
    val timestamp: Long
)

class SmsReader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun readBankSms(sinceTimestamp: Long = 0): List<RawSms> {
        val smsList = mutableListOf<RawSms>()
        val uri = Uri.parse("content://sms/inbox")
        val projection = arrayOf(
            Telephony.Sms.BODY,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.DATE
        )
        
        // Basic filter: only read messages that might be bank related (usually contain numbers or short alpha codes)
        // For simplicity, we read all recent and let the parser filter them out, or we could add a SQL filter here.
        val selection = "${Telephony.Sms.DATE} > ?"
        val selectionArgs = arrayOf(sinceTimestamp.toString())
        val sortOrder = "${Telephony.Sms.DATE} DESC"

        try {
            context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)?.use { cursor ->
                val bodyIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)
                val addressIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
                val dateIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)

                while (cursor.moveToNext()) {
                    val body = cursor.getString(bodyIndex)
                    val sender = cursor.getString(addressIndex)
                    val date = cursor.getLong(dateIndex)
                    
                    // Simple heuristic: bank senders usually don't have user phone numbers (start with + typically)
                    // Or they contain known keywords. For offline tracker, we process all and let parser drop invalid ones.
                    smsList.add(RawSms(body, sender, date))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return smsList
    }
}
