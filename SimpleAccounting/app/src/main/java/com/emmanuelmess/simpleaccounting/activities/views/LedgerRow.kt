package com.emmanuelmess.simpleaccounting.activities.views

import android.content.Context
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.View
import android.widget.TableRow
import android.widget.TextView

import com.emmanuelmess.simpleaccounting.R
import com.emmanuelmess.simpleaccounting.data.LedgerRowData
import java.lang.IllegalStateException

import java.math.BigDecimal

class LedgerRow(context: Context, attrs: AttributeSet) : TableRow(context, attrs) {

    lateinit var formatter: LedgerView.BalanceFormatter

    private lateinit var dateText: TextView
    private lateinit var referenceText: TextView
    private lateinit var creditText: TextView
    private lateinit var debitText: TextView

    private lateinit var balanceText: TextView

    private var finishedInflating = false
    private var afterInflation: (() -> Unit)? = null

    val date: CharSequence
        get() = dateText.text

    val credit: CharSequence
        get() = creditText.text

    val debit: CharSequence
        get() = debitText.text

    fun setDataDelayed(
        ledgerRowData: LedgerRowData,
        invertCreditDebit: Boolean
    ) {
        if(!finishedInflating) {
            afterInflation = { setData(ledgerRowData, invertCreditDebit) }
        } else {
            setData(ledgerRowData, invertCreditDebit)
        }
    }

    private fun setData(
        ledgerRowData: LedgerRowData,
        invertCreditDebit: Boolean
    ) {
        if(!finishedInflating) {
            throw IllegalStateException("It seems the view hasn't been inflated!")
        }

        if (invertCreditDebit) {
            invertDebitCredit()
        }

        dateText.setText(ledgerRowData.date)
        referenceText.setText(ledgerRowData.reference)
        creditText.setText(ledgerRowData.credit.toPlainString())
        debitText.setText(ledgerRowData.debit.toPlainString())
        balanceText.setText(formatter.format(ledgerRowData.balance))
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        dateText = findViewById(R.id.textDate)
        referenceText = findViewById(R.id.textRef)
        creditText = findViewById(R.id.textCredit)
        debitText = findViewById(R.id.textDebit)

        balanceText = findViewById(R.id.textBalance)
    }

    fun invertDebitCredit() {
        findViewById<View>(R.id.textCredit).setId(0)
        findViewById<View>(R.id.textDebit).setId(R.id.textCredit)
        findViewById<View>(0).setId(R.id.textDebit)

        creditText = findViewById(R.id.textCredit)
        debitText = findViewById(R.id.textDebit)
    }
}
