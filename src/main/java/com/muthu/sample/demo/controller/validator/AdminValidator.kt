package com.muthu.sample.demo.controller.validator

import com.muthu.sample.demo.data.model.Admin


fun isValidForAdminRegister(admin: Admin): String? {
    if (admin.firstName.isNullOrBlank()) {
        return "First name should not be empty"
    }

    return isValidForLogin(admin)
}

fun isValidForLogin(admin: Admin): String? {

    if (admin.userName.isNullOrBlank()) {
        return "Username should not be empty"
    }

    if (admin.password.isNullOrBlank()) {
        return "Password should not be empty"
    }

    return null
}

fun isValidForEditAdmin(admin: Admin): String? {
    if (admin.firstName.isNullOrBlank()) {
        return "First name should not be empty"
    }

    if (admin.userName.isNullOrBlank()) {
        return "Username should not be empty"
    }

    return null
}