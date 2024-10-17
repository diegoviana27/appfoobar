package br.com.foobar.adapter.repository.model;

import br.com.foobar.domain.ProcessControl;
import br.com.foobar.domain.ProcessControlError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "processing_control")
public class ProcessControlModel implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "InitialProcessDate cannot be null")
    private LocalDateTime initialProcessDate;

    @Column
    private LocalDateTime terminatedProcessDate;

    @Column
    private boolean processed;

    @Column
    @OneToMany(mappedBy = "processControlModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProcessControlErrorModel> errors;

    public ProcessControlModel(final Long id, final LocalDateTime initialProcessDate,
                               final LocalDateTime terminatedProcessDate, final Set<ProcessControlErrorModel> errors,
                               final boolean processed) {

        this.id = id;
        this.initialProcessDate = initialProcessDate;
        this.terminatedProcessDate = terminatedProcessDate;
        this.errors = errors;
        this.processed = processed;

    }

    public ProcessControlModel(final Long id) {
        this.id = id;
    }

    public static ProcessControlModel parseToModel(final ProcessControl proccessControl) {

        Set<ProcessControlErrorModel> errors = proccessControl.getErrors().stream()
                .map(error -> new ProcessControlErrorModel(
                        new ProcessControlModel(proccessControl.getId()), error.getDescription())).collect(
                        Collectors.toSet());

        return new ProcessControlModel(proccessControl.getId(), proccessControl.getInitialProcessDate(),
                proccessControl.getTerminatedProcessDate(), errors, proccessControl.isProcessed());
    }

    public static ProcessControl parseFromModel(
            final ProcessControlModel processControlModel) {

        Set<ProcessControlError> errors = processControlModel.getErrors().stream().map(
                error -> new ProcessControlError(error.getDescription())).collect(
                Collectors.toSet());

        return new ProcessControl(processControlModel.getId(),
                processControlModel.getInitialProcessDate(),
                processControlModel.getTerminatedProcessDate(),
                errors, processControlModel.isProcessed());

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ProcessControlModel that = (ProcessControlModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
