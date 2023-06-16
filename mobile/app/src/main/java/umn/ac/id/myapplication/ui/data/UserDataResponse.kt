package umn.ac.id.myapplication.ui.data

data class UserDataResponse(
    val Username: String,
    val Password: String,
    val Name: String?,
    val YearOfBirth: String?,
    val Email: String?,
    val Language: String?,
    val Summary: String?,
    val EducationInstitution: String?,
    val Skills: String?,
    val SalaryMinimum: String?,
    val Location: String?,
    val Degree: String?,
    val MobilePhone: String?,
    val OpenToWork: Int?,
    val PdfPath: String?,
    val PpPath: String?,
    val Score: List<Float>
)

//data class FilterResponse(
//    val filteredUsers: List<UserDataResponse>,
//    val totalCount: Int
//)

data class FilterOptions(
    val ageFilter: String,
    val salaryFilter: String,
    val skillFilter: String,
    val langFilter: String,
    val tolerance: String,
    val tol: String,
    val location: String
)

class FilterResponse: ArrayList<UserDataResponse>()
