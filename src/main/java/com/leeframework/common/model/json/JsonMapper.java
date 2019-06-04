package com.leeframework.common.model.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * 简单封装Jackson,包装ObjectMapper,自定义使用方法,同时定义spring json转换的格式
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:07:25
 */
public class JsonMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;
    private static Logger log = LoggerFactory.getLogger(JsonMapper.class);

    public JsonMapper() {
        this(Include.ALWAYS);
    }

    public JsonMapper(Include include) {

        // 设置输出时包含属性的风格
        // Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空("") 或者为 NULL 都不序列化
        // Include.NON_NULL 属性为NULL 不序列化
        if (include != null) {
            this.setSerializationInclusion(include);
        }

        // 允许单引号、允许不带引号的字段名称
        this.enableSimple();

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeString("");
            }
        });

        // 设置时区
        this.setTimeZone(TimeZone.getDefault());

        // 设置时间类型默认的转换格式
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 枚举类型用toString()获取值
        this.enableEnumUseToString();

        // 注册hibernate模块,解决懒加载的问题
        this.registerModule(new Hibernate4Module());
    }

    /**
     * 获取一个默认的实例
     * @author lee
     * @date 2016年5月19日 下午3:22:22
     * @return
     */
    public static JsonMapper getInstance() {
        return new JsonMapper();
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     * @author lee
     * @date 2016年5月19日 下午3:11:05
     * @return
     */
    public static JsonMapper getInstanceNonDefault() {
        return new JsonMapper(Include.NON_DEFAULT);
    }

    /**
     * 将指定对象转换为JSON串
     * @author lee
     * @date 2016年5月19日 下午3:11:41
     * @param object Object可以是POJO，也可以是Collection或数组
     * @return 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]"
     */
    public String toJson(Object object) {
        try {
            return this.writeValueAsString(object);
        } catch (IOException e) {
            log.error("Write to json string error:{}" + object, e);
        }
        return null;
    }

    /**
     * 反序列化POJO或简单Collection如List<String>. 请使用fromJson(String,JavaType)
     * @author lee
     * @date 2016年5月19日 下午3:13:52
     * @param jsonString json串
     * @param clazz Class
     * @see #fromJson(String, JavaType)
     * @return 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合. <br>
     *         如需反序列化复杂Collection如List<Bean>,请使用{@link #fromJson(jsonString, javaType)}
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (!StringUtils.isEmpty(jsonString)) {
            try {
                return this.readValue(jsonString, clazz);
            } catch (IOException e) {
                log.error("Parse json string error:{}", jsonString, e);
            }
        }
        return null;

    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函数createCollectionType构造类型,然后调用本函数.
     * @author lee
     * @date 2016年5月19日 下午3:18:23
     * @param jsonString json串
     * @param javaType JavaType
     * @see #createCollectionType(Class, Class...)
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (!StringUtils.isEmpty(jsonString)) {
            try {
                return (T) this.readValue(jsonString, javaType);
            } catch (IOException e) {
                log.error("Parse json string error:{}", jsonString, e);
            }
        }
        return null;
    }

    /**
     * 设定是否使用Enum的toString方法类读写Enum,为false时候使用Enum的name()函数读写,默认为false<br>
     * 注意本函数一定要在Mapper创建后,所有读写动作之前调用
     * @author lee
     * @date 2016年5月19日 下午3:27:10
     * @return
     */
    public JsonMapper enableEnumUseToString() {
        this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        return this;
    }

    /**
     * 允许单引号 允许不带引号的字段名称
     * @author lee
     * @date 2016年5月19日 下午3:26:56
     * @return
     */
    public JsonMapper enableSimple() {
        this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return this;
    }

    /**
     * 取出ObjectMapper做进一步的设置或使用其他序列化API.
     * @author lee
     * @date 2016年5月19日 下午3:26:31
     * @return ObjectMapper
     */
    public ObjectMapper getMapper() {
        return this;
    }

    /**
     * 对象转换为JSON字符串
     * @author lee
     * @date 2016年5月19日 下午3:26:01
     * @param object 被转换的对象
     * @return
     */
    public static String toJsonString(Object object) {
        return JsonMapper.getInstance().toJson(object);
    }

    /**
     * JSON字符串转换为对象
     * @author lee
     * @date 2016年5月19日 下午3:25:43
     * @param jsonString json串
     * @param clazz Class
     * @return
     */
    public static Object fromJsonString(String jsonString, Class<?> clazz) {
        return JsonMapper.getInstance().fromJson(jsonString, clazz);
    }

}
