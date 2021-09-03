package tech.brtrndb.airproject.flightplan.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LocalizableModel extends Model implements Localizable {

    @Getter
    @Setter
    @NonNull
    private Position position = ORIGIN;

    protected LocalizableModel(double x, double y) {
        this(Position.of(x, y));
    }

    protected LocalizableModel(Position position) {
        super();
        this.setPosition(position);
    }

    protected LocalizableModel(UUID id, CreationInfo creationInfo, Position position) {
        super(id, creationInfo);
        this.setPosition(position);
    }

    public void setPosX(double x) {
        this.position.x(x);
    }

    public void setPosY(double y) {
        this.position.y(y);
    }

}
