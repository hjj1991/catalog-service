package com.example.catalogservice.controller

import com.example.catalogservice.service.CatalogService
import com.example.catalogservice.vo.ResponseCatalog
import org.modelmapper.ModelMapper
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/catalog-service")
class CatalogController(
    private val env: Environment,
    private val catalogService: CatalogService,
) {

    @GetMapping("/health_check")
    fun status(): String {
        return "It's Working in Catalog Service on PORT ${env.getProperty("local.server.port")}"
    }

    @GetMapping("/catalogs")
    fun getCatalogs(): ResponseEntity<List<ResponseCatalog>> {
        val userList = catalogService.getAllCatalogs()

        val result = mutableListOf<ResponseCatalog>()

        userList.forEach { userEntity -> result.add(ModelMapper().map(userEntity, ResponseCatalog::class.java)) }
        return ResponseEntity.status(HttpStatus.OK).body(result)

    }


}