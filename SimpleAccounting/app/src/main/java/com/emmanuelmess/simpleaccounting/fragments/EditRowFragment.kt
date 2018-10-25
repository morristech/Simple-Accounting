package com.emmanuelmess.simpleaccounting.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.emmanuelmess.simpleaccounting.R
import com.emmanuelmess.simpleaccounting.data.LedgerRowData

class EditRowFragment : Fragment() {
	companion object {
		private const val LEDGER_ROW_DATA_KEY = "LedgerRowData"

		@JvmStatic
		fun newInstance(ledgerRowData: LedgerRowData) =
			EditRowFragment().apply {
				arguments = Bundle().apply {
					putParcelable(LEDGER_ROW_DATA_KEY, ledgerRowData)
				}
			}
	}

	private lateinit var ledgerRowData: LedgerRowData

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			ledgerRowData = it.getParcelable(LEDGER_ROW_DATA_KEY) as LedgerRowData
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_edit_row, container, false)
	}
}
