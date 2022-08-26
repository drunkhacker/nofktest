package com.example.nofkdemo

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class OrderOption(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    var price: Double,
    @Column
    var type: String,
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    val order: MyOrder?
)

interface OrderOptionRepository : JpaRepository<OrderOption, Long>