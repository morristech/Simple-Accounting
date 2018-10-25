package com.emmanuelmess.simpleaccounting.activities.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import com.emmanuelmess.simpleaccounting.R
import com.emmanuelmess.simpleaccounting.data.LedgerRowData
import java.lang.IllegalStateException

class LedgerEditableRow @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null
) : TableRow(context, attrs) {

	lateinit var formatter: LedgerView.BalanceFormatter

	private lateinit var dateEditText: EditText
	private lateinit var referenceEditText: EditText
	private lateinit var creditEditText: EditText
	private lateinit var debitEditText: EditText

	private lateinit var balanceText: TextView

	private var finishedInflating = false
	private var afterInflation: (() -> Unit)? = null

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

		dateEditText.setText(ledgerRowData.date)
		referenceEditText.setText(ledgerRowData.reference)
		creditEditText.setText(ledgerRowData.credit?.toPlainString() ?: "")
		debitEditText.setText(ledgerRowData.debit?.toPlainString() ?: "")
		balanceText.setText(formatter.format(ledgerRowData.balance))
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		finishedInflating = true

		dateEditText = findViewById(R.id.editDate)
		referenceEditText = findViewById(R.id.editRef)
		creditEditText = findViewById(R.id.editCredit)
		debitEditText = findViewById(R.id.editDebit)

		balanceText = findViewById(R.id.textBalance)

		afterInflation?.invoke()
	}

	private fun invertDebitCredit() {

		findViewById<View>(R.id.editCredit).setId(0)
		findViewById<View>(R.id.editDebit).setId(R.id.editCredit)
		findViewById<View>(0).setId(R.id.editDebit)

		findViewById<EditText>(R.id.editCredit).setHint(R.string.credit)
		findViewById<EditText>(R.id.editDebit).setHint(R.string.debit)

		creditEditText = findViewById(R.id.editCredit)
		debitEditText = findViewById(R.id.editDebit)
	}
}
