package com.leeframework.common.hibernate4;

import java.util.HashMap;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

import com.leeframework.common.model.DynamicBean;

/**
 * Hibernate.SQLQuery查询结果类型的转换,将查询结果转换为动态Bean
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月20日 Time: 下午2:41:25
 * @version 1.0
 * @since version 1.0
 * @update
 */
@SuppressWarnings("rawtypes")
public class DynamicResultTransformer implements ResultTransformer {
    private static final long serialVersionUID = 1L;

    /**
     * 指定返回结果的参数类型
     */
    private List<Class> types;

    public DynamicResultTransformer() {
    }

    /**
     * 指定返回结果的数据类型构造方法
     * @date 2015-6-3 下午04:43:51
     * @param types 返回结果的数据类型,需要跟查询结果的顺序一致
     */
    public void setTypes(List<Class> types) {
        this.types = types;
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }

    @Override
    public Object transformTuple(Object[] values, String[] columns) {

        // 如果不指定,默认都是string类型
        HashMap<String, Class<?>> properties = new HashMap<String, Class<?>>();
        for (int i = 0; i < columns.length; i++) {
            String name = columns[i];
            Class type = types == null ? (values[i] == null ? String.class : values[i].getClass()) : types.get(i);
            properties.put(name, type);
        }
        DynamicBean bean = new DynamicBean(properties);
        for (int i = 0; i < columns.length; i++) {
            String name = columns[i];
            bean.setValue(name, values[i]);
        }

        return bean.getObject();
    }

}
