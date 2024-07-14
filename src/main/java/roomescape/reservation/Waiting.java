package roomescape.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Waiting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Reservation reservation;

    private int rank;

    public Waiting(Long id, Reservation reservation, int rank) {
        this.id = id;
        this.reservation = reservation;
        this.rank = rank;
    }

    public Waiting(Reservation reservation, int rank) {
        this(null, reservation, rank);
    }

    protected Waiting() {
    }

    public Long getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public int getRank() {
        return rank;
    }
}
