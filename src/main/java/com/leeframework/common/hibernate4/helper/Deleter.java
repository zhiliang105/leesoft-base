package com.leeframework.common.hibernate4.helper;

/**
 * Hibernate删除器
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月21日 Time: 上午10:29:45
 * @version 1.0
 * @since version 1.0
 * @update
 */
public final class Deleter extends Updater {

    private Deleter(Class<?> clazz) {
        super(clazz);

        hqlBuilder.delete(0, hqlBuilder.length());
        hqlBuilder.append("delete from ").append(clazz.getSimpleName()).append(" ").append(this.alias).append(" ");
    }

    /**
     * 创建删除器实例
     * @author lee
     * @date 2016年4月21日 上午10:35:24
     * @param clazz
     * @return
     */
    public static Deleter create(Class<?> clazz) {
        return new Deleter(clazz);
    }

}
