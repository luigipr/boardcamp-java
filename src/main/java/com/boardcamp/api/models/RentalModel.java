package com.boardcamp.api.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                     // Getters e setters
@NoArgsConstructor        // Construtor sem argumentos
@AllArgsConstructor       // Construtor com todos os argumentos
@Entity                   // Representa que é uma entidade a ser mapeada no BD
@Table(name = "rentals")  // Nome da tabela que representa esses dados
public class RentalModel {
    
    public RentalModel(LocalDate rentDate, int originalPrice, int delayFee, int daysRented, CustomerModel customer, GameModel game){  
        this.customer = customer;
        this.game = game;
        this.rentDate = rentDate;
        this.daysRented = daysRented;
        this.originalPrice = originalPrice;
        this.delayFee = delayFee;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDate rentDate;

    @Column(nullable = false)
    private int daysRented;

    @Column
    private LocalDate returnDate;

    @Column(nullable = false)
    private int originalPrice;

    @Column
    private int delayFee;

    @ManyToOne 
    @JoinColumn(name = "customerId")
    private CustomerModel customer;

    @ManyToOne 
    @JoinColumn(name = "gameId")
    private GameModel game;



}
