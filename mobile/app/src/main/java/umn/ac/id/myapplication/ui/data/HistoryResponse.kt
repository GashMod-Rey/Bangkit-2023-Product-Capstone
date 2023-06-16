package umn.ac.id.myapplication.ui.data

data class HistoryDataResponse(
    val UsernameA: String,
    val UsernameC: String,
    val Offer: Int,
    val Status: Int
)

class HistoryResponse: ArrayList<HistoryDataResponse>()
