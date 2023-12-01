package com.crazypug.mapper;

import io.mybatis.mapper.Mapper;
import io.mybatis.mapper.example.Example;
import io.mybatis.mapper.list.ListProvider;
import io.mybatis.provider.Caching;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;

public interface ExtMapper<T, I extends Serializable> extends Mapper<T, I> {


    @Lang(Caching.class)
    @InsertProvider(type = ExtMapperProvider.class, method = "save")
    int save(T entity);


    @Lang(Caching.class)
    @InsertProvider(type = ExtMapperProvider.class, method = "saveAll")
    int saveAll(@Param("entityList") List<? extends T> entityList);


    /**
     * 批量保存实体，需要数据库支持批量插入的语法
     *
     * @param entityList 实体列表
     * @return 结果数等于 entityList.size() 时成功，不相等时失败
     */
    @Lang(Caching.class)
    @InsertProvider(type = ListProvider.class, method = "insertList")
    int insertList(@Param("entityList") List<? extends T> entityList);

    default List<T> selectAll() {
        Example<T> example = new Example<>();
        return selectByExample(example);
    }

    @Lang(Caching.class)
    @SelectProvider(type = ExtMapperProvider.class, method = "selectByIds")
    List<T> selectByIds(Iterable<I> fieldValueList);
}
