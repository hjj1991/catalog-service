package com.example.catalogservice.jpa

import org.hibernate.annotations.ColumnDefault
import java.io.Serializable
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "catalog")
class CatalogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false, length = 120, unique = true)
    var productId: String,
    @Column(nullable = false)
    var productName: String,
    @Column(nullable = false)
    var stock: Int,
    @Column(nullable = false)
    var unitPrice: Int,

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    var createdAt: Date
): Serializable {
}