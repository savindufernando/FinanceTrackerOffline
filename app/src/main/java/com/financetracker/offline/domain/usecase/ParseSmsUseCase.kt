package com.financetracker.offline.domain.usecase

import com.financetracker.offline.domain.model.Transaction
import com.financetracker.offline.domain.model.TransactionSource
import com.financetracker.offline.domain.parser.TransactionParserEngine
import com.financetracker.offline.domain.repository.SmsLogRepository
import com.financetracker.offline.domain.repository.TransactionRepository
import java.security.MessageDigest
import javax.inject.Inject

class ParseSmsUseCase @Inject constructor(
    private val smsLogRepository: SmsLogRepository,
    private val transactionRepository: TransactionRepository,
    private val categorizeTransactionUseCase: CategorizeTransactionUseCase
) {
    private val parserEngine = TransactionParserEngine()

    suspend operator fun invoke(smsBody: String, sender: String, timestamp: Long) {
        val hash = generateHash(smsBody, sender, timestamp)
        
        // 1. Duplicate protection check
        if (smsLogRepository.isSmsProcessed(hash)) {
            return
        }

        // 2. Parse SMS
        val parsedResult = parserEngine.parse(smsBody, timestamp, sender)
        
        if (parsedResult != null) {
            // 3. Categorize
            val categoryId = categorizeTransactionUseCase(parsedResult.merchant)

            // 4. Save to DB
            val transaction = Transaction(
                amount = parsedResult.amount,
                type = parsedResult.type,
                categoryId = categoryId,
                bankName = parsedResult.bankName,
                merchant = parsedResult.merchant,
                date = parsedResult.date,
                note = "Auto-parsed from SMS",
                source = TransactionSource.SMS
            )
            transactionRepository.insertTransaction(transaction)
            
            // 5. Mark SMS as processed
            smsLogRepository.markSmsAsProcessed(hash, sender, timestamp)
        }
    }

    private fun generateHash(body: String, sender: String, timestamp: Long): String {
        val input = "$body|$sender|$timestamp"
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
