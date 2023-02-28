package mappers;

import dto.entities.RankingsUpdateDto;
import dto.entities.ReservationDto;
import dto.entities.ReviewDto;
import models.Rank;
import models.Reservation;
import models.Reviews;

public class EntityMapper {

    public static RankingsUpdateDto toRankings(Integer silver, Integer gold){
        RankingsUpdateDto dto = new RankingsUpdateDto();
        dto.setSilverPoints(silver);
        dto.setGoldPoints(gold);
        return dto;
    }

    public static Rank fromScratch(String rank, Double reduction){
        Rank newRank = new Rank();
        newRank.setRank(rank);
        newRank.setReduction(reduction);
        return newRank;
    }

    public static Reservation fromReservationDto(Long userId, String hotelName, ReservationDto dto){

        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setHotelId(dto.getHotelId());
        reservation.setFrom(dto.getFrom());
        reservation.setUntil(dto.getUntil());
        reservation.setHotelName(hotelName);
        reservation.setRoom(dto.getRoom());
        return reservation;
    }

    public static Reviews fromReviewDto(Long userId, String username, ReviewDto reviewDto){
        Reviews review = new Reviews();
        review.setUsername(username);
        review.setStars(reviewDto.getStars());
        review.setComment(reviewDto.getComment());
        review.setUserId(userId);
        return review;
    }
}
