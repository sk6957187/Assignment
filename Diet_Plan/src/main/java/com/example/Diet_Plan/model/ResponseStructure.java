package com.example.Diet_Plan.model;

import lombok.Data;

@Data
public class ResponseStructure<T> {
    private int statusCode;
    private String msg;
    private T data;

}

//public class ResponseStructure<T> {
//    private int statusCode;
//    private String msg;
//    private T data;