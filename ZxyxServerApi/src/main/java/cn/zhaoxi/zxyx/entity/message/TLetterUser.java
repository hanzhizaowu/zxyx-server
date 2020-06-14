package cn.zhaoxi.zxyx.entity.message;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_letter_user")
@Data
public class TLetterUser {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;

    @Column(name = "postUserId")
    private Long postUserId;

    @Column(name = "receiverUserId")
    private Long receiverUserId;

    @Column(name = "updateTime")
    private Long updateTime;
}
