package com.example.core.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    fun formatLastSeen(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("id", "ID"))
        return formatter.format(Date(timestamp))
    }
}
