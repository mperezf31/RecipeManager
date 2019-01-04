package models;

import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.UpdatedTimestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;

@MappedSuperclass
public class ModelBase extends Model {

    @Id
    private Long id;

    @Version
    private Long version;

    @CreatedTimestamp
    private Timestamp whenCreated;

    @UpdatedTimestamp
    private Timestamp whenUpdated;

    // getters & setters
    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Timestamp getWhenCreated() {
        return whenCreated;
    }

    public Timestamp getWhenUpdated() {
        return whenUpdated;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setWhenCreated(Timestamp whenCreated) {
        this.whenCreated = whenCreated;
    }

    public void setWhenUpdated(Timestamp whenUpdated) {
        this.whenUpdated = whenUpdated;
    }

}

