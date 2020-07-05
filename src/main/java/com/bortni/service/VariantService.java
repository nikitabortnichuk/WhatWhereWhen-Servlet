package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.VariantDao;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity_mapper.VariantEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class VariantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VariantService.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private VariantEntityMapper variantEntityMapper = new VariantEntityMapper();

    public void save(Variant variant){
        try (VariantDao variantDao  = daoFactory.createVariantDao()){
            variantDao.save(variant);
        }
        LOGGER.info("Saving variant");
    }

    public void update(Variant variant){
        try (VariantDao variantDao = daoFactory.createVariantDao()){
            variantDao.update(variant);
        }
        LOGGER.info("Updating variant");
    }

    public List<Variant> getVariantListByQuestionId(Question question){

        try(VariantDao variantDao = daoFactory.createVariantDao()){
            List<Variant> variantList = variantDao.findVariantsByQuestionId(question);
            LOGGER.info("Getting variantList by question id");
            return variantList;
        }
    }

    public void mapListsForUpdate(List<Variant> variantList, List<Variant> variantListByQuestionId){
        variantEntityMapper.mapVariantListsForUpdate(variantList, variantListByQuestionId);
    }

}
