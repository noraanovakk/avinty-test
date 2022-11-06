package com.avinty.hr.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @JoinColumn(name = "manager_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Employee managerId;

    private LocalDateTime createdAt;

    @JoinColumn(name = "created_by", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee createdBy;

    private LocalDateTime modifiedAt;

    @JoinColumn(name = "modified_by", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee modifiedBy;
}
