package com.baytech.wikramahotel.Model;

public class Booking {

    String total_harga,harga_extra_bed,harga_room,date_booking,time_booking,status;

    public Booking() {
    }

    public Booking(String total,String harga_extra_bed, String harga_room,String date_booking,String time_booking,String status) {
        this.total_harga = total;
        this.harga_extra_bed = harga_extra_bed;
        this.harga_room = harga_room;
        this.date_booking = date_booking;
        this.time_booking = time_booking;
        this.status = status;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public String getHarga_extra_bed(){
        return harga_extra_bed;
    }

    public String getHarga_room(){
        return harga_room;
    }

    public String getDate_booking(){
        return date_booking;
    }

    public String getTime_booking(){
        return time_booking;
    }

    public String getStatus(){
        return status;
    }




}
