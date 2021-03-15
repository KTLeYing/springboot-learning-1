package net.huashimao.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "huashimao_hsm_db_collect_fold", schema = "huashimao_hsm_db", catalog = "")
public class DbCollectFold {
    private long id;
    private long userId;
    private String name;
    private String description;

    //在实体类日期类型的get方法或属性上添加注解@JsonFormat(pattern="yyyy-MM-dd")即可解决，把时间戳转换为时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
   //@JsonProperty("Date")  //用来指定该属性的json序列化字段名，比如：不进行首字母小写转换
    private Timestamp createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //和在实体类日期类型的属性上添加@JsonFormat是一样的
    @Basic
    @Column(name = "created_time", nullable = false)
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //和在实体类日期类型的属性上添加@JsonFormat是一样的
    @Basic
    @Column(name = "updated_time", nullable = false)
    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbCollectFold that = (DbCollectFold) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(updatedTime, that.updatedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, description, createdTime, updatedTime);
    }
}
