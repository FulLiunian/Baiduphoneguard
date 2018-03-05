package com.zys.baiduphoneguard.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.zys.baiduphoneguard.dao.DaoSession;
import com.zys.baiduphoneguard.dao.FunctionTableDao;

/**
 * Created by Administrator on 2017/11/13.
 */

@Entity(
        active = true,//crud
        nameInDb = "function_table",//数据库中的表名
        createInDb = true,
        generateConstructors = true,//生成构造方法
        generateGettersSetters = true//生成getter 与 setter 方法
)
public class FunctionTable {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    @Index(unique = true)
    private String funcName;
    @NotNull
    private String funcPic;
    @NotNull
    private Integer funcIndex;

    private Boolean funcFixed;
/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;
/** Used for active entity operations. */
@Generated(hash = 1306474659)
private transient FunctionTableDao myDao;

@Generated(hash = 542546271)
public FunctionTable(Long id, @NotNull String funcName, @NotNull String funcPic,
        @NotNull Integer funcIndex, Boolean funcFixed) {
    this.id = id;
    this.funcName = funcName;
    this.funcPic = funcPic;
    this.funcIndex = funcIndex;
    this.funcFixed = funcFixed;
}

@Generated(hash = 52834864)
public FunctionTable() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getFuncName() {
    return this.funcName;
}

public void setFuncName(String funcName) {
    this.funcName = funcName;
}

public String getFuncPic() {
    return this.funcPic;
}

public void setFuncPic(String funcPic) {
    this.funcPic = funcPic;
}

public Integer getFuncIndex() {
    return this.funcIndex;
}

public void setFuncIndex(Integer funcIndex) {
    this.funcIndex = funcIndex;
}

public Boolean getFuncFixed() {
    return this.funcFixed;
}

public void setFuncFixed(Boolean funcFixed) {
    this.funcFixed = funcFixed;
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1860561580)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getFunctionTableDao() : null;
}
}
