package com.muthu.sample.demo.util

import com.muthu.sample.demo.data.model.Response
import org.springframework.http.ResponseEntity

fun getSuccessResponse(value: Any?) = ResponseEntity.ok().body(Response("Success", true, value))

fun getFailureResponse(value: Any?) = ResponseEntity.ok().body(Response("Failure", false, value))