package com.atguigu.atcrowdfunding.util;

import java.security.MessageDigest;

/**
 * MD5�㷨 ��ϣ�㷨 MD5�㷨���������ص㣺 1��ѹ���ԣ����ⳤ�ȵ����ݣ������MD5ֵ���ȶ��ǹ̶��ġ� 2�����׼��㣺��ԭ���ݼ����MD5ֵ�����ס�
 * 3�����޸��ԣ���ԭ���ݽ����κθĶ�������ֻ�޸�1���ֽڣ����õ���MD5ֵ���кܴ�����
 * 4��ǿ����ײ����֪ԭ���ݺ���MD5ֵ�����ҵ�һ��������ͬMD5ֵ�����ݣ���α�����ݣ��Ƿǳ����ѵġ�
 */
public class MD5Util {
    public static String digest16(String inStr) {
        return digest(inStr, 16);
    }

    public static String digest(String inStr) {
        return digest(inStr, 32);
    }

    private static String digest(String inStr, int rang) {
        MessageDigest md5 = null;
        if (StringUtil.isEmpty(inStr)) {
            return "";
        }

        try {
            md5 = MessageDigest.getInstance("MD5"); // ȡ���㷨
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray); // ����

        StringBuilder hexValue = new StringBuilder();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        if (rang == 32) {
            return hexValue.toString();
        } else {
            return hexValue.toString().substring(8, 24);// ת��Ϊ32λ�ַ���
        }
    }

    public static void main(String args[]) {
        String s = new String("123");
        System.out.println(digest(s));
    }

}