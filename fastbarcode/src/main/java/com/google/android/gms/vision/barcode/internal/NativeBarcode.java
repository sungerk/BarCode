package com.google.android.gms.vision.barcode.internal;

import android.graphics.Point;
import android.support.annotation.Keep;

@Keep
public final class NativeBarcode {
    public CalendarEvent calendarEvent;
    public ContactInfo contactInfo;
    public Point[] cornerPoints;
    public String displayValue;
    public DriverLicense driverLicense;
    public Email email;
    public int format;
    public GeoPoint geoPoint;
    public Phone phone;
    public String rawValue;
    public Sms sms;
    public UrlBookmark url;
    public int valueFormat;
    public WiFi wifi;
    @Keep
    public static final class Address {
        public String[] addressLines;
        public int type;

    }
    @Keep
    public static final class CalendarDateTime {
        public int day;
        public int hours;
        public boolean isUtc;
        public int minutes;
        public int month;
        public String rawValue;
        public int seconds;
        public int year;

    }
    @Keep
    public static final class CalendarEvent {
        public String description;
        public CalendarDateTime end;
        public String location;
        public String organizer;
        public CalendarDateTime start;
        public String status;
        public String summary;
    }
    @Keep
    public static final class ContactInfo {
        public Address[] addresses;
        public Email[] emails;
        public PersonName name;
        public String organization;
        public Phone[] phones;
        public String title;
        public String[] urls;
    }
    @Keep
    public static final class DriverLicense {
        public String addressCity;
        public String addressState;
        public String addressStreet;
        public String addressZip;
        public String birthDate;
        public String documentType;
        public String expiryDate;
        public String firstName;
        public String gender;
        public String issueDate;
        public String issuingCountry;
        public String lastName;
        public String licenseNumber;
        public String middleName;
    }
    @Keep
    public static final class Email {
        public String address;
        public String body;
        public String subject;
        public int type;
    }
    @Keep
    public static final class GeoPoint {
        public double lat;
        public double lng;
    }
    @Keep
    public static final class PersonName {
        public String first;
        public String formattedName;
        public String last;
        public String middle;
        public String prefix;
        public String pronunciation;
        public String suffix;
    }
    @Keep
    public static final class Phone {
        public String number;
        public int type;

    }
    @Keep
    public static final class Sms {
        public String message;
        public String phoneNumber;
    }
    @Keep
    public static final class UrlBookmark {
        public String title;
        public String url;
    }
    @Keep
    public static final class WiFi {
        public int encryptionType;
        public String password;
        public String ssid;
    }
}
