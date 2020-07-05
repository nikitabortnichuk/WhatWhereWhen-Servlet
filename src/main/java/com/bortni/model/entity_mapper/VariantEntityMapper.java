package com.bortni.model.entity_mapper;

import com.bortni.model.entity.Variant;

import java.util.List;

public class VariantEntityMapper {
    public void mapVariantListsForUpdate(List<Variant> variantList, List<Variant> variantListByQuestionId) {
        variantList.get(0).setId(variantListByQuestionId.get(0).getId());
        variantList.get(1).setId(variantListByQuestionId.get(1).getId());
        variantList.get(2).setId(variantListByQuestionId.get(2).getId());
        variantList.get(3).setId(variantListByQuestionId.get(3).getId());
    }
}
