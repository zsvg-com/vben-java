package vben.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.AntPathMatcher;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StrUtils {

    //---------------通过组合方式使用hutool的StrUtil工具类，方便有需要替换的，也可以注释掉，直接继承StrUtil--------------------------

    public static final String EMPTY = "";

    public static final String SLASH = "";

    public static boolean isEmpty(CharSequence str) {
        return StrUtil.isEmpty(str);
    }

    public static boolean isNotEmpty(CharSequence str) {
        return StrUtil.isNotEmpty(str);
    }

    public static boolean isBlank(CharSequence str) {
        return StrUtil.isBlank(str);
    }

    public static boolean isNotBlank(CharSequence str) {
        return StrUtil.isNotBlank(str);
    }

    public static boolean hasEmpty(CharSequence... strs) {
       return StrUtil.hasEmpty(strs);
    }

    public static String blankToDefault(CharSequence str, String defaultStr) {
        return StrUtil.blankToDefault(str, defaultStr);
    }

    public static String format(CharSequence template, Object... params) {
       return StrUtil.format(template, params);
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return StrUtil.equalsIgnoreCase(str1, str2);
    }

    public static boolean equalsAnyIgnoreCase(CharSequence str1, CharSequence... strs) {
        return StrUtil.equalsAnyIgnoreCase(str1, strs);
    }

    public static boolean equalsAny(CharSequence str1, CharSequence... strs) {
        return StrUtil.equalsAny(str1, strs);
    }

    public static boolean startWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return StrUtil.startWithIgnoreCase(str, prefix);
    }

    public static boolean startWith(CharSequence str, CharSequence prefix) {
        return StrUtil.startWith(str, prefix);
    }

    public static boolean endWith(CharSequence str, CharSequence suffix) {
        return StrUtil.endWith(str, suffix);
    }

    public static boolean endWithAny(CharSequence str, CharSequence... suffixes) {
        return StrUtil.endWithAny(str, suffixes);
    }

    public static String upperFirst(CharSequence str) {
        return StrUtil.upperFirst(str);
    }

    public static boolean contains(CharSequence str, char searchChar) {
        return StrUtil.contains(str, searchChar);
    }


    public static boolean contains(CharSequence str, CharSequence searchStr) {
       return StrUtil.contains(str, searchStr);
    }

    public static boolean containsAny(CharSequence str, CharSequence... testStrs) {
        return StrUtil.containsAny(str, testStrs);
    }

    public static boolean containsAny(CharSequence str, char... testChars) {
        return StrUtil.containsAny(str, testChars);
    }

    public static List<String> split(CharSequence str, CharSequence separator) {
        return StrUtil.split(str, separator);
    }

    public static List<String> split(CharSequence str, CharSequence separator, boolean isTrim, boolean ignoreEmpty) {
        return StrUtil.split(str, separator, isTrim, ignoreEmpty);
    }

    public static String[] splitToArray(CharSequence str, CharSequence separator) {
        return StrUtil.splitToArray(str, separator);
    }

    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return StrUtil.indexOfIgnoreCase(str, searchStr);
    }

    public static String trim(CharSequence str) {
        return StrUtil.trim(str);
    }

    public static String trimToEmpty(CharSequence str) {
        return StrUtil.trimToEmpty(str);
    }

    public static String str(byte[] data, Charset charset) {
       return StrUtil.str(data, charset);
    }

    public static String join(CharSequence conjunction, Object... objs) {
        return StrUtil.join(conjunction, objs);
    }

    public static <T> String join(CharSequence conjunction, Iterable<T> iterable) {
        return StrUtil.join(conjunction, iterable);
    }

    public static String padPre(CharSequence str, int length, char padChar) {
        return StrUtil.padPre(str, length, padChar);
    }

    public static String sub(CharSequence str, int fromIndexInclude, int toIndexExclude) {
        return StrUtil.sub(str, fromIndexInclude, toIndexExclude);
    }

    public static String subBetween(CharSequence str, CharSequence before, CharSequence after) {
        return StrUtil.subBetween(str, before, after);
    }

    public static String subWithLength(String input, int fromIndex, int length) {
        return StrUtil.subWithLength(input, fromIndex, length);
    }

    public static String subAfter(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        return StrUtil.subAfter(string, separator, isLastSeparator);
    }

    public static String removePrefix(CharSequence str, CharSequence prefix) {
        return StrUtil.removePrefix(str, prefix);
    }

    public static boolean equals(CharSequence str1, CharSequence str2) {
        return StrUtil.equals(str1, str2);
    }

    public static String removeAll(CharSequence str, CharSequence strToRemove) {
       return StrUtil.removeAll(str, strToRemove);
    }

    public static boolean isAllNotBlank(CharSequence... args) {
        return StrUtil.isAllNotBlank(args);
    }

    public static List<String> splitTrim(CharSequence str, char separator) {
        return StrUtil.splitTrim(str, separator);
    }

    public static String toUnderlineCase(CharSequence str) {
        return StrUtil.toUnderlineCase(str);
    }

    public static String lowerFirst(CharSequence str) {
        return StrUtil.lowerFirst(str);
    }

    public static String toLowerCase(final CharSequence str) {
        return StrUtil.toLowerCase(str);
    }

    public static boolean isUrl(CharSequence value) {
        return Validator.isUrl(value);
    }

    //-------------------------------以上直接通过组合方式使用Hutool的StrUtil工具类,下面是一些扩展-----------------------------------------

    /**
     * 切分字符串自定义转换
     *
     * @param str       被切分的字符串
     * @param separator 分隔符
     * @param mapper    自定义转换
     * @return 分割后的数据列表
     */
    public static <T> List<T> splitTo(String str, String separator, Function<? super Object, T> mapper) {
        if (StrUtils.isBlank(str)) {
            return new ArrayList<>(0);
        }
        return StrUtil.split(str, separator)
            .stream()
            .filter(Objects::nonNull)
            .map(mapper)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /**
     * 切分字符串自定义转换(分隔符默认逗号)
     *
     * @param str    被切分的字符串
     * @param mapper 自定义转换
     * @return 分割后的数据列表
     */
    public static <T> List<T> splitTo(String str, Function<? super Object, T> mapper) {
        return splitTo(str, ",", mapper);
    }

    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符
     * @return 分割后的数据列表
     */
    public static List<String> splitList(String str, String separator) {
        return splitTo(str, separator, Convert::toStr);
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str  指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, List<String> strs) {
        if (StrUtils.isEmpty(str) || CollUtil.isEmpty(strs)) {
            return false;
        }
        for (String pattern : strs) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    public static String replaceEach(String text, String[] searchList, String[] replacementList) {
        if (text == null || searchList == null || replacementList == null) {
            return text;
        }

        if (searchList.length != replacementList.length) {
            throw new IllegalArgumentException("Search and Replacement array lengths don't match: "
                + searchList.length + " vs " + replacementList.length);
        }

        String result = text;
        for (int i = 0; i < searchList.length; i++) {
            result = StrUtil.replace(result, searchList[i], replacementList[i]);
        }
        return result;
    }


    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, true);
    }

    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        } else {
            int len = str.length();
            if (len == 0) {
                return new String[0];
            } else {
                List<String> list = new ArrayList();
                int sizePlus1 = 1;
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;
                if (separatorChars != null) {
                    if (separatorChars.length() != 1) {
                        while(i < len) {
                            if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                                if (match || preserveAllTokens) {
                                    lastMatch = true;
                                    if (sizePlus1++ == max) {
                                        i = len;
                                        lastMatch = false;
                                    }

                                    list.add(str.substring(start, i));
                                    match = false;
                                }

                                ++i;
                                start = i;
                            } else {
                                lastMatch = false;
                                match = true;
                                ++i;
                            }
                        }
                    } else {
                        char sep = separatorChars.charAt(0);

                        while(i < len) {
                            if (str.charAt(i) == sep) {
                                if (match || preserveAllTokens) {
                                    lastMatch = true;
                                    if (sizePlus1++ == max) {
                                        i = len;
                                        lastMatch = false;
                                    }

                                    list.add(str.substring(start, i));
                                    match = false;
                                }

                                ++i;
                                start = i;
                            } else {
                                lastMatch = false;
                                match = true;
                                ++i;
                            }
                        }
                    }
                } else {
                    while(i < len) {
                        if (Character.isWhitespace(str.charAt(i))) {
                            if (match || preserveAllTokens) {
                                lastMatch = true;
                                if (sizePlus1++ == max) {
                                    i = len;
                                    lastMatch = false;
                                }

                                list.add(str.substring(start, i));
                                match = false;
                            }

                            ++i;
                            start = i;
                        } else {
                            lastMatch = false;
                            match = true;
                            ++i;
                        }
                    }
                }

                if (match || preserveAllTokens && lastMatch) {
                    list.add(str.substring(start, i));
                }

                return (String[])list.toArray(new String[0]);
            }
        }
    }


}
