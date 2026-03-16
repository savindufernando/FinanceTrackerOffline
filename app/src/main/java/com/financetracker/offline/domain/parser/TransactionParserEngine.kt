package com.financetracker.offline.domain.parser

import com.financetracker.offline.domain.model.TransactionType

data class ParsedSmsResult(
    val amount: Double,
    val type: TransactionType,
    val bankName: String,
    val merchant: String,
    val date: Long
)

class TransactionParserEngine {
    
    // Simple mock regex patterns for demonstration
    // E.g. "Rs 500.00 debited from A/c XX1234 to MerchantXYZ on 04/03/24"
    private val debitPattern = Regex("""(?:Rs\.?|INR)\s*([\d,]+\.?\d*).*debited.*to\s+([A-Za-z0-9\s]+)\s+on""", RegexOption.IGNORE_CASE)
    
    // E.g. "Rs 1500.00 credited to A/c XX1234 from EmployerXYZ on 04/03/24"
    private val creditPattern = Regex("""(?:Rs\.?|INR)\s*([\d,]+\.?\d*).*credited.*from\s+([A-Za-z0-9\s]+)\s+on""", RegexOption.IGNORE_CASE)

    fun parse(smsBody: String, timestamp: Long, sender: String): ParsedSmsResult? {
        val normalizedBody = smsBody.replace("\n", " ").trim()

        val debitMatch = debitPattern.find(normalizedBody)
        if (debitMatch != null) {
            val amountStr = debitMatch.groupValues[1].replace(",", "")
            val merchant = debitMatch.groupValues[2].trim()
            return ParsedSmsResult(
                amount = amountStr.toDoubleOrNull() ?: 0.0,
                type = TransactionType.EXPENSE,
                bankName = sender,
                merchant = merchant,
                date = timestamp
            )
        }

        val creditMatch = creditPattern.find(normalizedBody)
        if (creditMatch != null) {
            val amountStr = creditMatch.groupValues[1].replace(",", "")
            val merchant = creditMatch.groupValues[2].trim()
            return ParsedSmsResult(
                amount = amountStr.toDoubleOrNull() ?: 0.0,
                type = TransactionType.INCOME,
                bankName = sender,
                merchant = merchant,
                date = timestamp
            )
        }

        return null
    }
}
