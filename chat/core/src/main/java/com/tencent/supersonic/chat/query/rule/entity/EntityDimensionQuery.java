package com.tencent.supersonic.chat.query.rule.entity;

import org.springframework.stereotype.Component;

import static com.tencent.supersonic.chat.api.pojo.SchemaElementType.DIMENSION;
import static com.tencent.supersonic.chat.api.pojo.SchemaElementType.ID;
import static com.tencent.supersonic.chat.query.rule.QueryMatchOption.OptionType.REQUIRED;
import static com.tencent.supersonic.chat.query.rule.QueryMatchOption.RequireNumberType.AT_LEAST;

@Component
public class EntityDimensionQuery extends EntitySemanticQuery {

    public static final String QUERY_MODE = "ENTITY_DIMENSION";

    public EntityDimensionQuery() {
        super();
        queryMatcher.addOption(DIMENSION, REQUIRED, AT_LEAST, 1)
                .addOption(ID, REQUIRED, AT_LEAST, 0);
    }

    @Override
    public String getQueryMode() {
        return QUERY_MODE;
    }

}
