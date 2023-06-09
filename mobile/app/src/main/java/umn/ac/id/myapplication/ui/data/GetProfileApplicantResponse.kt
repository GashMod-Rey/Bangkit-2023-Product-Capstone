package umn.ac.id.myapplication.ui.data

import com.google.gson.annotations.SerializedName

data class GetProfileApplicantResponse(
    @field: SerializedName("Username")
    val Username: String,

    @field: SerializedName("Password")
    val Password: String,

    @field: SerializedName("Name")
    val Name: String? = null,

    @field: SerializedName("YearOfBirth")
    val YearOfBirth: String?= null,

    @field: SerializedName("Email")
    val Email: String?= null,

    @field: SerializedName("Language")
    val Language: String? = null,

    @field: SerializedName("Summary")
    val Summary: String? = null,

    @field: SerializedName("EducationInstitution")
    val EducationInstitution: String?= null,

    @field: SerializedName("Skills")
    val Skills: String?= null,

    @field: SerializedName("SalaryMinimum")
    val SalaryMinimum: Int? = null,

    @field: SerializedName("Location")
    val Location: String?= null,

    @field: SerializedName("Degree")
    val Degree: String?= null,

    @field: SerializedName("MobilePhone")
    val MobilePhone: String?= null,

    @field: SerializedName("OpenToWork")
    val OpenToWork: Boolean? = null,

    @field: SerializedName("PdfPath")
    val PdfPath: String?= null,

    @field: SerializedName("PpPath")
    val PpPath: String?= null,
)

