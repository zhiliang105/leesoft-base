package com.leeframework.core.tag.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leeframework.common.model.DynamicBean;
import com.leeframework.common.model.view.PagingParameter;
import com.leeframework.common.utils.StringUtil;

/**
 * Bootstrap-table自定义标签
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月3日 下午3:07:37
 */
public class BootstrapTableTag extends AbstractTemplateUITag {
    private static final long serialVersionUID = 1L;

    private final static String TEMP_NAME = "tpl_bootstrap_table.ftl";

    public final static String DEFAULT_GRID_ID = "data_list";

    public static final int DEFAULT_PAGESIZE = PagingParameter.DEFAULT_SIZE;
    public static final int[] DEFAULT_PAGELSIT = { DEFAULT_PAGESIZE * 1, DEFAULT_PAGESIZE * 2, DEFAULT_PAGESIZE * 4, DEFAULT_PAGESIZE * 10,
            DEFAULT_PAGESIZE * 20 };

    private List<DynamicBean> columnsList = new ArrayList<DynamicBean>(); // 表格列

    @Override
    protected Map<String, Object> createRoot() {
        Map<String, Object> root = new HashMap<String, Object>();
        // TODO 编写ftl模板,组装模板数据
        return root;
    }

    /**
     * 设置tabele的数据列
     * @datetime 2018年6月3日 下午4:02:55
     */
    public void setColumn(DynamicBean column) {
        this.columnsList.add(column);
    }

    @Override
    public String getId() {
        String id = super.getId();
        if (StringUtil.isEmpty(id)) {
            id = DEFAULT_GRID_ID;
        }
        return id;
    }

    @Override
    protected String getTemplateName() {
        return TEMP_NAME;
    }

    @Override
    public void doFinally() {
        super.doFinally();
        this.columnsList.clear();
    }

}
