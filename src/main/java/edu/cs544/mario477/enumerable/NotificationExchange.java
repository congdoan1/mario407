package edu.cs544.mario477.enumerable;

import lombok.Getter;

@Getter
public enum NotificationExchange {
    POST("POST"),
    OTHER("OTHER");

    private String value;

    NotificationExchange(String value) {
        this.value = value;
    }
}
