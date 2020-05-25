package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.VariantDao;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity_mapper.VariantEntityMapper;

import java.util.List;


public class VariantService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private VariantEntityMapper variantEntityMapper = new VariantEntityMapper();

    public void save(Variant variant){
        try (VariantDao variantDao  = daoFactory.createVariantDao()){
            variantDao.save(variant);
        }
    }

    public void update(Variant variant){
        try (VariantDao variantDao = daoFactory.createVariantDao()){
            variantDao.update(variant);
        }
    }

    public List<Variant> getVariantListByQuestionId(Question question){
        try(VariantDao variantDao = daoFactory.createVariantDao()){
            return variantDao.findVariantsByQuestionId(question);
        }
    }

    public void mapListsForUpdate(List<Variant> variantList, List<Variant> variantListByQuestionId){
        variantEntityMapper.mapVariantListsForUpdate(variantList, variantListByQuestionId);
    }

}
