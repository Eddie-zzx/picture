package com.graduation.picture.utils;

/**
 * @Classname HexColorUtil
 * @Date 2025/12/17 17:42
 * @Author by Eddie
 */
public class HexColorUtil {
    public static String expandHexColor(String compressed) {
        // 去除可能存在的0x前缀
        String input = compressed.startsWith("0x") ? compressed.substring(2) : compressed;
        int length = input.length();
        // 长度为3直接返回
        if (length == 3) {
            return "0x000000";
        }
        int index = 0;
        StringBuilder expanded = new StringBuilder();

        // 处理三个颜色分量
        for (int i = 0; i < 3; i++) {
            char current = input.charAt(index);
            if (current == '0') {
                // 当前分量是00的情况
                expanded.append("00");
                index++;
            } else {
                // 正常分量处理（可能包含补零）
                if (index + 1 < length) {
                    expanded.append(current).append(input.charAt(index + 1));
                } else {
                    // 最后一个字符单独处理，补零
                    expanded.append(current).append('0');
                }
                index += 2;
            }
        }

        return "0x" + expanded.toString();
    }

}
