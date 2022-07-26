package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Поиск по id владельца бронирования
    Collection<Booking> findBookingByBookerIdOrderByStartDesc(long userId);

    // Поиск текущих бронирований
    Collection<Booking> findBookingByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(long userId, LocalDateTime now, LocalDateTime now2);

    // Поиск завершенных бронирований
    Collection<Booking> findBookingByBookerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now);

    // Поиск будущих бронирований
    Collection<Booking> findBookingByBookerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime now);

    Collection<Booking> findBookingByBookerIdAndStatusOrderByStartDesc(long userId, Status status);

    //   Поиск бронирований для всех вещей владельца
    Collection<Booking> findBookingByItemIdInOrderByStartDesc(Collection<Long> itemsId);

    Collection<Booking> findBookingByItemIdInAndStartBeforeAndEndAfterOrderByStartDesc(Collection<Long> itemsId, LocalDateTime now, LocalDateTime now2);

    Collection<Booking> findBookingByItemIdInAndEndBeforeOrderByStartDesc(Collection<Long> itemsId, LocalDateTime now);

    Collection<Booking> findBookingByItemIdInAndStartAfterOrderByStartDesc(Collection<Long> itemsId, LocalDateTime now);

    Collection<Booking> findBookingByItemIdInAndStatusOrderByStartDesc(Collection<Long> itemsId, Status status);

    Collection<Booking> findBookingByItemIdAndEndBeforeOrderByStartDesc(long itemId, LocalDateTime now);

    Collection<Booking> findBookingByItemIdAndStartAfterOrderByStartDesc(long itemId, LocalDateTime now);

    Collection<Booking> findBookingByBookerIdAndItemIdAndEndBeforeOrderByStartDesc(long bookerId, long itemId, LocalDateTime now);
}
