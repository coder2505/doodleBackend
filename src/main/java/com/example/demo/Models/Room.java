package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID room_id;

    String room_name;
}
