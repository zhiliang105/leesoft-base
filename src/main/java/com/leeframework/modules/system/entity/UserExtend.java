package com.leeframework.modules.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.leeframework.common.hibernate4.entity.IdEntity;

/**
 * 用户的扩展属性实体
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月3日 下午3:53:30
 */
@Entity
@DynamicUpdate
@Table(name = "t_sys_user_extend")
public class UserExtend extends IdEntity {
    private static final long serialVersionUID = 5536063025883597779L;

    private User user;// 对应的用户：一对一关联
    private String cardNo;// 工号
    private String entryDate;// 入职日期
    private String idCardNo;// 身份证号
    private String shortNo;// 短号
    private String officeTel;// 办公电话
    private String education;// 学历
    private String address;// 联系地址
    private Boolean onState;// 在职状态
    private String signPic;// 签名图片
    private String field1;// 扩展属性1
    private String field2;// 扩展属性2

    public UserExtend() {
    }

    public UserExtend(String id) {
        super(id);
    }

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    public User getUser() {
        return user;
    }

    @Column(name = "card_no")
    public String getCardNo() {
        return cardNo;
    }

    @Column(name = "entry_date")
    public String getEntryDate() {
        return entryDate;
    }

    @Column(name = "idcard_no")
    public String getIdCardNo() {
        return idCardNo;
    }

    @Column(name = "short_no")
    public String getShortNo() {
        return shortNo;
    }

    @Column(name = "office_tel")
    public String getOfficeTel() {
        return officeTel;
    }

    public String getEducation() {
        return education;
    }

    public String getAddress() {
        return address;
    }

    @Column(name = "on_state")
    public Boolean getOnState() {
        return onState;
    }

    @Column(name = "sign_pic")
    public String getSignPic() {
        return signPic;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public void setShortNo(String shortNo) {
        this.shortNo = shortNo;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOnState(Boolean onState) {
        this.onState = onState;
    }

    public void setSignPic(String signPic) {
        this.signPic = signPic;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

}
