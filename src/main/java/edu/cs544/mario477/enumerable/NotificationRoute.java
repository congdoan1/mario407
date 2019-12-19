package edu.cs544.mario477.enumerable;

import lombok.Getter;

@Getter
public enum NotificationRoute {
    NEW_POST("NEW.POST"),
    CHECK_UNHEALTHY_POST("CHECK.UNHEALTHY.POST"),
    OTHER("OTHER");

    private String value;

    NotificationRoute(String value) {
        this.value = value;
    }
}
