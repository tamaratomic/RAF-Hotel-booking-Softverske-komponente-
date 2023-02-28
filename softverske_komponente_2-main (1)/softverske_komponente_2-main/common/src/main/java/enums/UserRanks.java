package enums;

import mappers.EntityMapper;
import models.Rank;

@SuppressWarnings("all")
public class UserRanks {

    public static final String GOLD = "GOLD";
    public static final String SILVER = "SILVER";
    public static final String BRONZE = "BRONZE";

    public static final Double GOLD_REDUCTION = 0.25;
    public static final Double SILVER_REDUCTION = 0.15;
    public static final Double BRONZE_REDUCTION = 0.0;

    public static int GOLDNUMBER = 20;
    public static int SILVERNUMBER = 10;

    public static final String MAKE_RESERVATION = "MAKE";
    public static final String CANCEL_RESERVATION = "CANCEL";

    public static final Rank defaultRank = EntityMapper.fromScratch(BRONZE, 0.0);

    public static Rank determineRank(int numberOfReservations){
        if(numberOfReservations < SILVERNUMBER)
            return EntityMapper.fromScratch(BRONZE, BRONZE_REDUCTION);
        if(numberOfReservations < GOLDNUMBER)
            return EntityMapper.fromScratch(SILVER, SILVER_REDUCTION);
        return EntityMapper.fromScratch(GOLD, GOLD_REDUCTION);
    }

    public static void setGOLDNUMBER(int GOLDNUMBER) {
        UserRanks.GOLDNUMBER = GOLDNUMBER;
    }

    public static void setSILVERNUMBER(int SILVERNUMBER) {
        UserRanks.SILVERNUMBER = SILVERNUMBER;
    }
}
