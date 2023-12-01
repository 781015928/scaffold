package com.crazypug.mapper;

import io.mybatis.provider.EntityColumn;
import io.mybatis.provider.EntityTable;
import io.mybatis.provider.SqlScript;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.List;
import java.util.stream.Collectors;

public class ExtMapperProvider {

    public static String save(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, entity -> "REPLACE INTO " + entity.table()
                + "(" + entity.insertColumnList() + ")"
                + " VALUES (" + entity.insertColumns().stream()
                .map(EntityColumn::variables).collect(Collectors.joining(",")) + ")");
    }



    public static String saveAll(ProviderContext providerContext, @Param("entityList") List<?> entityList) {
        if (entityList == null || entityList.size() == 0) {
            throw new NullPointerException("Parameter cannot be empty");
        }
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "REPLACE INTO " + entity.table()
                        + "(" + entity.insertColumnList() + ")"
                        + " VALUES "
                        + foreach("entityList", "entity", ",", () ->
                        trimSuffixOverrides("(", ")", ",", () ->
                                entity.insertColumns().stream().map(column -> column.variables("entity.")).collect(Collectors.joining(","))));
            }
        });
    }
    public static String selectByIds(ProviderContext providerContext) {
        return SqlScript.caching(providerContext, new SqlScript() {
            @Override
            public String getSql(EntityTable entity) {
                return "SELECT " + entity.baseColumnAsPropertyList()
                        + " FROM " + entity.table()
                        + where(()->entity.idColumns().stream().findFirst().get().column()+" IN "+foreach("collection", "item",",","(",")",()->"#{item}"));
            }
        });
    }

}
