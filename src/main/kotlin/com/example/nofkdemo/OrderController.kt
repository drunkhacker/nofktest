package com.example.nofkdemo

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderRepository: OrderRepository,
    private val orderOptionRepository: OrderOptionRepository,
) {
    data class OrderCreateDto(
        val price: Double,
        val note: String,
        val options: List<OrderOptionDto>
    ) {
        data class OrderOptionDto(
            val type: String,
            val price: Double,
        )
    }

    @PostMapping
    fun create(@RequestBody dto: OrderCreateDto): MyOrder {
        val x = MyOrder(
            price = dto.price,
            note = dto.note
        )

        val order =  orderRepository.save(x)
        val options = dto.options.map {
            OrderOption(
                price = it.price,
                type = it.type,
                order = order
            )
        }
        val savedOptions = orderOptionRepository.saveAll(options)
        order.price = savedOptions.fold(0.0) { acc, option -> acc + option.price }
        order.options = savedOptions.toSet()
        return orderRepository.save(order)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): MyOrder? {
        return orderRepository.findByIdOrNull(id)
    }
}