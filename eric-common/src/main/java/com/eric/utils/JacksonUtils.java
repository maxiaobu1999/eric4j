package com.eric.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

/***
 *
 */
public class JacksonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);


    static {
        MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(SerializationFeature.INDENT_OUTPUT, false);
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(Feature.ALLOW_SINGLE_QUOTES, true);
    }

    private JacksonUtils() {
    }

    /**
     * 将实体对象转换为json字符串
     *
     * @param entity 实体对象
     * @param <T>    泛型
     * @return json string
     * @throws JsonProcessingException JsonProcessingException
     */
    public static <T> String obj2json(T entity) throws JsonProcessingException {
        return MAPPER.writeValueAsString(entity);
    }

    /**
     * 将实体对象转换为json字符串
     *
     * @param entity 实体对象
     * @param pretty 是否转换为美观格式
     * @param <T>    泛型
     * @return json string
     * @throws JsonProcessingException JsonProcessingException
     */
    public static <T> String obj2json(T entity, boolean pretty) throws JsonProcessingException {
        if (pretty) {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(entity);
        }
        return MAPPER.writeValueAsString(entity);
    }

    /**
     * 将实体对象转换为字节数组
     *
     * @param entity 实体对象
     * @param <T>    泛型
     * @return json string
     * @throws JsonProcessingException JsonProcessingException
     */
    public static <T> byte[] obj2jsonBytes(T entity) throws JsonProcessingException {
        return MAPPER.writeValueAsBytes(entity);
    }

    public static <T> T bytes2obj(byte[] bytes , Type type) throws IOException {
        JavaType javaType = TypeFactory.defaultInstance().constructType(type);
        return MAPPER.readValue(bytes, javaType);
    }

    /**
     * 将实体类转换为JsonNode对象
     *
     * @param entity 实体对象
     * @param <T>    泛型
     * @return JsonNode
     */
    public static <T> JsonNode obj2node(T entity) {
        return MAPPER.valueToTree(entity);
    }

    /**
     * 将实体对象写入文件
     *
     * @param filepath 文件绝对路径
     * @param entity   实体对象
     * @param <T>      泛型
     * @return 写入成功：true，否则：false
     */
    public static <T> boolean write2jsonFile(String filepath, T entity) {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                boolean success = file.createNewFile();
                if (!success) {
                    log.error("[write2jsonFile]-创建文件失败！路径：{}", filepath);
                    return false;
                }
            } catch (IOException e) {
                log.error("[write2jsonFile]-创建文件失败！路径：{}，失败原因：{}", filepath, e.getMessage());
                return false;
            }
        }
        return write2jsonFile(new File(filepath), entity);
    }

    /**
     * 将实体对象写入指定文件
     *
     * @param file   文件
     * @param entity 实体对象
     * @param <T>    泛型
     * @return 写入成功：true，否则：false
     */
    public static <T> boolean write2jsonFile(File file, T entity) {
        try {
            MAPPER.writeValue(file, entity);
            return true;
        } catch (IOException e) {
            log.error("[write2jsonFile]-写入文件失败！路径：{}，失败原因：{}", file.getAbsolutePath(), e.getMessage());
        }
        return false;
    }

    /**
     * 将json字符串转换为实体类对象
     *
     * @param json json字符串
     * @param type 实体对象类型
     * @param <T>  泛型
     * @return 转换成功后的对象
     * @throws IOException IOException
     */
    public static <T> T json2obj(String json, Class<T> type) throws IOException {
        if ("".equals(json)) {
            return MAPPER.readValue("{}", type);
        }
        return MAPPER.readValue(json, type);
    }

    /**
     * 将json字符串转换为map
     *
     * @param json json字符串
     * @return Map
     * @throws JsonProcessingException JsonProcessingException
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> json2map(String json) throws IOException {
        return (Map<String, T>) MAPPER.readValue(json, Map.class);
    }

    public static <T> TreeMap<String, T> json2TreeMap(String json) throws IOException {
        return (TreeMap<String, T>) MAPPER.readValue(json, TreeMap.class);
    }
    /**
     * 泛化转换方式，此方式最为强大、灵活
     * <p>
     * example：
     * <p>
     * {@code Map<String, List<UserInfo>> listMap = genericConvert(jsonStr, new TypeReference<Map<String, List<UserInfo>>>() {});}
     *
     * @param json json字符串
     * @param type type
     * @param <T>  泛化
     * @return T
     * @throws JsonProcessingException JsonProcessingException
     */
    public static <T> T genericConvert(String json, TypeReference<T> type) throws IOException {
        return MAPPER.readValue(json, type);
    }


    /**
     * 将map转换为实体类对象
     *
     * @param map  map
     * @param type 实体对象类型
     * @param <T>  泛型
     * @return 实体对象
     */
    public static <T> T map2obj(Map map, Class<T> type) {
        return MAPPER.convertValue(map, type);
    }

    /**
     * 将文件内容转为实体类对象
     *
     * @param file 文件
     * @param type 实体类类型
     * @param <T>  泛型
     * @return 实体类对象
     * @throws IOException IOException
     */
    public static <T> T file2obj(File file, Class<T> type) throws IOException {
        return MAPPER.readValue(file, type);
    }

    /**
     * 将url指向的资源转换为实体类对象
     *
     * @param url  url
     * @param type 实体类对象类型
     * @param <T>  泛型
     * @return 实体类对象
     * @throws IOException IOException
     */
    public static <T> T urlResource2obj(URL url, Class<T> type) throws IOException {
        return MAPPER.readValue(url, type);
    }

    /**
     * 将json字符串转换为实体类集合
     *
     * @param json json字符串
     * @param type 实体对象类型
     * @param <T>  泛型
     * @return list集合
     * @throws JsonProcessingException JsonProcessingException
     */
    public static <T> List<T> json2list(String json, Class<T> type) throws IOException {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(List.class, type);
        return MAPPER.readValue(json, collectionType);
    }


    /**
     * 将json字符串转换为JsonNode对象
     *
     * @param json json字符串
     * @return JsonNode对象
     * @throws JsonProcessingException JsonProcessingException
     */
    public static JsonNode json2node(String json) throws IOException {
        return MAPPER.readTree(json);
    }


    /**
     * 检查字符串是否是json格式
     *
     * @param str 待检查字符串
     * @return 是：true，否：false
     */
    public static boolean isJsonString(String str) {
        try {
            MAPPER.readTree(str);
            return true;
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("[isJsonString]-检查字符串是否是json格式...{}", e.getMessage());
            }
            return false;
        }
    }

    /**
     * 打印json到控制台
     *
     * @param obj    需要打印的对象
     * @param pretty 是否打印美观格式
     * @param <T>    泛型
     */
    public static <T> void printJson(T obj, boolean pretty) {
        try {
            if (pretty) {
                System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
            } else {
                System.out.println(MAPPER.writeValueAsString(obj));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印json到控制台
     *
     * @param json json字符串
     * @param objClass 映射的对象类型
     * @param <T>    泛型
     */
    public static <T> T readValue(String json, Class<T> objClass) {
        try {
            return MAPPER.readValue(json, objClass);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T copyObj(T object) {
        try {
            String json = obj2json(object);
            return (T) json2obj(json, object.getClass());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
