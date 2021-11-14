package com.bjsxt.mappers;

import com.bjsxt.domain.AdminBank;
import com.bjsxt.dto.AdminBankDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用来做对象的映射转化
 * entity2Dto
 * Dto2Entity
 */
@Mapper(componentModel = "spring")
public interface AdminBankDtoMappers {

    // 获取该对象的实例
    AdminBankDtoMappers INSTANCE = Mappers.getMapper(AdminBankDtoMappers.class);

    /**
     * 将entity转化为dto
     *
     * @param source
     * @return
     */
    AdminBankDto convert2Dto(AdminBank source);

    /**
     * 将dto对象转化为entity对象
     *
     * @param source
     * @return
     */
    AdminBank convert2Entity(AdminBankDto source);


    /**
     * 将entity转化为dto
     *
     * @param source
     * @return
     */
    List<AdminBankDto> convert2Dto(List<AdminBank> source);

    /**
     * 将dto对象转化为entity对象
     *
     * @param source
     * @return
     */
    List<AdminBank> convert2Entity(List<AdminBankDto> source);
}
