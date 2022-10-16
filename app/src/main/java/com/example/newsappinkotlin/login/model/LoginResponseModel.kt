package com.example.newsappinkotlin.login.model

data class LoginResponseModel(
    val `data`: LoginDataModel? = null,
    val message: String? = null,
    val status: Int? = null,
    val success: Boolean? = null
)

data class LoginDataModel(
    val civil_ID: String? = null,
    val current_designation: String? = null,
    val email: String? = null,
    val fullname: String? = null,
    val id: String? = null,
    val ipin: String? = null,
    val maritial_Status: String? = null,
    val nationality: String? = null,
    val no_of_kids: String? = null,
    val phone: String? = null,
    val profile_picture: String? = null,
    val qr: String? = null,
    val qualification_degree: String? = null,
    val salary: String? = null,
    val username: String? = null,
    val company_name: String? = null,
    val description: String? = null,
    val working_Location: String? = null
)