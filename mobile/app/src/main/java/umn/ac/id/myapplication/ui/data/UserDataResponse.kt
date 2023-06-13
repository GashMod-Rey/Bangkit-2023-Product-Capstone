package umn.ac.id.myapplication.ui.data

data class UserDataResponse(
    val username: String,
    val password: String,
    val name: String?,
    val yearOfBirth: String?,
    val email: String?,
    val language: String?,
    val summary: String?,
    val educationInstitution: String?,
    val skills: String?,
    val salaryMinimum: String?,
    val location: String?,
    val degree: String?,
    val mobilePhone: String?,
    val openToWork: Int?,
    val pdfPath: String?,
    val ppPath: String?,
    val score: List<Float>
)

data class FilterResponse(
    val filteredUsers: List<UserDataResponse>,
    val totalCount: Int
)
