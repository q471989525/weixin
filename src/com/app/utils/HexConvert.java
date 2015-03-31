package com.app.utils;

/**
 * 此类提供2进制与16进制之间互转换 Created by fuq on 14-8-12.
 */
public class HexConvert {

    /**
     * 将指定byte数组以16进制
     *
     * @param b byte Array
     * @return 16进制字符串
     */
    public static String hexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        // int length = b.length;
        for (int i = 0; i < b.length; i++) {
            String stamp = Integer.toHexString(b[i] & 0xff);
            if (stamp.length() == 1) {
                sb.append("0" + stamp);
            } else {
                sb.append(stamp);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将16进制字符串转成2进制
     *
     * @param nm 16进制字符串
     * @return byte Array
     */
    public static byte[] decodeHex(String nm) {
        int len = nm.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i++) {
            char c = nm.charAt(i);
            byte b = Byte.decode("0x" + c);
            c = nm.charAt(++i);
            result[i / 2] = (byte) (b << 4 | Byte.decode("0x" + c));
        }
        return result;
    }

    /**
     * 格式化16进制字符串
     *
     * @param str
     * @return
     */
    public static String HexStrFmt(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String c = str.substring(i, i + 1);
            sb.append(c);
            if (i % 2 != 0) {
                sb.append(" ");
            }

        }
        return sb.toString();
    }

/**
 * 拼接两个byte
 * @param byte_1
 * @param byte_2
 * @return 
 */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }
}
