package com.example.nofkdemo

import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class MyOrder(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column
    var price: Double,
    @Column
    var note: String,
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    var options: Set<OrderOption> = emptySet(),
    @Column
    var createTime: Instant = Instant.now()
)

interface OrderRepository : JpaRepository<MyOrder, Long>