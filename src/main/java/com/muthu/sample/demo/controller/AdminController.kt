package com.muthu.sample.demo.controller

import com.muthu.sample.demo.jwt.JwtTokenUtil
import com.muthu.sample.demo.controller.validator.isValidForAdminRegister
import com.muthu.sample.demo.controller.validator.isValidForEditAdmin
import com.muthu.sample.demo.controller.validator.isValidForLogin
import com.muthu.sample.demo.data.model.Admin
import com.muthu.sample.demo.data.model.JwtAuthenticationResponse
import com.muthu.sample.demo.data.model.UserWithKey
import com.muthu.sample.demo.util.getFailureResponse
import com.muthu.sample.demo.util.getSuccessResponse
import com.muthu.sample.demo.service.org.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController {

    @Autowired
    lateinit var adminService: AdminService

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @PostMapping("/signup")
    fun addAdmin(@RequestBody admin: Admin): ResponseEntity<*> {

        val isValid = isValidForAdminRegister(admin)

        if (!isValid.isNullOrBlank()) {
            return getFailureResponse(isValid)
        }

        val status = adminService.addAdmin(admin)

        when (status) {
            -1 -> {
                return getFailureResponse("User Already exists")
            }
            0 -> {
                // This should never happen
                return getFailureResponse("Not able to add Admin")
            }
        }
        return getUserWithAuthCredentials(admin.userName)
    }

    @PostMapping("/edit")
    fun editAdmin(@RequestBody admin: Admin): ResponseEntity<*> {
        val isValid = isValidForEditAdmin(admin)

        if (!isValid.isNullOrBlank()) {
            return getFailureResponse(isValid)
        }

        val status = adminService.editAdmin(admin)

        when (status) {
            -1 -> {
                return getFailureResponse("User not exists")
            }
            0 -> {
                // This should never happen
                return getFailureResponse("Not able to Edit Admin")
            }
        }

        return getSuccessResponse(adminService.getAdmin(admin.userName))
    }

    @GetMapping("/getAll")
    fun getAllAdmin(): ResponseEntity<*> {
        return getSuccessResponse(adminService.getAllAdmin())
    }

    @PostMapping("/signin")
    fun login(@RequestBody admin: Admin): ResponseEntity<*> {

        val isValid = isValidForLogin(admin)

        if (!isValid.isNullOrBlank()) {
            return getFailureResponse(isValid)
        }

        val status = adminService.doLogin(admin)

        when (status) {
            -1 -> {
                return getFailureResponse("Username Not exists")
            }
            -2 -> {
                // This should never happen
                return getFailureResponse("Password not matching the username")
            }
        }

        return getUserWithAuthCredentials(admin.userName)
    }

    /**
     * Should call only this method when user successfully SignUp/SignIn
     */
    fun getUserWithAuthCredentials(userName: String): ResponseEntity<*> {

        val localAdmin = adminService.getAdmin(userName)!!

        return getSuccessResponse(UserWithKey(localAdmin, JwtAuthenticationResponse(jwtTokenUtil.generateToken(localAdmin.userName, 2))))
    }
}
