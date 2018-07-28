import java.text.SimpleDateFormat;

public class Ticket {
    private long arrivalTime;
    private long departureTime;
    private String from;
    private String to;
    private int routeNum;
    private String passengerName;
    private long sellDate;

    public static final String DATE_FORMAT = "HH:mm dd MM yyyy";

    private Ticket() {
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat(Ticket.DATE_FORMAT);
        String arrival = sdf.format(arrivalTime);
        String departure = sdf.format(departureTime);
        String sell = sdf.format(sellDate);
        return "Ticket{" +
                "arrivalTime=" + arrival +
                ", departureTime=" + departure +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", routeNum=" + routeNum +
                ", passengerName='" + passengerName + '\'' +
                ", sellDate=" + sell +
                '}';
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getRouteNum() {
        return routeNum;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public long getSellDate() {
        return sellDate;
    }

    public static class Builder {
        private long arrivalTime;
        private long departureTime;
        private String from;
        private String to;
        private int routeNum;
        private String passengerName;
        private long sellDate;

        public Builder() {
        }

        public Ticket ticket() {
            Ticket t = new Ticket();
            t.arrivalTime = arrivalTime;
            t.departureTime = departureTime;
            t.from = from;
            t.to = to;
            t.routeNum = routeNum;
            t.passengerName = passengerName;
            t.sellDate = sellDate;
            return t;
        }

        public Builder setArrivalTime(long arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public Builder setDepartureTime(long departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        public Builder setRouteNum(int routeNum) {
            this.routeNum = routeNum;
            return this;
        }

        public Builder setPassengerName(String passengerName) {
            this.passengerName = passengerName;
            return this;
        }

        public Builder setSellDate(long sellDate) {
            this.sellDate = sellDate;
            return this;
        }
    }
}
