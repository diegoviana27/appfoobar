package br.com.foobar.adapter.repository.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "processing_control_errors")
public class ProcessControlErrorModel implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 2, max = 250, message = "Description must be between 2 and 250 characters")
    @NotNull(message = "Description cannot be null")
    private String description;

    @ManyToOne
    @JoinColumn(name = "process_control_id")
    @NotNull(message = "ProcessControlModel cannot be null")
    private ProcessControlModel processControlModel;

    public ProcessControlErrorModel(final ProcessControlModel processControlModel,
                                    final String description) {
        this.processControlModel = processControlModel;
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ProcessControlErrorModel that = (ProcessControlErrorModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}