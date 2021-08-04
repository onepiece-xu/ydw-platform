package com.ydw.edge.utils;

 public class Utility {
    public static byte[] int2bytes(int i){
        byte[] arr = new byte[4] ;
        arr[3] = (byte)i ;
        arr[2] = (byte)(i >> 8) ;
        arr[1] = (byte)(i >> 16) ;
        arr[0] = (byte)(i >> 24) ;
        return arr ;
    }

    public static int bytes2int(byte[] bytes){
        int i0 = 0xFF & bytes[3];
        int i1 = (bytes[2] & 0xFF) << 8 ;
        int i2 = (bytes[1] & 0xFF) << 16 ;
        int i3 = (bytes[0] & 0xFF) << 24 ;
        return i0 | i1 | i2 | i3 ;
    }

    public static byte[] short2bytes(short i) {
        byte[] arr = new byte[2] ;
        arr[1] = (byte)i ;
        arr[0] = (byte)(i >> 8) ;
        return arr ;
    }

    public static int bytes2short(byte[] bytes) {
        int i0 = 0xFF & bytes[1];
        int i1 = (bytes[0] & 0xFF) << 8 ;
        return i0 | i1;
    }

    public static String bytes2HexString(byte[] bytes, int size) {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<size; i++) {
            builder.append(String.format("%02X ", bytes[i]));
            if ((i+1) % 16 == 0) builder.append("\n");
        }
        builder.append("\n");
        return builder.toString();
    }

    public static String bytes2String(byte[] bytes, int size) {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<size; i++) {
            if(bytes[i] == '\0') break;
            builder.append(String.format("%c", bytes[i]));
        }
        return builder.toString();
    }
}
