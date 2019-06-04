package com.leeframework.common.model.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * bootstrap-treeview数据节点模型
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月30日 下午9:52:28
 */
public class BootstrapTree {

    private String text;
    private String icon; // 列表树节点上的图标，通常是节点左边的图标。
    private String selectedIcon;// 当某个节点被选择后显示的图标，通常是节点左边的图标。
    private String href;// 结合全局enableLinks选项为列表树节点指定URL。
    private boolean selectable = true;// 指定列表树的节点是否可选择。设置为false将使节点展开，并且不能被选择。
    private State state = new State();
    private String color;
    private String backColor;
    private List<String> tags = new ArrayList<String>();// 通过结合全局showTags选项来在列表树节点的右边添加额外的信息。

    private List<BootstrapTree> nodes = new ArrayList<BootstrapTree>();
    
    public static class State {
        private boolean checked = false;
        private boolean disabled = false;
        private boolean expanded = false;
        private boolean selected = false;

        public boolean isChecked() {
            return checked;
        }

        public boolean isExpanded() {
            return expanded;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public String getSelectedIcon() {
        return selectedIcon;
    }

    public String getHref() {
        return href;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public State getState() {
        return state;
    }

    public String getColor() {
        return color;
    }

    public String getBackColor() {
        return backColor;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setSelectedIcon(String selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<BootstrapTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<BootstrapTree> nodes) {
        this.nodes = nodes;
    }

}
