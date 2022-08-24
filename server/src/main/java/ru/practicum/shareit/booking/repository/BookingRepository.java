package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Поиск по id владельца бронирования
    Collection<Booking> findByBookerIdOrderByStartDesc(long userId);

    // Поиск текущих бронирований
    Collection<Booking> findByBookerIdAndStartBeforeAndEndAfter(long userId, LocalDateTime now, LocalDateTime now2, Sort sort);

    // Поиск завершенных бронирований
    Collection<Booking> findByBookerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now);

    // Поиск будущих бронирований
    Collection<Booking> findByBookerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime now);

    Collection<Booking> findByBookerIdAndStatusOrderByStartDesc(long userId, Status status);

    //   Поиск бронирований для всех вещей владельца
    Collection<Booking> findByItemIdInOrderByStartDesc(Collection<Long> itemsId);

    Collection<Booking> findByItemIdInAndStartBeforeAndEndAfter(Collection<Long> itemsId, LocalDateTime now, LocalDateTime now2, Sort sort);

    Collection<Booking> findByItemIdInAndEndBeforeOrderByStartDesc(Collection<Long> itemsId, LocalDateTime now);

    Collection<Booking> findByItemIdInAndStartAfterOrderByStartDesc(Collection<Long> itemsId, LocalDateTime now);

    Collection<Booking> findByItemIdInAndStatusOrderByStartDesc(Collection<Long> itemsId, Status status);

    Collection<Booking> findByItemIdAndEndBeforeOrderByStartDesc(long itemId, LocalDateTime now);

    Collection<Booking> findByItemIdAndStartAfterOrderByStartDesc(long itemId, LocalDateTime now);

    Collection<Booking> findByBookerIdAndItemIdAndEndBefore(long bookerId, long itemId, LocalDateTime now, Sort sort);
}
