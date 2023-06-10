package umn.ac.id.myapplication.ui.data

import com.google.gson.annotations.SerializedName

data class GetProfileApplicantResponse(
    @field: SerializedName("Username")
    val username: String,

    @field: SerializedName("Password")
    val password: String,

    @field: SerializedName("Name")
    val name: String? = null,

    @field: SerializedName("YearOfBirth")
    val yearOfBirth: String?= null,

    @field: SerializedName("Email")
    val email: String?= null,

    @field: SerializedName("Language")
    val language: String? = null,

    @field: SerializedName("Summary")
    val summary: String? = null,

    @field: SerializedName("EducationInstitution")
    val educationInstitution: String?= null,

    @field: SerializedName("Skills")
    val skills: String?= null,

    @field: SerializedName("SalaryMinimum")
    val salaryMinimum: Int? = null,

    @field: SerializedName("Location")
    val location: String?= null,

    @field: SerializedName("Degree")
    val degree: String?= null,

    @field: SerializedName("MobilePhone")
    val mobilePhone: String?= null,

    @field: SerializedName("OpenToWork")
    val openToWork: Boolean? = null,

    @field: SerializedName("PdfPath")
    val pdfPath: String?= null,

    @field: SerializedName("PpPath")
    val ppPath: String?= null,
)

