package com.avinty.hr.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @JoinColumn(name = "manager_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Employee manager;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private List<Employee> employees;

    private LocalDateTime createdAt;

    @JoinColumn(name = "created_by", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee createdBy;

    private LocalDateTime modifiedAt;

    @JoinColumn(name = "modified_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee modifiedBy;
}
