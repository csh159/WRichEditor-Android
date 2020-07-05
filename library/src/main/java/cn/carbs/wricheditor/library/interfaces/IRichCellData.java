package cn.carbs.wricheditor.library.interfaces;

import java.util.Set;

import cn.carbs.wricheditor.library.types.RichType;

/**
 * 所有存储数据信息的数据单元的接口
 */
public interface IRichCellData {

    Object getData();

    Set<RichType> getType();

}