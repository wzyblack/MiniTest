package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

/**
 * json工具类
 * 
 * @author komojoemary_suiyue
 * @version [版本号, 2015-2-28]
 */
public class JsonUtil
{
    /**
     * 从一个JSON 对象字符格式中得到一个java对象
     * 
     * @param jsonString
     *            json字符串
     * @param pojoClass
     *            要转换成的对象
     * @return T 目标对象
     */

    public static <T> T jsonToObject(String jsonString, Class<T> pojoClass) {
        return jsonToObject(jsonString, pojoClass, EpointDateUtil.DATE_TIME_FORMAT);
    }

    /**
     * 从一个JSON 对象字符格式中得到一个java对象
     * 
     * @param jsonString
     *            json字符串
     * @param pojoClass
     *            要转换成的对象
     * @param dateFormat
     *            指定的日期格式
     * @return T 目标对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObject(String jsonString, Class<T> pojoClass, String dateFormat) {
        Object pojo;
        JsonConfig jsonConfig = configJson(dateFormat);
        JSONObject jsonObject = JSONObject.fromObject(jsonString, jsonConfig);
        Set<String> keys = jsonObject.keySet();
        List<String> nulls = new ArrayList<String>();
        // 只有转为弱类型map对象的时候,我们才保留下null,其他强类型通通忽略掉,否则会出现get出来是个JSONNull对象问题
        // edit by ko 2016-6-3
        if (pojoClass != Map.class && pojoClass != HashMap.class
                && !pojoClass.getName().equalsIgnoreCase("com.epoint.core.grammar.Record")) {
            for (String key : keys) {
                Object item = jsonObject.get(key);
                if (item instanceof JSONNull) {
                    nulls.add(key);
                }
            }
            for (String key : nulls) {
                jsonObject.remove(key);
            }
        }
        pojo = JSONObject.toBean(jsonObject, pojoClass);
        return (T) pojo;
    }

    /**
     * json字符串转换成集合
     * 
     * @param jsonString
     *            json字符串
     * @param pojoClass
     *            要转换成的对象
     * @return List<T> list集合
     */

    public static <T> List<T> jsonToList(String jsonString, Class<T> pojoClass) {
        return jsonToList(jsonString, pojoClass, EpointDateUtil.DATE_TIME_FORMAT);
    }

    /**
     * json字符串转换成集合
     * 
     * @param jsonString
     *            json字符串
     * @param pojoClass
     *            要转换成的对象
     * @param dateFormat
     *            指定的日期格式
     * @return List<T> list集合
     */

    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToList(String jsonString, Class<T> pojoClass, String dateFormat) {
        JsonConfig jsonConfig = configJson(dateFormat);
        JSONArray jsonArray = JSONArray.fromObject(jsonString, jsonConfig);
        JSONObject jsonObject;
        Object pojoValue;
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Object obj = jsonArray.get(i);
            if (obj instanceof JSONObject) {
                jsonObject = jsonArray.getJSONObject(i);
                pojoValue = JSONObject.toBean(jsonObject, pojoClass);
                list.add((T) pojoValue);
            }
            else {
                list.add((T) obj);
            }
        }
        return list;
    }

    /**
     * json字符串转换成map
     * 
     * @param jsonString
     *            json字符串
     * @return Map<String, Object> map对象
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        JSONObject json = JSONObject.fromObject(jsonString);
        return jsonToMap(json);
    }

    /**
     * 将java对象转换成json字符串
     * 
     * @param javaObj
     *            普通java对象
     * @return String json字符串
     */
    public static String objectToJson(Object javaObj) {
        return objectToJson(javaObj, EpointDateUtil.DATE_TIME_FORMAT);
    }

    /**
     * 将java对象转换成json字符串,并设定日期格式
     * 
     * @param javaObj
     *            要转换的java对象
     * @param dateFormat
     *            指定的日期格式
     * @return String json字符串
     */
    public static String objectToJson(Object javaObj, String dateFormat) {
        JsonConfig jsonConfig = configJson(dateFormat);
        JSONObject json = JSONObject.fromObject(javaObj, jsonConfig);
        return json.toString();

    }

    /**
     * list变成json
     * 
     * @param list
     *            集合对象
     * @return String json字符串
     */
    public static <T> String listToJson(List<T> list) {
        return listToJson(list, EpointDateUtil.DATE_TIME_FORMAT);
    }

    /**
     * list变成json
     * 
     * @param list
     *            集合对象
     * @param dateFormat
     *            指定的日期格式
     * @return String json字符串
     */
    public static <T> String listToJson(List<T> list, String dateFormat) {
        JSONArray json;
        JsonConfig jsonConfig = configJson(dateFormat);
        json = JSONArray.fromObject(list, jsonConfig);
        return json.toString();
    }

    /**
     * JSON 时间解析器
     * 
     * @param datePattern
     * @return JsonConfig
     */
    private static JsonConfig configJson(final String datePattern) {

        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {datePattern }));

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor()
        {

            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                if (value instanceof Date) {
                    String str = new SimpleDateFormat(datePattern).format((Date) value);
                    return str;
                }
                return value == null ? null : value.toString();
            }

            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                String[] obj = {};
                if (value instanceof Date[]) {
                    SimpleDateFormat sf = new SimpleDateFormat(datePattern);
                    Date[] dates = (Date[]) value;
                    obj = new String[dates.length];
                    for (int i = 0; i < dates.length; i++) {
                        obj[i] = sf.format(dates[i]);
                    }
                }
                return obj;
            }
        });

        jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessor()
        {

            @Override
            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                if (value instanceof java.sql.Date) {
                    Date uDate = new Date(((java.sql.Date) value).getTime());
                    String str = new SimpleDateFormat(datePattern).format(uDate);
                    return str;
                }
                return value == null ? null : value.toString();
            }

            @Override
            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                String[] obj = {};
                if (value instanceof java.sql.Date[]) {
                    SimpleDateFormat sf = new SimpleDateFormat(datePattern);
                    Date[] dates = (Date[]) value;
                    obj = new String[dates.length];
                    for (int i = 0; i < dates.length; i++) {
                        Date uDate = new Date(((java.sql.Date) dates[i]).getTime());
                        obj[i] = sf.format(uDate);
                    }
                }
                return obj;
            }
        });
        
        // 加入时间戳
        jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessor()
        {

            @Override
            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                if (value instanceof java.sql.Date) {
                    Date uDate = new Date(((java.sql.Date) value).getTime());
                    String str = new SimpleDateFormat(datePattern).format(uDate);
                    return str;
                }
                return value == null ? null : value.toString();
            }

            @Override
            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                String[] obj = {};
                if (value instanceof java.sql.Date[]) {
                    SimpleDateFormat sf = new SimpleDateFormat(datePattern);
                    Date[] dates = (Date[]) value;
                    obj = new String[dates.length];
                    for (int i = 0; i < dates.length; i++) {
                        Date uDate = new Date(((java.sql.Date) dates[i]).getTime());
                        obj[i] = sf.format(uDate);
                    }
                }
                return obj;
            }
        });
        return jsonConfig;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> jsonToMap(JSONObject json) {
        Map<String, Object> columnValMap = new HashMap<String, Object>();
        Set<String> jsonKeys = json.keySet();
        for (String key : jsonKeys) {
            Object JsonValObj = json.get(key);
            if (JsonValObj instanceof JSONObject) {
                columnValMap.put(key, jsonToMap((JSONObject) JsonValObj));
            }
            else {
                columnValMap.put(key, JsonValObj);
            }
        }
        return columnValMap;
    }

}
