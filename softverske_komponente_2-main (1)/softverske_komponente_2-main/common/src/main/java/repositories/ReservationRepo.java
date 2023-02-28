package repositories;

import models.HotelRoom;
import models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findByHotelIdAndUserId(Long hotelId, Long userId);
    Optional<List<Reservation>> findByUserId(Long userId);
    Optional<Reservation> findByIdAndUserId(Long id, Long userId);
    List<Reservation> findByHotelIdAndRoom(Long hotelId, HotelRoom room);
}
