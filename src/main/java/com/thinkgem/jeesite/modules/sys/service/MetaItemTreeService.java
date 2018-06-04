/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.MetaItemTreeDao;
import com.thinkgem.jeesite.modules.sys.entity.MetaItemTree;

/**
 * 资源分类表Service
 * @author hxw
 * @version 2016-04-25
 */
@Service
@Transactional(readOnly = true)
public class MetaItemTreeService extends TreeService<MetaItemTreeDao, MetaItemTree> {

	public MetaItemTree get(String id) {
		return super.get(id);
	}
	
	public MetaItemTree getByCode(String code) {
		return dao.getByCode(code);
	}
	
	
	public List<MetaItemTree> findList(MetaItemTree metaItemTree) {
		if (StringUtils.isNotBlank(metaItemTree.getParentIds())){
			metaItemTree.setParentIds(","+metaItemTree.getParentIds()+",");
		}
		return super.findList(metaItemTree);
	}
	
	public List<MetaItemTree> findChild(MetaItemTree metaItemTree) {
		return dao.findChild(metaItemTree);
	}
	public List<MetaItemTree> findChildByIndex(String pid) {
		return dao.findChildByIndex(pid);
	}
	
	@Transactional(readOnly = false)
    public void inser(MetaItemTree entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            dao.inser(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

	
	@Transactional(readOnly = false)
    public void save(MetaItemTree entity) {

        @SuppressWarnings("unchecked")
        Class<MetaItemTree> entityClass = Reflections.getClassGenricType(getClass(), 1);

        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if (entity.getParent() == null || StringUtils.isBlank(entity.getParentId())
                || "0".equals(entity.getParentId())) {
            entity.setParent(null);
        } else {
            entity.setParent(super.get(entity.getParentId()));
        }
        if (entity.getParent() == null) {
        	MetaItemTree parentEntity = null;
            try {
                parentEntity = entityClass.getConstructor(String.class).newInstance("0");
            } catch (Exception e) {
                throw new ServiceException(e);
            }
            entity.setParent(parentEntity);
            entity.getParent().setParentIds(StringUtils.EMPTY);
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = entity.getParentIds();
        
        // 设置新的父节点串
        entity.setParentIds(entity.getParent().getParentIds() + entity.getParent().getId() + ",");

        Integer grade = entity.getParentIds().split(",").length;
        
        entity.setGrade(grade.toString());
         
        // 保存或更新实体
        this.inser(entity);

        // 更新子节点 parentIds
        MetaItemTree o = null;
        try {
            o = entityClass.newInstance();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        o.setParentIds("%," + entity.getId() + ",%");
        List<MetaItemTree> list = dao.findByParentIdsLike(o);
        int i = 0;
        for (MetaItemTree e : list) {
            i++;
            System.out.println("===========================" + i);
            if (i == 670) {
                System.out.println("===========Debug================");
            }
            if (e.getParentIds() != null && oldParentIds != null) {
                e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
                preUpdateChild(entity, e);
                dao.updateParentIds(e);
            }
        }

    }
	
	@Transactional(readOnly = false)
	public void delete(MetaItemTree metaItemTree) {
		super.delete(metaItemTree);
	}
	
	public List<MetaItemTree> findByGrade(String parebtId){
		return dao.findByGrade(parebtId);
	}
	 
	public List<MetaItemTree> findByParentId(String parebtId){
		return dao.findByParentId(parebtId);
	}
	 
}