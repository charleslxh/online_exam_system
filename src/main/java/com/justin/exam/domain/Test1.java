package com.justin.exam.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.justin.exam.domain.util.CustomDateTimeDeserializer;
import com.justin.exam.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Test1.
 */
@Entity
@Table(name = "T_TEST1")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Test1 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "testname")
    private String testname;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "create_time")
    private DateTime createTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "lase_update_time")
    private DateTime laseUpdateTime;

    @OneToMany(mappedBy = "test1")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Test2> test2s = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getLaseUpdateTime() {
        return laseUpdateTime;
    }

    public void setLaseUpdateTime(DateTime laseUpdateTime) {
        this.laseUpdateTime = laseUpdateTime;
    }

    public Set<Test2> getTest2s() {
        return test2s;
    }

    public void setTest2s(Set<Test2> test2s) {
        this.test2s = test2s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test1 test1 = (Test1) o;

        if ( ! Objects.equals(id, test1.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Test1{" +
                "id=" + id +
                ", testname='" + testname + "'" +
                ", password='" + password + "'" +
                ", fullName='" + fullName + "'" +
                ", createTime='" + createTime + "'" +
                ", laseUpdateTime='" + laseUpdateTime + "'" +
                '}';
    }
}
