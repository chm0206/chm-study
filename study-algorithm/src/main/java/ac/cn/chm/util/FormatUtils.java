package ac.cn.chm.util;

import ac.cn.chm.tree.TreeAnalysis;
import ac.cn.chm.tree.TreeNodes;
import ac.cn.chm.tree.TreeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FormatUtils {

    /**
     * 获取 yyyy-MM-dd HH:mm:ss 格式的时间
     **/
    public static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 获取 yyyy-MM-dd 格式的时间
     **/
    public static final SimpleDateFormat SDF_DAY = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 获取 HH:mm:ss 格式的时间
     **/
    public static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm:ss");
    /**
     * 获取 yyyy-MM-dd HH:mm:ss.SSS 格式的时间
     **/
    public static final SimpleDateFormat SDF_NOW = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * 获取 yyyy-MM 格式的时间
     **/
    public static final SimpleDateFormat SDF_MONTH = new SimpleDateFormat("yyyy-MM");
    /**
     * 获取 yyyy 格式的时间
     **/
    public static final SimpleDateFormat SDF_YEAR = new SimpleDateFormat("yyyy");
    /**
     * 获取 HH:mm 格式的时间
     **/
    public static final SimpleDateFormat SDF_CURRENT_DATE = new SimpleDateFormat("HH:mm");


    /**
     * 使用fastJson,但是效率并不高
     *
     * @param date
     * @param format
     * @return
     */
    @Deprecated
    public static String data2JsonStr(Date date, String format) {
        if (CheckUtils.isEmpty(format)) {
            return JSON.toJSONStringWithDateFormat(date, format);
        } else {
            return "";
        }
    }

    /**
     * 按照参数format的格式，日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        String str = null;
        if (CheckUtils.isNotEmpty(date) && CheckUtils.isNotEmpty(format)) {
            try {
                switch (format) {//优化效率，使用常量，多次使用不需要重新初始化
                    //常用的要放在前面，提高效率
                    case "yyyy-MM-dd HH:mm:ss":
                        str = SDF_DATE.format(date);
                        break;
                    case "yyyy-MM-dd":
                        str = SDF_DAY.format(date);
                        break;
                    case "HH:mm:ss":
                        str = SDF_TIME.format(date);
                        break;
                    case "yyyy-MM-dd HH:mm:ss.SSS":
                        str = SDF_NOW.format(date);
                        break;
                    case "yyyy-MM":
                        str = SDF_MONTH.format(date);
                        break;
                    case "yyyy":
                        str = SDF_YEAR.format(date);
                        break;
                    case "HH:mm":
                        str = SDF_CURRENT_DATE.format(date);
                        break;
                    default:
                        str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                }
            } catch (Exception e) {
                e.printStackTrace();
                str = "";
            }
        }
        return str;
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串(可以使用dateUtil工具类)
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
//    @Deprecated
    public static String date2Str(Date date) {
        if (CheckUtils.isEmpty(date)) return null;
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将指定格式的字符串转为时间对象
     *
     * @param str
     * @param format
     * @return 失败返回当前时间
     */
    public static Date str2Date(String str, String format) {
        if (CheckUtils.isOneEmpty(str, format)) {
            return null;
        }
        Date date;
        try {
            switch (format) {//优化效率，使用常量，多次使用不需要重新初始化
                //常用的要放在前面，提高效率
                case "yyyy-MM-dd HH:mm:ss":
                    date = SDF_DATE.parse(str);
                    break;
                case "yyyy-MM-dd":
                    date = SDF_DAY.parse(str);
                    break;
                case "HH:mm:ss":
                    date = SDF_TIME.parse(str);
                    break;
                case "yyyy-MM-dd HH:mm:ss.SSS":
                    date = SDF_NOW.parse(str);
                    break;
                case "yyyy-MM":
                    date = SDF_MONTH.parse(str);
                    break;
                case "yyyy":
                    date = SDF_YEAR.parse(str);
                    break;
                case "HH:mm":
                    date = SDF_CURRENT_DATE.parse(str);
                    break;
                default:
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的字符串转换为时间对象
     *
     * @param str
     * @return 失败返回当前时间
     */
    public static Date str2Date(String str) {
        return str2Date(str, "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 将对象转换为map
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> object2Map(T t) {
        return CheckUtils.isEmpty(t) ? new HashMap() : entity2Json(t);
    }

    /**
     * 将对象转换为json
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> JSONObject entity2Json(T t) {
        return CheckUtils.isEmpty(t) ? new JSONObject() : (JSONObject) JSONObject.toJSON(t);
    }

    /**
     * json字符串转json对象
     *
     * @param str
     * @return
     */
    public static JSONObject str2Json(String str) {
//        return CheckUtils.isEmpty(str)?new JSONObject():(JSONObject)JSONObject.parse(str);
        if (CheckUtils.isEmpty(str)) new JSONObject();
        try {
            return (JSONObject) JSONObject.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    /**
     * json字符串转map
     *
     * @param str
     * @return
     */
    public static Map<String, Object> str2Map(String str) {
        return CheckUtils.isEmpty(str) ? new HashMap<>() : str2Json(str);
    }

    /**
     * json字符串转换对象list
     *
     * @param str   list-json格式字符串
     * @param clazz 要拷贝的类型
     * @param <T>   拷贝的类型
     * @return
     */
    public static <T> List<T> str2List(String str, Class<T> clazz) {
        return CheckUtils.isOneEmpty(str, clazz) ? new ArrayList<T>() : JSONArray.parseArray(str, clazz);
    }

    /**
     * Map转JSONObject（应该是用不上的，但依旧添加，使功能完善）
     *
     * @param map
     * @return
     */
    public static JSONObject map2Json(Map<String, Object> map) {
        return new JSONObject(map);
    }

    /**
     * json字符串转对象
     *
     * @param str   要转换的json格式字符串
     * @param clazz 要转换的对象类型
     * @param <T>   要转换的对象实体泛型
     * @return
     */
    public static <T> T str2Entity(String str, Class<T> clazz) {
        return CheckUtils.isEmpty(str) ? null : objectClone(str, clazz);
    }

    /**
     * 通过反射进行对象拷贝(支持json,map,object)
     *
     * @param v    源list
     * @param clazz 要拷贝的类型
     * @param <T>   拷贝的类型
     * @param <V>   源类型
     * @return
     */
    public static <T, V> T objectClone(V v, Class<T> clazz) {
        if (CheckUtils.isOneEmpty(v, clazz)) return null;
        try {
            return JSON.parseObject(v instanceof String ? (String) v : JSON.toJSONString(v), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对象list拷贝
     *
     * @param vs    源list
     * @param clazz 要拷贝的类型
     * @param <T>   拷贝的类型
     * @param <V>   源类型
     * @return
     */
    public static <T, V> List<T> listClone(List<V> vs, Class<T> clazz) {
        if (CheckUtils.isOneEmpty(vs, clazz)) return new ArrayList<T>();
        try {
            return JSONArray.parseArray(JSON.toJSONString(vs), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

    /**
     * 将json格式的字符串转换为指定对象的列表
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> listClone(String str, Class<T> clazz) {
        return str2List(str, clazz);
    }

    /**
     * 获取列表里的指定key,并转换为指定格式List，如List<User>-->获取userId-->List<Long> userIds
     *
     * @param vs    源列表
     * @param clazz 获取key的格式
     * @param key   对象中的某一个字段名称
     * @param <T>   key的对象类型
     * @param <V>   源数据对象类型
     * @return
     */
    public static <T, V> List<T> listClone(List<V> vs, Class<T> clazz, String key) {
        List<JSONObject> list = JSONArray.parseArray(JSON.toJSONString(vs), JSONObject.class);
        List<T> ts = new ArrayList<>();
        for (JSONObject obj : list) {
            try {
                ts.add((T) obj.get(key));
            } catch (Exception e) {
                return new ArrayList<T>();
            }
        }
        return ts;
    }

    /**
     * 根据注解获取包含指定key的树
     *
     * @param vs  要转换为树的list集合
     * @param t   从哪个实体类中获取注解
     * @param <T> list集合的实体类
     * @param <V> 需要获取注解的实体类
     * @return
     */
    public static <T, V> List<TreeNodes> list2TreeByAnno(List<V> vs, Class<T> t) {
        return TreeUtils.formatTreeByMap(vs, TreeAnalysis.source(t), TreeAnalysis.target(t));
    }

    /**
     * 将列表转换为树，不去除多余的key值
     *
     * @param vs            要转换为树的list集合
     * @param t             从哪个实体类中获取注解
     * @param <T>list集合的实体类
     * @param <V>需要获取注解的实体类
     * @return
     */
    public static <T, V> List<TreeNodes> list2Tree(List<V> vs, Class<T> t) {
        return TreeUtils.formatTree(vs, TreeAnalysis.source(t), TreeAnalysis.target(t));
    }

    /**
     * 将前端保存的数据转换为实体类数据
     *
     * @param list  源数据
     * @param clazz 从哪个实体类中获取注解
     * @param <T>   list集合的实体类
     * @param <K>   需要获取注解的实体类
     * @return
     */
    public static <T, K> List<K> listCloneChangeKey(List<T> list, Class<K> clazz) {
        List<TreeNodes> tree = TreeUtils.formatList(list, TreeAnalysis.target(clazz), TreeAnalysis.source(clazz));//source与target位置交换，达到反向替换的目的
        return listClone(tree, clazz);
    }

    /**
     * 将列表转换为树
     *
     * @param t   实体类数据
     * @param <T> 列表的实体类泛型
     * @return
     */
    public static <T, K> TreeNodes bean2Nodes(T t, Map<String, String> source, Class<K> clazz) {
        return TreeUtils.formatBean(t, TreeAnalysis.source(clazz), TreeAnalysis.target(clazz));
    }

    /**
     * 将指定的对象转换为相应的实体类
     *
     * @param t     源数据
     * @param clazz 从哪个实体类中获取注解
     * @param <T>   要转换的实体类
     * @param <K>   需要获取注解的实体类
     * @return
     */
    public static <T, K> K objectCloneChangeKey(T t, Class<K> clazz) {
        TreeNodes nodes = TreeUtils.formatBean(t, TreeAnalysis.target(clazz), TreeAnalysis.source(clazz));//source与target位置交换，达到反向替换的目的
        return objectClone(nodes, clazz);
    }
}
