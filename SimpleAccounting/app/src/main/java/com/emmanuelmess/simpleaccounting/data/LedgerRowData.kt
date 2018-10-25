package com.emmanuelmess.simpleaccounting.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class LedgerRowData(
	val date: String,
	val reference: String,
	val credit: BigDecimal?,
	val debit: BigDecimal?,
	val balance: BigDecimal
): Parcelable