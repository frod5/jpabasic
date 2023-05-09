package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") // ITEM1에 DTYPE에 값을 Album(default)가 아닌 "A"로 하고싶을 떄 사용.
public class Album1 extends Item1 {
    private String artist;
}
