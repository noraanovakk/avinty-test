package com.avinty.hr.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"employees"})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @JoinColumn(name = "manager_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Employee manager;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Employee> employees;

    private LocalDateTime createdAt;

    @JoinColumn(name = "created_by", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee createdBy;

    private LocalDateTime modifiedAt;

    @JoinColumn(name = "modified_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee modifiedBy;
}
