package com.example.serviceboundmusic.model;

public class Song {
    int id;
    String ten;

    public Song(int id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                '}';
    }
}
