package com.example.catalogservice.messagequeue

import com.example.catalogservice.jpa.CatalogRepository
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(
    private val repository: CatalogRepository,
) {
    private val log = LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(topics = ["example-catalog-topic"])
    fun updateQty(kafkaMessage: String) {
        log.info("Kafka Message: -> ${kafkaMessage}")

        var map = HashMap<Any, Any>()
        val mapper = ObjectMapper()
        kotlin.runCatching {
            map = mapper.readValue(kafkaMessage, object: TypeReference<HashMap<Any, Any>>(){})
        }.onFailure {
            when(it){
                is JsonProcessingException -> it.printStackTrace()
            }
        }

        val entity = repository.findByProductId(map["productId"] as String)
        entity?.stock = entity?.stock?.minus(map["qty"] as Int)!!
        repository.save(entity)
    }
}