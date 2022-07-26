package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.SelfBookingException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final ItemService itemService;

    public BookingServiceImpl(UserService userService, BookingRepository bookingRepository, ItemService itemService) {
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.itemService = itemService;
    }

    @Override
    public Booking addBooking(long userId, Booking booking) {
        if (booking.getStart().isAfter(booking.getEnd())
                || booking.getStart().isBefore(LocalDateTime.now())) {
            throw new IncorrectParameterException("start");
        }
        booking.setBooker(userService.getUserById(userId));
        Item item = itemService.getItemById(booking.getItem().getId());
        booking.setItem(item);
        if (!booking.getItem().getAvailable()) {
            throw new IncorrectParameterException("item");
        }
        booking.setStatus(Status.WAITING);
        if (itemService.isUserEqualsOwnerItem(userId, booking.getItem().getId())) {
            throw new SelfBookingException("userId!!!");
        }
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public Booking approveBooking(long ownerId, long bookingId, boolean approved) {
        Booking booking = getBookingById(bookingId);
        if (itemService.isUserEqualsOwnerItem(ownerId, booking.getItem().getId())) {
            if (approved) {
                if (!booking.getStatus().equals(Status.APPROVED)) {
                    booking.setStatus(Status.APPROVED);
                } else throw new IncorrectParameterException("status. Бронирование уже подтверждено");
            } else {
                booking.setStatus(Status.REJECTED);
            }
        } else
            throw new ItemNotFoundException(String.format("Вещь № %d не принадлежит пользователю № %d",
                    booking.getItem().getId(),
                    ownerId));
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public Booking getBookingById(long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(String.format("Бронирование № %d не найдено", bookingId)));
    }

    @Override
    public Booking requestBookingByIdByOwnerBookingOrItem(long bookingId, long userId) {
        Booking booking = getBookingById(bookingId);
        if (!itemService.isUserEqualsOwnerItem(userId, booking.getItem().getId())
                && !isUserEqualsOwnerBooking(userId, bookingId))
            throw new BookingNotFoundException(String.format("Бронирование № %d не найдено", bookingId));
        return booking;
    }

    @Override
    public Collection<Booking> getUserBookings(long userId, String state) {
        switch (state) {
            case "ALL":
                return bookingRepository.findBookingByBookerIdOrderByStartDesc(userId);
            case "CURRENT":
                LocalDateTime now = LocalDateTime.now();
                return bookingRepository.findBookingByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now);
            case "PAST":
                return bookingRepository.findBookingByBookerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
            case "FUTURE":
                return bookingRepository.findBookingByBookerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
            case "WAITING":
                return bookingRepository.findBookingByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
            case "REJECTED":
                return bookingRepository.findBookingByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            default:
                throw new IncorrectParameterException("state");
        }

    }

    @Override
    public Collection<Booking> getItemBookingsForOwner(long userId, String state) {
        Collection<Long> itemsId = itemService.getAllItemByUserId(userId)
                .stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        switch (state) {
            case "ALL":
                return bookingRepository.findBookingByItemIdInOrderByStartDesc(itemsId);
            case "CURRENT":
                LocalDateTime now = LocalDateTime.now();
                return bookingRepository.findBookingByItemIdInAndStartBeforeAndEndAfterOrderByStartDesc(itemsId, now, now);
            case "PAST":
                return bookingRepository.findBookingByItemIdInAndEndBeforeOrderByStartDesc(itemsId, LocalDateTime.now());
            case "FUTURE":
                return bookingRepository.findBookingByItemIdInAndStartAfterOrderByStartDesc(itemsId, LocalDateTime.now());
            case "WAITING":
                return bookingRepository.findBookingByItemIdInAndStatusOrderByStartDesc(itemsId, Status.WAITING);
            case "REJECTED":
                return bookingRepository.findBookingByItemIdInAndStatusOrderByStartDesc(itemsId, Status.REJECTED);
            default:
                throw new IncorrectParameterException("state");
        }
    }

    @Override
    public boolean isUserEqualsOwnerBooking(long userId, long bookingId) {
        return getBookingById(bookingId).getBooker().getId() == userId;
    }

    @Override
    public Booking getLastItemBooking(long itemId, LocalDateTime now) {
        return bookingRepository.findBookingByItemIdAndEndBeforeOrderByStartDesc(itemId, now)
                .stream()
                .max(Comparator.comparing(Booking::getEnd))
                .orElse(null);
    }

    @Override
    public Booking getNextItemBooking(long itemId, LocalDateTime now) {
        return bookingRepository.findBookingByItemIdAndStartAfterOrderByStartDesc(itemId, now)
                .stream()
                .min(Comparator.comparing(Booking::getStart))
                .orElse(null);
    }
}
