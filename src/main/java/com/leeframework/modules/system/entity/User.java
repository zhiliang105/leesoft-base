package com.leeframework.modules.system.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.leeframework.common.hibernate4.entity.BaseEntity;
import com.leeframework.common.utils.properties.SysConfigProperty;
import com.leeframework.core.security.encrypt.MD5Encoder;

/**
 * <b>SysUser.java</b><br />
 * <b>description:用户实体</b>
 * @author 李志亮 (Lee-ANDON) <zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月16日 Time: 下午4:06:29
 * @version 1.0
 * @since version 1.0
 * @update
 */

@Entity
@DynamicUpdate
@Table(name = "t_sys_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 3193275122442878538L;

    private Origanization origanization;// 所在部门组织
    private String realName;// 真实姓名
    private String userName; // 用户名
    private String password;// 密码
    private String sex;// 性别
    private String birth;// 生日
    private String duty;// 担任职务
    private String phone;// 手机号码
    private String tel;// 联系电话
    private String weixin;
    private String email;// 电子邮件
    private String qq;// QQ号码
    private String bigPhoto;// 大头像
    private String smallPhoto;// 小头像

    private UserExtend userExtend;

    /**
     * 创建超级管理员用户
     * @datetime 2018年6月21日 下午4:14:11
     */
    public static User createSuperadmin() {
        String password = SysConfigProperty.getDepPassword();
        String userName = SysConfigProperty.getDepUserName();
        User user = new User();
        user.setUserName(userName);
        user.setPassword(MD5Encoder.encode(password, userName));
        user.setRealName("力软网络科技");
        user.setCanDelete(false);
        user.setPhone("18538086785");
        user.setQq("279683131");
        user.setTel("0513-66670700");
        user.setWeixin("13962944095");
        return user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    public Origanization getOriganization() {
        return origanization;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    public UserExtend getUserExtend() {
        return userExtend;
    }

    @Column(name = "real_name", length = 20)
    public String getRealName() {
        return realName;
    }

    @Column(name = "user_name", length = 20, nullable = false)
    public String getUserName() {
        return userName;
    }

    @Column(length = 100, nullable = false)
    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public String getBirth() {
        return birth;
    }

    public String getDuty() {
        return duty;
    }

    public String getPhone() {
        return phone;
    }

    public String getTel() {
        return tel;
    }

    public String getWeixin() {
        return weixin;
    }

    public String getEmail() {
        return email;
    }

    public String getQq() {
        return qq;
    }

    @Column(name = "big_photo")
    public String getBigPhoto() {
        return bigPhoto;
    }

    @Column(name = "small_photo")
    public String getSmallPhoto() {
        return smallPhoto;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOriganization(Origanization origanization) {
        this.origanization = origanization;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public void setBigPhoto(String bigPhoto) {
        this.bigPhoto = bigPhoto;
    }

    public void setSmallPhoto(String smallPhoto) {
        this.smallPhoto = smallPhoto;
    }

    public void setUserExtend(UserExtend userExtend) {
        this.userExtend = userExtend;
    }
}
